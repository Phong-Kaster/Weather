package com.example.weather.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class HourlyForecast(
    val id: Long = 0,
    val locationKey: String = "",
    val epochDateTime: Long = 0,
    val weatherIcon: Int = 0,
    val temperature: Float = 0f,
    val feelTemperature: Float = 0f,
    val dewPoint: Float = 0f,
    val windSpeed: Value = Value(),
    val windDirection: WindDirection = WindDirection(),
    val relativeHumidity: Int = 0,
    val visibility: Value = Value(),
    val uvIndex: Int = 0,
    val rain: Value = Value(),
) {
}