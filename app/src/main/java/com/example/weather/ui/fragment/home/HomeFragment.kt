package com.example.weather.ui.fragment.home

import android.util.Log
import android.widget.Toast
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.weather.domain.model.DailyForecast
import com.example.weather.domain.model.HourlyForecast
import com.example.weather.ui.fragment.home.component.AccuWeather
import com.example.weather.ui.fragment.home.component.HomeTopBar
import com.example.weather.ui.fragment.home.component.WeatherForecastDaily
import com.example.weather.ui.fragment.home.component.WeatherForecastHourly
import com.example.weather.ui.fragment.home.component.WeatherHeader
import com.example.weather.ui.theme.brushDay
import com.example.weather.ui.theme.brushDreary
import com.example.weather.ui.theme.brushFog
import com.example.weather.ui.theme.brushManageLocation
import com.example.weather.ui.theme.brushMidnight
import com.example.weather.ui.theme.brushNight
import com.example.weather.ui.theme.brushRain
import com.example.weather.ui.theme.brushSunrise
import com.example.weather.ui.theme.brushSunset
import com.example.weather.ui.theme.colorDay
import com.example.weather.ui.theme.colorDreary
import com.example.weather.ui.theme.colorMidnight
import com.example.weather.ui.theme.colorNight
import com.example.weather.ui.theme.colorRain
import com.example.weather.ui.theme.colorSunset
import dagger.hilt.android.AndroidEntryPoint
import kotlin.math.absoluteValue

@AndroidEntryPoint
class HomeFragment : CoreFragment() {

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        HomeLayout()
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeLayout() {

    val context = LocalContext.current
    val color = remember { Animatable(colorDay) }

    var pageValue by remember { mutableIntStateOf(0) }
    val pagerState = rememberPagerState(initialPage = 0, pageCount = { 5 })
    val backgroundBrush by remember {
        derivedStateOf {
            when (pagerState.currentPage) {
                0 -> brushSunset
                1 -> brushDay
                2 -> brushNight
                3 -> brushDreary
                4 -> brushMidnight
                else -> brushNight
            }
        }
    }

    LaunchedEffect(Unit) {
        snapshotFlow { pagerState.settledPage }.collect { page ->
            color.animateTo(
                targetValue = when (page) {
                    0 -> colorMidnight
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


    Box(modifier = Modifier.background(brush = backgroundBrush)) {
        CoreLayout(
            backgroundBrush = backgroundBrush,
//        backgroundColor = color.value,
            modifier = Modifier
                .alpha(
                    alpha = lerp(1f, 0f, pagerState.currentPageOffsetFraction.absoluteValue)
                ),
//            .graphicsLayer {
//                // Calculate the absolute offset for the current page from the
//                // scroll position. We use the absolute value which allows us to mirror
//                // any effects for both directions
//                val pageOffset = ((pagerState.currentPage - pageValue) + pagerState.currentPageOffsetFraction).absoluteValue
//
//                Log.d("TAG", "currentPageOffsetFraction: ${pagerState.currentPageOffsetFraction}")
//
//                alpha = lerp(
//                    start = 1f,
//                    stop = 1f,
//                    fraction = pageOffset.coerceIn(-0.5f, 1f)
//                )
//            }
            topBar = {
                HomeTopBar(
                    pageCurrent = pagerState.currentPage,
                    pageCount = pagerState.pageCount,
                    onMenuLeft = {},
                    onMenuRight = {},
                    modifier = Modifier.statusBarsPadding(),
                )
            },
            bottomBar = {
                AccuWeather(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            },
            content = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .fillMaxSize()
                ) {

                    Spacer(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(20.dp)
                    )

                    WeatherHeader(
                        page = pagerState.settledPage
                    )

                    HorizontalPager(
                        verticalAlignment = Alignment.Top,
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxSize(),
                        pageContent = { page ->
                            pageValue = page

                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                item(key = "WeatherForecastHourly") {
                                    WeatherForecastHourly(
                                        modifier = Modifier,
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
                                        )
                                    )
                                }

                                item(key = "WeatherForecastDaily") {
                                    WeatherForecastDaily(
                                        modifier = Modifier,
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
                                        )
                                    )
                                }
                            }
                        }
                    )
                }
            },

            )
    }
}

@Preview
@Composable
private fun PreviewHome() {
    HomeLayout()
}