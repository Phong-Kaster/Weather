package com.example.weather.domain.model

import androidx.compose.runtime.Immutable
import com.example.weather.domain.enums.ConditionType
import java.util.Date
import java.util.UUID

@Immutable
data class HourlyForecast(
    val id: String = UUID.randomUUID().toString(),
    val date: Date = Date(),
    val weatherIcon: Int = 0,
    val temperature: Value = Value(),
    val feelTemperature: Value = Value(),
    val humidity: Float = 0f,
    val wind: Wind = Wind(),
    val visibility: Value = Value(),
    val rain: Value = Value(),
    val snow: Value = Value(),
    val ice: Value = Value(),
    val dewPoint: Value = Value(),
    val solarIrradiance: Value = Value(),
    val precipitation: Float = 0f,
    val evapotranspiration: Value = Value(),
    val uvIndex: Float = 0f,
    val parentCurrent: String = "",
    val created: Date? = null,
) {
}