package com.example.weather.domain.model

/**
 * # DTO is Data transfer object
 * @author Phong-Apero
 * this class is bases on AccuWeather API - Autocomplete Search
 * @see https://developer.accuweather.com/accuweather-locations-api/apis/get/locations/v1/cities/geoposition/search
 * */
data class LocationInfo(
    val id: Long = 0,
    val locationKey: String = "",
    val localizedName: String = "",
    val country: Country? = Country(),
    val administrativeArea: AdministrativeArea? = AdministrativeArea(),
    val timezone: String = "Europe/Berlin",
){

}