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
//    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @PrimaryKey @ColumnInfo(name = "locationKey") var locationKey: String,
    @ColumnInfo(name = "date") var date: Date,
    @ColumnInfo(name = "weatherText") var weatherText: String,
    @ColumnInfo(name = "weatherIcon") var weatherIcon: Int,
    @ColumnInfo(name = "hasPrecipitation") val hasPrecipitation: Boolean,
    @ColumnInfo(name = "temperature") var temperature: Float,
    @ColumnInfo(name = "feelTemperature") var feelTemperature: Float,
    @ColumnInfo(name = "relativeHumidity") var relativeHumidity: Float,
    @ColumnInfo(name = "windSpeed") var windSpeed: @RawValue Value,
    @ColumnInfo(name = "windDirection") var windDirection: @RawValue WindDirection,
    @ColumnInfo(name = "uvIndex") var uvIndex: Float,
    @ColumnInfo(name = "visibility") var visibility : @RawValue Value,
){
}