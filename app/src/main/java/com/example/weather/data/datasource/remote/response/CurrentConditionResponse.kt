package com.example.weather.data.datasource.remote.response

import androidx.annotation.Keep
import com.example.weather.data.datasource.remote.dto.MetricAndImperialResponseDto
import com.example.weather.data.datasource.remote.dto.WindResponseDto
import com.google.gson.annotations.SerializedName

/***********************************
 * # [Current Conditions](https://developer.accuweather.com/accuweather-current-conditions-api/apis/get/currentconditions/v1/%7BlocationKey%7D)
 */
@Keep
data class CurrentConditionResponse(
    @SerializedName("WeatherText") val weatherText: String = "",

    @SerializedName("WeatherIcon") val weatherIcon: Int = 1,

    @SerializedName("HasPrecipitation") val hasPrecipitation: Boolean = false,

    @SerializedName("Temperature") val temperature: MetricAndImperialResponseDto = MetricAndImperialResponseDto(),

    @SerializedName("RealFeelTemperature") val feelTemperature: MetricAndImperialResponseDto = MetricAndImperialResponseDto(),

    @SerializedName("RelativeHumidity") val relativeHumidity: Float = 0f,

    @SerializedName("Wind") val wind: WindResponseDto = WindResponseDto(),

    @SerializedName("UVIndex") val uvIndex: Float = 0f,

    @SerializedName("Visibility") val visibility: MetricAndImperialResponseDto = MetricAndImperialResponseDto(),
)