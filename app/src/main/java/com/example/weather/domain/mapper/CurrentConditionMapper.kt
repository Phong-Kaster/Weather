package com.example.weather.domain.mapper

import com.example.weather.data.datasource.database.entity.CurrentConditionEntity
import com.example.weather.data.datasource.remote.response.CurrentConditionResponse
import com.example.weather.domain.model.CurrentCondition
import com.example.weather.domain.model.Value
import com.example.weather.domain.model.WindDirection
import java.util.Date

object CurrentConditionMapper {
    fun CurrentConditionEntity.toModel(): CurrentCondition {
        return CurrentCondition(
            locationKey = this.locationKey,
            date = this.date,
            weatherText = this.weatherText,
            weatherIcon = this.weatherIcon,
            hasPrecipitation = this.hasPrecipitation,
            temperature = this.temperature,
            feelTemperature = this.feelTemperature,
            relativeHumidity = this.relativeHumidity,
            windSpeed = this.windSpeed,
            windDirection = this.windDirection,
            uvIndex = this.uvIndex,
            visibility = this.visibility
        )
    }

    fun CurrentCondition.toModel(): CurrentConditionEntity {
        return CurrentConditionEntity(
            locationKey = this.locationKey,
            date = this.date,
            weatherText = this.weatherText,
            weatherIcon = this.weatherIcon,
            hasPrecipitation = this.hasPrecipitation,
            temperature = this.temperature,
            feelTemperature = this.feelTemperature,
            relativeHumidity = this.relativeHumidity,
            windSpeed = this.windSpeed,
            windDirection = this.windDirection,
            uvIndex = this.uvIndex,
            visibility = this.visibility
        )
    }

    fun CurrentConditionResponse.toModel(locationKey: String): CurrentCondition {
        return CurrentCondition(
            locationKey = locationKey,
            date = Date(),
            weatherText = this.weatherText,
            weatherIcon = this.weatherIcon,
            hasPrecipitation = this.hasPrecipitation,
            temperature = this.temperature.metric.value,
            feelTemperature = this.feelTemperature.metric.value,
            relativeHumidity = this.relativeHumidity,
            windSpeed = Value(
                value = this.wind.speed.metric.value,
                unit = this.wind.speed.metric.unit,
                unitType = this.wind.speed.metric.unitType
            ),
            windDirection = WindDirection(
                degree = this.wind.direction.degrees,
                english = this.wind.direction.english,
                localized = this.wind.direction.localized
            ),
            uvIndex = this.uvIndex,
            visibility = Value(
                value = this.visibility.metric.value,
                unit = this.visibility.metric.unit,
                unitType = this.visibility.metric.unitType
            )
        )
    }
}