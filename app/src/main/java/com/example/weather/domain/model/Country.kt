package com.example.weather.domain.model

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
        fun getFakeCountry(): Country{
            return Country(id = "DE", localizedName = "Deutschland", englishName = "Germany")
        }
    }
}