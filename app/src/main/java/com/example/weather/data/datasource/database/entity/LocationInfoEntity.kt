package com.example.weather.data.datasource.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weather.domain.model.AdministrativeArea
import com.example.weather.domain.model.Country

@Entity(tableName = "table_location_info")
data class LocationInfoEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "locationKey") val locationKey: String = "",
    @ColumnInfo(name = "localizedName") val localizedName: String = "",
    @ColumnInfo(name = "country") val country: Country? = Country(),
    @ColumnInfo(name = "administrativeArea") val administrativeArea: AdministrativeArea? = AdministrativeArea(),
)