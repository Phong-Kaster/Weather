package com.example.weather.data.datasource.remote.response

import androidx.annotation.Keep
import com.example.weather.data.datasource.remote.dto.ValueMultiResponseDto
import com.example.weather.data.datasource.remote.dto.WindMultiResponseDto
import com.google.gson.annotations.SerializedName

@Keep
data class CurrentForecastResponse(
    @SerializedName("WeatherText") val weatherText: String = "",

    @SerializedName("WeatherIcon") val weatherIcon: Int = 1,

    @SerializedName("IsDayTime") val isDayTime: Boolean = true,

    @SerializedName("Temperature") val temperature: ValueMultiResponseDto = ValueMultiResponseDto(),

    @SerializedName("RealFeelTemperature") val feelTemperature: ValueMultiResponseDto = ValueMultiResponseDto(),

    @SerializedName("RelativeHumidity") val relativeHumidity: Float = 0f,

    @SerializedName("Wind") val wind: WindMultiResponseDto = WindMultiResponseDto(),

    @SerializedName("UVIndex") val uvIndex: Float = 0f,

    @SerializedName("UVIndexText") val uvIndexText: String = "",

    @SerializedName("Visibility") val visibility: ValueMultiResponseDto = ValueMultiResponseDto(),

    @SerializedName("Pressure") val pressure: ValueMultiResponseDto = ValueMultiResponseDto(),

    @SerializedName("HasPrecipitation") val hasPrecipitation: Boolean = false,
)