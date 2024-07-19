package com.example.weather.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Value(
    val value: Float = 0f, val unit: String = "", val unitType: Int = 0
)