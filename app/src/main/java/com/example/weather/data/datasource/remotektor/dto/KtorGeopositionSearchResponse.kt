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
 * @author Phong-Kaster
 */
@Keep
@Serializable // <- this is the only required annotation, at the class level
data class KtorGeopositionSearchResponse(
    @SerialName("Version")  val Version: Int? = null,
    @SerialName("Key") val Key: String? = null,
    @SerialName("Type")  val Type: String? = null,
    @SerialName("Rank")  val Rank: Int? = null,
    @SerialName("LocalizedName")  val LocalizedName: String? = null,
    @SerialName("Country") val Country: KtorCountryDto? = null,
    @SerialName("AdministrativeArea") val AdministrativeArea: KtorAdministrativeAreaDto? = null
)