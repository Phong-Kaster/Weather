package com.example.weather.data.repository

import com.example.weather.data.datasource.database.dao.WeatherDao
import com.example.weather.data.datasource.remote.WeatherApi
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WeatherRepository
@Inject
constructor(
    private val weatherDatabase: WeatherDao,
    private val weatherApi: WeatherApi
) {

}