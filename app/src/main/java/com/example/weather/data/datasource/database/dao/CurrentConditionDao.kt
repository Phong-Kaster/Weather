package com.example.weather.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.weather.data.datasource.database.entity.CurrentConditionEntity
import com.example.weather.data.datasource.remote.response.CurrentConditionResponse
import com.example.weather.domain.model.CurrentCondition

@Dao
interface CurrentConditionDao {
    // CREATE
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: CurrentConditionEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMany(entities: List<CurrentConditionEntity>)

    // READ
    @Query("SELECT * FROM table_current_condition WHERE table_current_condition.locationKey LIKE :locationKey")
    suspend fun findByLocationKey(locationKey: String): List<CurrentConditionEntity>

    // UPDATE
    @Update
    suspend fun update(entity: CurrentConditionEntity)

    // DELETE
    @Query("DELETE FROM table_current_condition WHERE table_current_condition.locationKey =:locationKey")
    suspend fun deleteByLocationKey(locationKey: String)

    @Query("DELETE FROM table_current_condition")
    suspend fun clear()
}