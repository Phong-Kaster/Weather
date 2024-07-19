package com.example.weather.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Direction(
    val degree: Int = 0,
    val localized: String = "SSE",
    val english: String = ""
)