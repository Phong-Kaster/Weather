package com.example.weather.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weather.data.datasource.database.entity.HourlyForecastEntity


@Dao
interface HourlyForecastDao {

    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: HourlyForecastEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(entities: List<HourlyForecastEntity>)

    // READ
    @Query("SELECT * FROM table_hourly_forecast WHERE table_hourly_forecast.locationKey LIKE :locationKey")
    suspend fun findByLocationKey(locationKey: String): List<HourlyForecastEntity>

    // UPDATE
    @Update
    suspend fun update(entity: HourlyForecastEntity)

    // DELETE
    @Query("DELETE FROM table_hourly_forecast WHERE table_hourly_forecast.locationKey =:locationKey")
    suspend fun clear(locationKey: String)
}