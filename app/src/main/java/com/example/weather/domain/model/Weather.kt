package com.example.weather.domain.model

import androidx.compose.runtime.Stable

@Stable
class Weather(
    var locationInfo: LocationInfo = LocationInfo(),
    var currentCondition: CurrentCondition = CurrentCondition(),
    val listOfHourlyForecast: List<HourlyForecast> = listOf(),
) {
}