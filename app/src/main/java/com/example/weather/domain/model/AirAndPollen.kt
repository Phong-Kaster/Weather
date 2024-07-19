package com.example.weather.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class AirAndPollen(
    val name: String = "",
    val value: Float = 0f,
    val category: String = "",
    val categoryValue: Float = 0f,
    val type: String = ""
)