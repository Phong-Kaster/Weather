package com.example.jetpack.network.dto

import androidx.annotation.Keep
import com.example.weather.data.datasource.remote.dto.AdministrativeAreaDto
import com.example.weather.data.datasource.remote.dto.CountryDto
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.UUID


/**
 * # [Serializable](https://levelup.gitconnected.com/serialization-with-kotlin-and-jetpack-compose-3ab36055fd59)
 * # DTO is Data transfer object
 * @author Phong-Apero
 * this class is bases on AccuWeather API - Geoposition Search
 * @see https://developer.accuweather.com/accuweather-locations-api/apis/get/locations/v1/cities/geoposition/search
 * */
@Keep
class LocationGeoDto(
    @SerializedName("Version") val Version: Int? = null,
    @SerializedName("Key") val Key: String? = UUID.randomUUID().toString(),
    @SerializedName("Type") val Type: String? = null,
    @SerializedName("LocalizedName") val LocalizedName: String? = null,
    @SerializedName("EnglishName") val EnglishName: String? = null,
    @SerializedName("Region") val RegionDto: RegionDto? = RegionDto(),
    @SerializedName("Country") val CountryDto: CountryDto? = CountryDto(),
    @SerializedName("AdministrativeArea") val AdministrativeAreaDto: AdministrativeAreaDto? = AdministrativeAreaDto(),
    @SerializedName("TimeZone") val TimeZoneDto: TimeZoneDto? = TimeZoneDto(),
    @SerializedName("GeoPosition") val GeoPositionDto: GeoPositionDto? = GeoPositionDto(),
    @SerializedName("IsAlias") val IsAlias: Boolean? = null,
    @SerializedName("ParentCity") val ParentCityDto: ParentCityDto? = ParentCityDto(),
): Serializable


