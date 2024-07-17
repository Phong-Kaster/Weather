package com.example.weather.data.datasource.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "table_administrative_area")
data class AdministrativeAreaEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
)