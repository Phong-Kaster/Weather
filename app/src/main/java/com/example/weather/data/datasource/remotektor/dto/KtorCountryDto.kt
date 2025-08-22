package com.example.weather.data.datasource.remotektor.dto

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * # [Serializable](https://levelup.gitconnected.com/serialization-with-kotlin-and-jetpack-compose-3ab36055fd59)
 *
 * KtorGeopositionSearchResponse is Data transfer object (DTO) & the class is written to resolve
 * the response from AccuWeather Autocomplete Search - https://developer.accuweather.com/accuweather-locations-api/apis/get/locations/v1/cities/geoposition/search
 *
 * annotation @Serializable is required by Ktor - and it is applied to the whole class, not individual fields
 *
 * For Ktor/KotlinX: Use @Serializable and match names. Use @SerialName only when needed.
 * For Retrofit/Gson: Use @SerializedName on every DTO property
 *
 * @author Phong-Kaster
 */
@Keep
@Serializable
data class KtorCountryDto(
    @SerialName("ID") val ID: String? = null,
    @SerialName("LocalizedName") val localizedName: String? = null,
    @SerialName("EnglishName") val englishName: String? = null,
)