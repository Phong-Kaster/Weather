package com.example.weather.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class DayNight(
    val icon: Int = 1,
    val hasPrecipitation: Boolean = false,
    val shortPhrase: String = "Partly cloudy",
    val longPhrase: String = "",
    val precipitationProbability: Float = 0f,
    val wind: Wind = Wind(),
    val windGust: Wind = Wind(),
    val rain: Value = Value(),
    val snow: Value = Value(),
    val ice: Value = Value(),
    val evapotranspiration: Value = Value(),
    val solarIrradiance: Value = Value(),
)