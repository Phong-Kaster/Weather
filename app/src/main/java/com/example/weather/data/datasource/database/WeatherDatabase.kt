package com.example.weather.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.weather.data.datasource.database.converter.WeatherConverter
import com.example.weather.data.datasource.database.dao.CurrentConditionDao
import com.example.weather.data.datasource.database.entity.CurrentConditionEntity

@TypeConverters(WeatherConverter::class)
@Database(
    entities = [CurrentConditionEntity::class],
    version = 1,
    exportSchema = true,
)

abstract class WeatherDatabase : RoomDatabase() {
    abstract fun currentConditionDao(): CurrentConditionDao
}