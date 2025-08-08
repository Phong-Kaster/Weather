package com.example.jetpack.network.dto

import com.google.gson.annotations.SerializedName

/**
 * # [Serializable](https://levelup.gitconnected.com/serialization-with-kotlin-and-jetpack-compose-3ab36055fd59)
 * # DTO is Data transfer object
 */
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