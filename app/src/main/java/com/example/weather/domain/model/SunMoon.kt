package com.example.weather.domain.model

import androidx.compose.runtime.Immutable
import java.util.Date

@Immutable
data class SunMoon(
    val rise: Date? = null,
    val epochRise: Long? = 0,
    val set: Date? = null,
    val epochSet: Long? = 0,
    val phase: String? = "",
)