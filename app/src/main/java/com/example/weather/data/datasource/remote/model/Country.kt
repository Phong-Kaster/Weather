package com.example.weather.data.datasource.remote.model

import com.example.jetpack.network.dto.CountryDto

/**
 * # DTO is Data transfer object
 */
data class Country(
    val id: String? = null,
    val localizedName: String? = null,
    val englishName: String? = null,
){
    companion object{
        fun getFakeCountry(): CountryDto {
            return CountryDto(ID = "DE", LocalizedName = "Deutschland", EnglishName = "Germany")
        }
    }
}