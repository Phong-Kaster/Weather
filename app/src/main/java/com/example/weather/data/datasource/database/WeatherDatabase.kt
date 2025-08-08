package com.example.weather.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weather.data.datasource.database.converter.WeatherConverter
import com.example.weather.data.datasource.database.dao.CurrentConditionDao
import com.example.weather.data.datasource.database.dao.HourlyForecastDao
import com.example.weather.data.datasource.database.dao.LocationInfoDao
import com.example.weather.data.datasource.database.entity.CurrentConditionEntity
import com.example.weather.data.datasource.database.entity.HourlyForecastEntity
import com.example.weather.data.datasource.database.entity.LocationInfoEntity
import com.example.weather.data.datasource.remote.dto.LocationInfoDto
import com.example.weather.domain.model.HourlyForecast

@TypeConverters(WeatherConverter::class)
@Database(
    entities = [
        LocationInfoEntity::class,
        CurrentConditionEntity::class,
        HourlyForecastEntity::class,
    ],
    version = 1,
    exportSchema = false,
)

abstract class WeatherDatabase : RoomDatabase() {
    abstract fun currentConditionDao(): CurrentConditionDao
    abstract fun locationInfoDao(): LocationInfoDao
    abstract fun hourlyForecastDao(): HourlyForecastDao
}