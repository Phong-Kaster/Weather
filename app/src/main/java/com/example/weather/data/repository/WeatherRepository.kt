package com.example.weather.data.repository

import android.util.Log
import com.example.jetpack.network.dto.LocationAutoDto
import com.example.jetpack.network.dto.LocationGeoDto
import com.example.weather.data.datasource.database.WeatherDatabase
import com.example.weather.data.datasource.database.dao.WeatherDao
import com.example.weather.data.datasource.remote.WeatherApi
import com.example.weather.data.datasource.remote.dto.LocationInfoDto
import com.example.weather.util.ApiUtil
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository
@Inject
constructor(
    private val weatherDatabase: WeatherDatabase,
    private val weatherApi: WeatherApi
){
    private val TAG = "WeatherRepository"
    private val weatherDao = weatherDatabase.weatherDao()

    suspend fun searchGeoposition(lntLng: String) : LocationGeoDto {
        return ApiUtil.fetchDataBody {  weatherApi.searchGeoposition(lnglat = lntLng) }
    }

    suspend fun searchAutocomplete(keyword: String): List<LocationAutoDto> {
        val response = ApiUtil.fetchDataBody { weatherApi.searchAutocomplete(keyword = keyword) }
        Log.d(TAG, "searchAutocomplete - response: $response")
        return response
    }
}