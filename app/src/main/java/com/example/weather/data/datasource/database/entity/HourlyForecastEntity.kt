package com.example.weather.data.datasource.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weather.domain.model.Value
import com.example.weather.domain.model.WindDirection
import kotlinx.parcelize.RawValue

/***********************************
 * # [24 Hours of Hourly Forecasts](https://developer.accuweather.com/accuweather-forecast-api/apis/get/forecasts/v1/hourly/24hour/%7BlocationKey%7D)
 */
@Entity(tableName = "table_hourly_forecast")
data class HourlyForecastEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    @ColumnInfo(name = "locationKey") val locationKey: String,
    @ColumnInfo(name = "epochDateTime") val epochDateTime: Long = 0,
    @ColumnInfo(name = "weatherIcon") val weatherIcon: Int = 0,
    @ColumnInfo(name = "temperature") val temperature: Float = 0f,
    @ColumnInfo(name = "feelTemperature") val feelTemperature: Float = 0f,
    @ColumnInfo(name = "dewPoint") val dewPoint: Float = 0f,
    @ColumnInfo(name = "windSpeed") val windSpeed: @RawValue Value,
    @ColumnInfo(name = "windDirection") val windDirection: @RawValue WindDirection,
    @ColumnInfo(name = "relativeHumidity") val relativeHumidity: Int = 0,
    @ColumnInfo(name = "visibility") var visibility: @RawValue Value,
    @ColumnInfo(name = "uvIndex") val uvIndex: Int,
    @ColumnInfo(name = "rain") val rain: @RawValue Value,
)