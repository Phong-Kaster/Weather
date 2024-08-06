package com.example.weather.data.datasource.remote.dto

import com.google.gson.annotations.SerializedName

data class WindMultiResponseDto(
    @SerializedName("Speed") val speed: ValueMultiResponseDto = ValueMultiResponseDto(),

    @SerializedName("Direction") val direction: DirectionMultiResponseDto = DirectionMultiResponseDto()
)