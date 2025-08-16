package com.example.weather.data.datasource.remote.response

import androidx.annotation.Keep
import com.example.weather.data.datasource.remote.dto.AdministrativeAreaDto
import com.example.weather.data.datasource.remote.dto.CountryDto
import com.example.jetpack.network.dto.TimeZoneDto
import com.google.gson.annotations.SerializedName

/**
 * # [Serializable](https://levelup.gitconnected.com/serialization-with-kotlin-and-jetpack-compose-3ab36055fd59)
 * # DTO is Data transfer object
 * @author Phong-Kaster
 * this class is bases on AccuWeather API - Search by locationKey
 * @see https://developer.accuweather.com/accuweather-locations-api/apis/get/locations/v1/%7BlocationKey%7D
 * */
@Keep
data class SearchByLocationKeyResponse(
    @SerializedName("Version"            ) val Version            : Int?                = null,
    @SerializedName("Key"                ) val Key                : String?             = null,
    @SerializedName("Type"               ) val Type               : String?             = null,
    @SerializedName("Rank"               ) val Rank               : Int?                = null,
    @SerializedName("LocalizedName"      ) val LocalizedName      : String?             = null,
    @SerializedName("EnglishName"      )   val EnglishName      : String?             = null,
    @SerializedName("Country"            ) val CountryDto            : CountryDto?            = CountryDto(),
    @SerializedName("AdministrativeArea" ) val AdministrativeAreaDto : AdministrativeAreaDto? = AdministrativeAreaDto(),
    @SerializedName("TimeZone"           ) val TimeZone: TimeZoneDto? = TimeZoneDto(),
)