package com.example.weather.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Wind(
    val speed: Value = Value(),
    val direction: Direction = Direction()
)