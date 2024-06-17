package com.example.jetpack.network.dto

import com.google.gson.annotations.SerializedName
/**
 * # DTO is Data transfer object
 */
data class ElevationDto (
    @SerializedName("Metric"   ) val MetricDto   : MetricDto?   = MetricDto(),
    @SerializedName("Imperial" ) val ImperialDto : ImperialDto? = ImperialDto()
)