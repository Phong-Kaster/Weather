package com.example.jetpack.network.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable
/**
 * # [Serializable](https://levelup.gitconnected.com/serialization-with-kotlin-and-jetpack-compose-3ab36055fd59)
 * # DTO is Data transfer object
 *
 * @Serializable is required by Ktor
 * @SerializedName is required by Retrofit
 */
@Keep
@Serializable
data class AdministrativeAreaDto(
    @SerializedName("ID") val ID: String? = null,
    @SerializedName("LocalizedName") val LocalizedName: String? = null,
    @SerializedName("EnglishName") val EnglishName: String? = null,
    @SerializedName("Level") val Level: Int? = null,
    @SerializedName("LocalizedType") val LocalizedType: String? = null,
    @SerializedName("EnglishType") val EnglishType: String? = null,
    @SerializedName("CountryID") val CountryID: String? = null
) {
    companion object {
        fun getFakeAdministrativeArea(): AdministrativeAreaDto {
            return AdministrativeAreaDto(
                ID = "BB",
                LocalizedName = "Brandenburg",
                EnglishName = "Brandenburg",
                Level = 1,
                LocalizedType = "State",
                EnglishType = "State",
                CountryID = "DE"
            )
        }
    }
}