package com.example.jetpack.network.dto

import com.google.gson.annotations.SerializedName

/**
 * # [Serializable](https://levelup.gitconnected.com/serialization-with-kotlin-and-jetpack-compose-3ab36055fd59)
 * # DTO is Data transfer object
 */
data class GeoPositionDto(
    @SerializedName("Latitude") val Latitude: Double? = null,
    @SerializedName("Longitude") val Longitude: Double? = null,
    @SerializedName("Elevation") val ElevationDto: ElevationDto? = ElevationDto()
)