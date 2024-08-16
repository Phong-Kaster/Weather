package com.example.weather.data.datasource.remote.response

import androidx.annotation.Keep
import com.example.weather.data.datasource.remote.dto.ValueResponseDto
import com.example.weather.data.datasource.remote.dto.WindResponseDto
import com.google.gson.annotations.SerializedName

/***********************************
 * # [24 Hours of Hourly Forecasts](https://developer.accuweather.com/accuweather-forecast-api/apis/get/forecasts/v1/hourly/24hour/%7BlocationKey%7D)
 */
@Keep
data class HourlyForecastResponse(
    @SerializedName("DateTime") val date: String = "",
    @SerializedName("EpochDateTime") val epochDateTime: Long = 0,
    @SerializedName("WeatherIcon") val weatherIcon: Int = 0,
    @SerializedName("IconPhrase") val iconPhrase: String = "",
    @SerializedName("IsDaylight") val isDaylight: Boolean = false,
    @SerializedName("Temperature") val temperature: ValueResponseDto = ValueResponseDto(),
    @SerializedName("RealFeelTemperature") val feelTemperature: ValueResponseDto = ValueResponseDto(),
    @SerializedName("DewPoint") val dewPoint: ValueResponseDto = ValueResponseDto(),
    @SerializedName("Wind") val wind: WindResponseDto = WindResponseDto(),
    @SerializedName("RelativeHumidity") val relativeHumidity: Int = 0,
    @SerializedName("Visibility") val visibility: ValueResponseDto = ValueResponseDto(),
    @SerializedName("UVIndex") val uvIndex: Int = 1,
    @SerializedName("Rain") val rain: ValueResponseDto = ValueResponseDto(),
) {
}