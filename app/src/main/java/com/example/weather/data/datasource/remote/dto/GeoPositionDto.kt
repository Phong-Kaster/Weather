package com.example.jetpack.network.dto

import com.google.gson.annotations.SerializedName
/**
 * # DTO is Data transfer object
 */
data class GeoPositionDto (
    @SerializedName("Latitude"  ) val Latitude  : Double?    = null,
    @SerializedName("Longitude" ) val Longitude : Double?    = null,
    @SerializedName("Elevation" ) val ElevationDto : ElevationDto? = ElevationDto()
)