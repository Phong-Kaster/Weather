package com.example.weather.data.datasource.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weather.domain.model.Value
import com.example.weather.domain.model.WindDirection
import kotlinx.parcelize.RawValue
import java.util.Date

/***********************************
 * # [Current Conditions](https://developer.accuweather.com/accuweather-current-conditions-api/apis/get/currentconditions/v1/%7BlocationKey%7D)
 */
@Entity(tableName = "table_current_condition")
data class CurrentConditionEntity(
    @PrimaryKey @ColumnInfo(name = "locationKey") var locationKey: String,
    @ColumnInfo(name = "date") var date: Date,
    @ColumnInfo(name = "weatherText") var weatherText: String,
    @ColumnInfo(name = "weatherIcon") val weatherIcon: Int,
    @ColumnInfo(name = "hasPrecipitation") val hasPrecipitation: Boolean,
    @ColumnInfo(name = "temperature") val temperature: Float,
    @ColumnInfo(name = "feelTemperature") val feelTemperature: Float,
    @ColumnInfo(name = "relativeHumidity") val relativeHumidity: Float,
    @ColumnInfo(name = "windSpeed") val windSpeed: @RawValue Value,
    @ColumnInfo(name = "windDirection") val windDirection: @RawValue WindDirection,
    @ColumnInfo(name = "uvIndex") val uvIndex: Float,
    @ColumnInfo(name = "visibility") val visibility : @RawValue Value,
){
}