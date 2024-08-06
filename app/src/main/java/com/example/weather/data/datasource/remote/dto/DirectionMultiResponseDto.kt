package com.example.weather.data.datasource.remote.dto

import com.google.gson.annotations.SerializedName

data class DirectionMultiResponseDto(
    @SerializedName("Degrees") val degrees: Int = 0,

    @SerializedName("Localized") val localized: String = "",

    @SerializedName("English") val english: String = ""
)