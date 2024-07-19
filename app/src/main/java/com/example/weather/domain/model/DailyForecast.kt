package com.example.weather.domain.model

import androidx.compose.runtime.Immutable
import java.util.Date
import java.util.UUID

@Immutable
data class DailyForecast(
    val id: String = UUID.randomUUID().toString(),
    val date: Date = Date(),
    val sun: SunMoon = SunMoon(),
    val moon: SunMoon = SunMoon(),
    val temperature: Temperature = Temperature(),
    val feelTemperature: Temperature = Temperature(),
    val airAndPollen: List<AirAndPollen> = mutableListOf(),
    val day: DayNight = DayNight(),
    val night: DayNight = DayNight(),
    val created: Date? = null,
)