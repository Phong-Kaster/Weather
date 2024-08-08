package com.example.weather.data.datasource.remote.response

import androidx.annotation.Keep
import com.example.jetpack.network.dto.AdministrativeAreaDto
import com.example.jetpack.network.dto.CountryDto
import com.google.gson.annotations.SerializedName


/**
 * # DTO is Data transfer object
 * @author Phong-Apero
 * this class is bases on AccuWeather API - Autocomplete Search
 * @see https://developer.accuweather.com/accuweather-locations-api/apis/get/locations/v1/cities/geoposition/search
 * */
@Keep
data class LocationAutoResponse(
    @SerializedName("Version"            ) val Version            : Int?                = null,
    @SerializedName("Key"                ) val Key                : String?             = null,
    @SerializedName("Type"               ) val Type               : String?             = null,
    @SerializedName("Rank"               ) val Rank               : Int?                = null,
    @SerializedName("LocalizedName"      ) val LocalizedName      : String?             = null,
    @SerializedName("Country"            ) val CountryDto            : CountryDto?            = CountryDto(),
    @SerializedName("AdministrativeArea" ) val AdministrativeAreaDto : AdministrativeAreaDto? = AdministrativeAreaDto()
){
    companion object{
        fun getFakeData(): List<LocationAutoResponse> {

            val countryDto = CountryDto.getFakeCountry()
            val administrativeAreaDto = AdministrativeAreaDto.getFakeAdministrativeArea()

            return listOf(
                LocationAutoResponse(Version = 1, Key = "1024259", Type ="City", Rank = 63, LocalizedName = "Glienicke/Nordbahn", CountryDto = countryDto, AdministrativeAreaDto = administrativeAreaDto),
                LocationAutoResponse(Version = 1, Key = "995487", Type ="City", Rank = 75, LocalizedName = "Schildow", CountryDto = countryDto, AdministrativeAreaDto = administrativeAreaDto),
                LocationAutoResponse(Version = 1, Key = "173535", Type ="City", Rank = 83, LocalizedName = "Lindenberg", CountryDto = countryDto, AdministrativeAreaDto = administrativeAreaDto),
                LocationAutoResponse(Version = 1, Key = "173570", Type ="City", Rank = 83, LocalizedName = "Mühlenbeck", CountryDto = countryDto, AdministrativeAreaDto = administrativeAreaDto),
                LocationAutoResponse(Version = 1, Key = "2599961", Type ="City", Rank = 85, LocalizedName = "Birkholz", CountryDto = countryDto, AdministrativeAreaDto = administrativeAreaDto),
                LocationAutoResponse(Version = 1, Key = "2599879", Type ="City", Rank = 85, LocalizedName = "Gartenstadt Großziethen", CountryDto = countryDto, AdministrativeAreaDto = administrativeAreaDto),
                LocationAutoResponse(Version = 1, Key = "167900", Type ="City", Rank = 85, LocalizedName = "Großziethen", CountryDto = countryDto, AdministrativeAreaDto = administrativeAreaDto),
                LocationAutoResponse(Version = 1, Key = "3557587", Type ="City", Rank = 85, LocalizedName = "Klarahöh", CountryDto = countryDto, AdministrativeAreaDto = administrativeAreaDto),
                LocationAutoResponse(Version = 1, Key = "1013761", Type ="City", Rank = 85, LocalizedName = "Kleinziethen", CountryDto = countryDto, AdministrativeAreaDto = administrativeAreaDto),
            )
        }
    }
}