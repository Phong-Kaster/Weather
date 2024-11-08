package com.example.weather.ui.fragment.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
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
import com.example.weather.ui.fragment.home.component.WeatherPage
import com.example.weather.ui.fragment.home.component.WeatherSunrise
import com.example.weather.util.ColorUtil
import com.example.weather.util.NavigationUtil.safeNavigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.persistentListOf
import java.util.TimeZone

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
                enableDarkTheme = !enableDarkTheme
                viewModel.setDarkMode(enableDarkTheme)
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


    var pageValue by remember { mutableIntStateOf(0) }
    val pagerState =
        rememberPagerState(initialPage = 0, pageCount = { weathers.size.coerceAtLeast(1) })


    val weatherIcon by remember(weathers) {
        derivedStateOf {
            if (weathers.isEmpty())
                1
            else
                weathers[pagerState.settledPage].currentCondition.weatherIcon
        }
    }
    val timezone by remember(weathers) {
        derivedStateOf {
            if (weathers.isEmpty())
                TimeZone.getDefault().id
            else
                weathers[pagerState.settledPage].locationInfo.timezone
        }
    }

    // CONTENT
    CoreLayout(
        backgroundBrush = ColorUtil.createWeatherBrush(
            weatherIcon = weatherIcon,
            timezone = timezone
        ),
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
                onMenuLeft = onOpenSearch,
                onMenuRight = onOpenSetting,
                modifier = Modifier.statusBarsPadding(),
            )
        },
        bottomBar = {
            AccuWeather(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 10.dp, top = 0.dp)
            )
        },
        content = {
            HorizontalPager(
                state = pagerState,
                modifier = Modifier.fillMaxSize(),
                pageContent = { page ->
                    pageValue = page

                     WeatherPage(
                        showLoading = showLoading,
                        currentCondition = if (weathers.isEmpty()) CurrentCondition() else weathers[page].currentCondition,
                        listOfHourlyForecast = if (weathers.isEmpty()) listOf() else weathers[page].listOfHourlyForecast,
                        listOfDailyForecast = if (weathers.isEmpty()) listOf() else listOf(),
                        modifier = Modifier,
                    )
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