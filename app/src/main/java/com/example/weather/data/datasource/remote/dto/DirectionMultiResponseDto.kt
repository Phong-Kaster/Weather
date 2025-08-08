package com.example.weather.data.datasource.remote.dto

import com.google.gson.annotations.SerializedName

/**
 * # [Serializable](https://levelup.gitconnected.com/serialization-with-kotlin-and-jetpack-compose-3ab36055fd59)
 */
data class DirectionMultiResponseDto(
    @SerializedName("Degrees") val degrees: Int = 0,

    @SerializedName("Localized") val localized: String = "",

    @SerializedName("English") val english: String = ""
)