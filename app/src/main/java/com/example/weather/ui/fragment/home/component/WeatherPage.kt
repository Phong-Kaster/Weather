package com.example.weather.ui.fragment.home.component

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weather.domain.model.CurrentCondition
import com.example.weather.domain.model.DailyForecast
import com.example.weather.domain.model.HourlyForecast
import java.util.TimeZone

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WeatherPage(
    showLoading: Boolean,
    timezone: String = TimeZone.getDefault().id,
    currentCondition: CurrentCondition,
    listOfHourlyForecast: List<HourlyForecast>,
    listOfDailyForecast: List<DailyForecast>,
    modifier: Modifier = Modifier,
) {
    val lazyState = rememberLazyListState()
    CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
        LazyColumn(
            state = lazyState,
            verticalArrangement = Arrangement.spacedBy(
                space = 16.dp,
                alignment = Alignment.Top
            ),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, bottom = 16.dp, top = 16.dp)
        ) {

            item(key = "header") {
                WeatherHeader(
                    modifier = Modifier.padding(top = 20.dp, bottom = 0.dp),
                    currentCondition = currentCondition,
                )
            }

            item(key = "hourly") {
                WeatherForecastHourly(
                    timezone = timezone,
                    showLoading = showLoading,
                    hourlyForecasts = listOfHourlyForecast,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            item(key = "daily") {
                WeatherForecastDaily(
                    showLoading = showLoading,
                    dailyForecasts = listOfDailyForecast,
                    modifier = Modifier.fillMaxWidth(),
                )
            }

            item(key = "sunrise") {
                WeatherSunrise(
                    showLoading = showLoading,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            item(key = "chart") {
                WeatherHourlyChart(
                    records = listOfHourlyForecast,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(20.dp))
                        .background(
                            color = Color.White.copy(alpha = 0.3f),
                            shape = RoundedCornerShape(20.dp)
                        )
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewWeatherPage() {
    WeatherPage(
        timezone = TimeZone.getDefault().id,
        listOfHourlyForecast = listOf(),
        listOfDailyForecast = listOf(),
        currentCondition = CurrentCondition(),
        showLoading = false,
        modifier = Modifier
    )
}