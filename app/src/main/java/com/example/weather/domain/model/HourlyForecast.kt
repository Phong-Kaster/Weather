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

//    fun getValueByFilter(conditionType: ConditionType): Float {
//        return when (conditionType) {
//            ConditionType.TEMPERATURE -> {
//                temperatureValue(temperature).toFloat()
//            }
//
//            ConditionType.PRECIPITATION -> {
//                precipitation
//            }
//
//            ConditionType.WIND -> {
//                windValue(wind.speed)
//            }
//
//            else -> {
//                0f
//            }
//        }
//    }
//
//    fun getValueUnitByFilter(conditionType: ConditionType): String {
//        return when (conditionType) {
//            ConditionType.TEMPERATURE -> "${Constants.PREFIX_DEGREE}${UnitUtils.temperatureUnit}"
//
//            ConditionType.PRECIPITATION -> Constants.PERCENT
//
//            ConditionType.WIND -> UnitUtils.windUnit
//
//            else -> ""
//        }
//    }
//
//    fun rainValue() = if (UnitUtils.precipitationUnit == Constants.PRECIPITATION_IN) {
//        if (rain.unit == Constants.PRECIPITATION_IN) {
//            rain.value
//        } else {
//            rain.value.MmToIn()
//        }
//    } else {
//        if (rain.unit == Constants.PRECIPITATION_MM) {
//            rain.value
//        } else {
//            rain.value.InToMM()
//        }
//    }
//
//    fun temperatureStr() = if (UnitUtils.temperatureUnit == Constants.C_DEGREE) {
//        if (temperature.unit == Constants.C_DEGREE) {
//            temperature.value.toStr()
//        } else {
//            temperature.value.toDegreeF().toStr()
//        }
//    } else {
//        if (temperature.unit == Constants.F_DEGREE) {
//            temperature.value.toStr()
//        } else {
//            temperature.value.toDegreeC().toStr()
//        }
//    }
}