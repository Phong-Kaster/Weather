package com.example.weather.ui.fragment.home

import androidx.compose.animation.Animatable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
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
import com.example.weather.ui.fragment.home.component.WeatherSunrise
import com.example.weather.ui.theme.colorNight
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
        viewModel.findAllWeathers()
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
    onOpenSearch: () -> Unit = {}
) {
//    LaunchedEffect(key1 = weathers) {
//        Log.d("TAG", "HomeLayout - weathers: ${weathers.size}")
//    }

    val context = LocalContext.current
    val color = remember { Animatable(colorNight) }

    var pageValue by remember { mutableIntStateOf(0) }
    val pagerState =
        rememberPagerState(initialPage = 0, pageCount = { weathers.size.coerceAtLeast(1) })

//    val backgroundBrush by remember {
//        derivedStateOf {
//            when (pagerState.currentPage) {
//                0 -> brushSunset
//                1 -> brushDay
//                2 -> brushNight
//                3 -> brushDreary
//                4 -> brushMidnight
//                else -> brushNight
//            }
//        }
//    }

//    LaunchedEffect(Unit) {
//        snapshotFlow { pagerState.settledPage }.collect { page ->
//            color.animateTo(
//                targetValue = when (page) {
//                    0 -> colorSunrise
//                    1 -> colorDreary
//                    2 -> colorNight
//                    3 -> colorRain
//                    4 -> colorSunset
//                    else -> colorNight
//                },
//                animationSpec = tween(1000),
//                block = {},
//            )
//        }
//    }

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
                onMenuLeft = onChangeDarkTheme,
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
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                )

                WeatherHeader(
                    currentCondition =
                    if (weathers.isEmpty())
                        CurrentCondition()
                    else
                        weathers[pagerState.settledPage].currentCondition,
                )


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
//                            item(key = "WeatherHeader") {
//                                Spacer(
//                                    modifier = Modifier
//                                        .fillMaxWidth()
//                                        .height(20.dp)
//                                )
//
//                                WeatherHeader(
//                                    currentCondition =
//                                    if (weathers.isEmpty())
//                                        CurrentCondition()
//                                    else
//                                        weathers[pagerState.settledPage].currentCondition,
//                                )
//
//                            }

                            item(key = "WeatherForecastHourly") {
                                WeatherForecastHourly(
                                    showLoading = showLoading,
                                    hourlyForecasts = listOf(
                                        HourlyForecast(id = "0"),
                                        HourlyForecast(id = "1"),
                                        HourlyForecast(id = "2"),
                                        HourlyForecast(id = "3"),
                                        HourlyForecast(id = "4"),
                                        HourlyForecast(id = "5"),
                                        HourlyForecast(id = "6"),
                                        HourlyForecast(id = "7"),
                                        HourlyForecast(id = "8"),
                                        HourlyForecast(id = "9"),
                                        HourlyForecast(id = "10"),
                                        HourlyForecast(id = "11"),
                                        HourlyForecast(id = "12"),
                                        HourlyForecast(id = "13"),
                                        HourlyForecast(id = "14"),
                                        HourlyForecast(id = "15"),
                                    ),
                                    modifier = Modifier,
                                )
                            }

                            item(key = "WeatherForecastDaily") {
                                WeatherForecastDaily(
                                    showLoading = showLoading,
                                    dailyForecasts = listOf(
                                        DailyForecast(),
                                        DailyForecast(),
                                        DailyForecast(),
                                        DailyForecast(),
                                        DailyForecast(),
                                        DailyForecast(),
                                        DailyForecast(),
                                        DailyForecast(),
                                        DailyForecast(),
                                        DailyForecast(),
                                        DailyForecast(),
                                        DailyForecast(),
                                    ),
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