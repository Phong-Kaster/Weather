package com.example.weather.data.datasource.remote.dto

import com.google.gson.annotations.SerializedName

data class WindResponseDto(
    @SerializedName("Speed") val speed: MetricAndImperialResponseDto = MetricAndImperialResponseDto(),

    @SerializedName("Direction") val direction: DirectionMultiResponseDto = DirectionMultiResponseDto(),
)