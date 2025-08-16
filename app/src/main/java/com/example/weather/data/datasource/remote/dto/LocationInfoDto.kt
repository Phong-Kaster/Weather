package com.example.weather.data.datasource.remote.dto

import android.graphics.Region
import androidx.annotation.Keep
import com.example.jetpack.network.dto.GeoPositionDto
import com.example.jetpack.network.dto.ParentCityDto
import com.example.jetpack.network.dto.TimeZoneDto
import com.google.gson.annotations.SerializedName
import java.io.Serializable
import java.util.UUID

/**
 * # [Serializable](https://levelup.gitconnected.com/serialization-with-kotlin-and-jetpack-compose-3ab36055fd59)
* @author Phong-Apero
* this class is bases on AccuWeather API - GeopositionSearch
* @see https://developer.accuweather.com/accuweather-locations-api/apis/get/locations/v1/cities/geoposition/search
* */
@Keep
class LocationInfoDto(
    @SerializedName("Version") val Version: Int? = null,
    @SerializedName("Key") val Key: String? = UUID.randomUUID().toString(),
    @SerializedName("Type") val Type: String? = null,
    @SerializedName("LocalizedName") val LocalizedName: String? = null,
    @SerializedName("EnglishName") val EnglishName: String? = null,
    @SerializedName("Region") val Region: Region? = Region(),
    @SerializedName("Country") val Country: CountryDto? = CountryDto(),
    @SerializedName("AdministrativeArea") val AdministrativeArea: AdministrativeAreaDto? = AdministrativeAreaDto(),
    @SerializedName("TimeZone") val TimeZone: TimeZoneDto? = TimeZoneDto(),
    @SerializedName("GeoPosition") val GeoPosition: GeoPositionDto? = GeoPositionDto(),
    @SerializedName("IsAlias") val IsAlias: Boolean? = null,
    @SerializedName("ParentCity") val ParentCity: ParentCityDto? = ParentCityDto(),
): Serializable

