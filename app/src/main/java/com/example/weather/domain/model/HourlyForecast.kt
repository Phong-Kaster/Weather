package com.example.weather.domain.model

import androidx.compose.runtime.Immutable
import java.time.LocalDate

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
    companion object {

        fun getFakeInstance(): HourlyForecast {
            val epochDateTime = LocalDate.now()
            return HourlyForecast(id = 1, locationKey = "1", epochDateTime = epochDateTime.plusDays(0).toEpochDay(), weatherIcon = 1, temperature = 37f, feelTemperature = 40f, dewPoint = 0f, windSpeed = Value(1f, "km/h"), windDirection = WindDirection(1, "N"), relativeHumidity = 10, visibility = Value(1f, "km"), uvIndex = 1, rain = Value(1f, "mm"))

        }

        fun getFakeList(): List<HourlyForecast> {
            val epochDateTime = LocalDate.now()

            val hourly1 = HourlyForecast(id = 1, locationKey = "1", epochDateTime = epochDateTime.plusDays(0).toEpochDay(), weatherIcon = 1, temperature = 37f, feelTemperature = 40f, dewPoint = 0f, windSpeed = Value(1f, "km/h"), windDirection = WindDirection(1, "N"), relativeHumidity = 10, visibility = Value(1f, "km"), uvIndex = 1, rain = Value(1f, "mm"))
            val hourly2 = HourlyForecast(id = 2, locationKey = "1", epochDateTime = epochDateTime.plusDays(1).toEpochDay(), weatherIcon = 1, temperature = 10f, feelTemperature = 11.5f, dewPoint = 0f, windSpeed = Value(1f, "km/h"), windDirection = WindDirection(1, "N"), relativeHumidity = 10, visibility = Value(1f, "km"), uvIndex = 1, rain = Value(1f, "mm"))
            val hourly3 = HourlyForecast(id = 3, locationKey = "1", epochDateTime = epochDateTime.plusDays(2).toEpochDay(), weatherIcon = 1, temperature = 15f, feelTemperature = 20f, dewPoint = 0f, windSpeed = Value(1f, "km/h"), windDirection = WindDirection(1, "N"), relativeHumidity = 10, visibility = Value(1f, "km"), uvIndex = 1, rain = Value(1f, "mm"))
            val hourly4 = HourlyForecast(id = 4, locationKey = "1", epochDateTime = epochDateTime.plusDays(3).toEpochDay(), weatherIcon = 1, temperature = 28f, feelTemperature = 20f, dewPoint = 0f, windSpeed = Value(1f, "km/h"), windDirection = WindDirection(1, "N"), relativeHumidity = 10, visibility = Value(1f, "km"), uvIndex = 1, rain = Value(1f, "mm"))
            val hourly5 = HourlyForecast(id = 5, locationKey = "1", epochDateTime = epochDateTime.plusDays(5).toEpochDay(), weatherIcon = 1, temperature = 24f, feelTemperature = 20f, dewPoint = 0f, windSpeed = Value(1f, "km/h"), windDirection = WindDirection(1, "N"), relativeHumidity = 10, visibility = Value(1f, "km"), uvIndex = 1, rain = Value(1f, "mm"))
            val hourly6 = HourlyForecast(id = 6, locationKey = "1", epochDateTime = epochDateTime.plusDays(6).toEpochDay(), weatherIcon = 1, temperature = 22f, feelTemperature = 23f, dewPoint = 0f, windSpeed = Value(1f, "km/h"), windDirection = WindDirection(1, "N"), relativeHumidity = 10, visibility = Value(1f, "km"), uvIndex = 1, rain = Value(1f, "mm"))

            return listOf(hourly1, hourly2, hourly3, hourly4, hourly5, hourly6)
        }
    }
}