package com.example.weather.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Temperature(
    val max: Value = Value(),
    val min: Value = Value()
) {
//    fun temperatureMaxValue() = temperatureValue(max)
//
//    fun temperatureMinValue() = temperatureValue(min)
}