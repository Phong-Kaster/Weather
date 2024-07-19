package com.example.weather.data.datasource.remote.model

/**
 * # DTO is Data transfer object
 * @author Phong-Apero
 * this class is bases on AccuWeather API - Autocomplete Search
 * @see https://developer.accuweather.com/accuweather-locations-api/apis/get/locations/v1/cities/geoposition/search
 * */
data class LocationInfo(
    val version: Int? = 1,
    val key: Int? = null,
    val type: String? = null,
    val rank: Int? = null,
    val localizedName: String? = null,
    val country: Country? = null,
){

}