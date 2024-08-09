package com.example.weather.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weather.data.datasource.database.entity.LocationInfoEntity

@Dao
interface LocationInfoDao {

    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: LocationInfoEntity): Long

    // READ
    @Query("SELECT * FROM table_location_info WHERE table_location_info.locationKey LIKE :locationKey")
    suspend fun findByLocationKey(locationKey: String): List<LocationInfoEntity>

    @Query("SELECT * FROM table_location_info")
    suspend fun findAll(): List<LocationInfoEntity>

    // UPDATE
    @Update
    suspend fun update(entity: LocationInfoEntity)

    // DELETE
    @Query("DELETE FROM table_location_info WHERE table_location_info.locationKey =:locationKey")
    suspend fun deleteByLocationKey(locationKey: String)

    @Query("DELETE FROM table_location_info")
    suspend fun clear()
}