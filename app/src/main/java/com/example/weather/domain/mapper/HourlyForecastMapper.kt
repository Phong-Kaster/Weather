package com.example.weather.domain.mapper

import com.example.weather.data.datasource.database.entity.HourlyForecastEntity
import com.example.weather.data.datasource.remote.response.HourlyForecastResponse
import com.example.weather.domain.model.HourlyForecast
import com.example.weather.domain.model.Value
import com.example.weather.domain.model.Wind
import com.example.weather.domain.model.WindDirection

object HourlyForecastMapper {
    fun HourlyForecastEntity.toModel(): HourlyForecast {
        return HourlyForecast(
            id = this.id,
            locationKey = this.locationKey,
            epochDateTime = this.epochDateTime,
            weatherIcon = this.weatherIcon,
            temperature = this.temperature,
            feelTemperature = this.feelTemperature,
            dewPoint = this.dewPoint,
            windSpeed = this.windSpeed,
            windDirection = this.windDirection,
            relativeHumidity = this.relativeHumidity,
            visibility = this.visibility,
            uvIndex = this.uvIndex,
            rain = this.rain
        )
    }

    fun HourlyForecast.toEntity(locationKey: String): HourlyForecastEntity {
        return HourlyForecastEntity(
            id = this.id,
            locationKey = locationKey,
            epochDateTime = this.epochDateTime,
            weatherIcon = this.weatherIcon,
            temperature = this.temperature,
            feelTemperature = this.feelTemperature,
            dewPoint = this.dewPoint,
            windSpeed = this.windSpeed,
            windDirection = this.windDirection,
            relativeHumidity = this.relativeHumidity,
            visibility = this.visibility,
            uvIndex = this.uvIndex,
            rain = this.rain,
        )
    }

    fun HourlyForecastResponse.toModel(locationKey: String): HourlyForecast {
        return HourlyForecast(
            id = 0,
            locationKey = locationKey,
            epochDateTime = this.epochDateTime,
            weatherIcon = this.weatherIcon,
            temperature = this.temperature.value,
            feelTemperature = this.feelTemperature.value,
            dewPoint = this.dewPoint.value,
            windSpeed = Value(
                value = this.wind.speed.metric.value,
                unitType = this.wind.speed.metric.unitType,
                unit = this.wind.speed.metric.unit
            ),
            windDirection = WindDirection(
                degree = this.wind.direction.degrees,
                localized = this.wind.direction.localized,
                english = this.wind.direction.english
            ),
            relativeHumidity = this.relativeHumidity,
            visibility = Value(
                value = this.visibility.value,
                unitType = this.visibility.unitType,
                unit = this.visibility.unit
            ),
            uvIndex = this.uvIndex,
            rain = Value(
                value = this.rain.value,
                unitType = this.rain.unitType,
                unit = this.rain.unit
            ),

        )
    }


}