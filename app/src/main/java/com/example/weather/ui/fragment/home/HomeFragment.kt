package com.example.weather.ui.fragment.home

import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.activityViewModels
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.weather.R
import com.example.weather.domain.model.CurrentCondition
import com.example.weather.domain.model.DailyForecast
import com.example.weather.domain.model.HourlyForecast
import com.example.weather.domain.model.LocationInfo
import com.example.weather.domain.model.Weather
import com.example.weather.ui.activity.MainViewModel
import com.example.weather.ui.fragment.home.component.AccuWeather
import com.example.weather.ui.fragment.home.component.HomeTopBar
import com.example.weather.ui.fragment.home.component.WeatherForecastDaily
import com.example.weather.ui.fragment.home.component.WeatherForecastHourly
import com.example.weather.ui.fragment.home.component.WeatherHeader
import com.example.weather.ui.fragment.home.component.WeatherHourlyChart
import com.example.weather.ui.fragment.home.component.WeatherSunrise
import com.example.weather.ui.theme.colorDreary
import com.example.weather.ui.theme.colorNight
import com.example.weather.ui.theme.colorRain
import com.example.weather.ui.theme.colorSunrise
import com.example.weather.ui.theme.colorSunset
import com.example.weather.util.NavigationUtil.safeNavigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.persistentListOf

@AndroidEntryPoint
class HomeFragment : CoreFragment() {

    private val viewModel: MainViewModel by activityViewModels()

    override fun onStart() {
        super.onStart()
        viewModel.executeWeatherWorker()
    }

    override fun onResume() {
        super.onResume()
        viewModel.findWeathers()
    }

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        val errorMessage = viewModel.errorMessage.collectAsState().value

        HomeLayout(
            showLoading = viewModel.showLoading.collectAsState().value,
            weathers = viewModel.weathers.collectAsState().value,
            onOpenSearch = { safeNavigate(R.id.toSearch) },
            onChangeDarkTheme = {
                darkTheme = !darkTheme
                viewModel.setDarkMode(darkTheme)
            },
            onOpenSetting = {
                safeNavigate(R.id.toSetting)
            }
        )

        LaunchedEffect(key1 = errorMessage) {
            if (errorMessage.isEmpty()) return@LaunchedEffect
            showToast(errorMessage)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeLayout(
    showLoading: Boolean,
    weathers: List<Weather>,
    onChangeDarkTheme: () -> Unit = {},
    onOpenSearch: () -> Unit = {},
    onOpenSetting: () -> Unit = {},
) {
    val color = remember { Animatable(colorNight) }

    var pageValue by remember { mutableIntStateOf(0) }
    val pagerState =
        rememberPagerState(initialPage = 0, pageCount = { weathers.size.coerceAtLeast(1) })

    LaunchedEffect(Unit) {
        snapshotFlow { pagerState.settledPage }.collect { page ->
            color.animateTo(
                targetValue = when (page) {
                    0 -> colorSunrise
                    1 -> colorDreary
                    2 -> colorNight
                    3 -> colorRain
                    4 -> colorSunset
                    else -> colorNight
                },
                animationSpec = tween(1000),
                block = {},
            )
        }
    }

    val lazyColumnState = rememberLazyListState()

    // CONTENT
    CoreLayout(
        backgroundColor = color.value,
        modifier = Modifier,
        topBar = {
            HomeTopBar(
                locationInfo =
                if (weathers.isEmpty())
                    LocationInfo()
                else
                    weathers[pagerState.settledPage].locationInfo,
                pageCurrent = pagerState.currentPage,
                pageCount = weathers.size,
                onMenuLeft = onOpenSetting,
                onMenuRight = onOpenSearch,
                modifier = Modifier.statusBarsPadding(),
            )
        },
        bottomBar = {
            AccuWeather(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 0.dp)
            )
        },
        content = {
            HorizontalPager(
                verticalAlignment = Alignment.Top,
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                pageContent = { page ->
                    pageValue = page

                    LazyColumn(
                        state = lazyColumnState,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    ) {
                        item(key = "WeatherHeader") {
                            WeatherHeader(
                                currentCondition =
                                if (weathers.isEmpty())
                                    CurrentCondition()
                                else
                                    weathers[pagerState.settledPage].currentCondition,
                            )
                        }

                        item(key = "WeatherHourlyChart") {
                            WeatherHourlyChart(
                                records = HourlyForecast.getFakeList(),
                                modifier = Modifier.fillMaxWidth()
                            )
                        }

                        item(key = "WeatherForecastHourly") {
                            WeatherForecastHourly(
                                showLoading = showLoading,
                                hourlyForecasts = HourlyForecast.getFakeList(),
                                modifier = Modifier,
                            )
                        }

                        item(key = "WeatherForecastDaily") {
                            WeatherForecastDaily(
                                showLoading = showLoading,
                                dailyForecasts = listOf(DailyForecast()),
                                modifier = Modifier,
                            )
                        }

                        item(key = "WeatherSunrise") {
                            WeatherSunrise(
                                showLoading = showLoading,
                                modifier = Modifier.fillMaxWidth()
                            )
                        }


                    }
                }
            )
        }
    )
}

@Preview
@Composable
private fun PreviewHome() {
    HomeLayout(
        showLoading = false,
        weathers = persistentListOf(),
        onChangeDarkTheme = {}
    )
}