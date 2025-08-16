package com.example.weather.data.datasource.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class KtorCountryDto (
    val ID: String? = null,
    val LocalizedName: String? = null,
    val EnglishName: String? = null
)
