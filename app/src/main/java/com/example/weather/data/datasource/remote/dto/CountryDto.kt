package com.example.weather.data.datasource.remote.dto

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

/**
 * # [Serializable](https://levelup.gitconnected.com/serialization-with-kotlin-and-jetpack-compose-3ab36055fd59)
 * # DTO is Data transfer object
 * @Serializable is required by Ktor
 * @SerializedName is required by Retrofit
 *
 * For Ktor/KotlinX: Use @Serializable and match names. Use @SerialName only when needed.
 * For Retrofit/Gson: Use @SerializedName on every DTO property
 */
@Keep
@Serializable
data class CountryDto (
    @SerializedName("ID"            ) val ID            : String? = null,
    @SerializedName("LocalizedName" ) val LocalizedName : String? = null,
    @SerializedName("EnglishName"   ) val EnglishName   : String? = null
){
    companion object{
        fun getFakeCountry(): CountryDto{
            return CountryDto(ID = "DE", LocalizedName = "Deutschland", EnglishName = "Germany")
        }
    }
}