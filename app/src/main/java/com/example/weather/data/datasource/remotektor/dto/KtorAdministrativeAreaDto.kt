package com.example.weather.data.datasource.remotektor.dto

import androidx.annotation.Keep
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * For Ktor/KotlinX: Use @Serializable and match names. Use @SerialName only when needed.
 * For Retrofit/Gson: Use @SerializedName on every DTO property
 */
@Keep
@Serializable
data class KtorAdministrativeAreaDto(
    @SerialName("ID") val ID: String? = null,
    @SerialName("LocalizedName") val LocalizedName: String? = null,
    @SerialName("EnglishName") val EnglishName: String? = null,
    @SerialName("Level") val Level: Int? = null,
    @SerialName("LocalizedType") val LocalizedType: String? = null,
    @SerialName("EnglishType") val EnglishType: String? = null,
    @SerialName("CountryID") val CountryID: String? = null
)