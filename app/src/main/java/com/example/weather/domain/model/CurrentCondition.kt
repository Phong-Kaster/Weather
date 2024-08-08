package com.example.weather.domain.model

import androidx.compose.runtime.Stable
import java.util.Date

@Stable
class CurrentCondition(
    val locationKey: String = "",
    val date: Date = Date(),
    val weatherText: String = "",
    val weatherIcon: Int = 0,
    val hasPrecipitation: Boolean = false,
    val temperature: Float = 0f,
    val feelTemperature: Float = 0f,
    val relativeHumidity: Float = 0f,
    val windSpeed: Value = Value(),
    val windDirection: WindDirection = WindDirection(),
    val uvIndex: Float = 0f,
    val visibility: Value = Value()
) {
}