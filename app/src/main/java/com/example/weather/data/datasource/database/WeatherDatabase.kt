package com.example.weather.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weather.data.datasource.database.dao.WeatherDao
import com.example.weather.data.datasource.database.entity.AdministrativeAreaEntity

@Database(
    entities = [AdministrativeAreaEntity::class],
    version = 1,
    exportSchema = true,
)
abstract class WeatherDatabase : RoomDatabase() {
    abstract fun wifiQRDao(): WeatherDao
}