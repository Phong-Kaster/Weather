package com.example.weather.domain.enums

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.example.weather.R

enum class ConditionType(
    @StringRes val fullName: Int,
    @DrawableRes val icon: Int,
    @DrawableRes val iconCircle: Int,
) {
    AIR_QUALITY(R.string.app_name, R.drawable.ic_air_quality, R.drawable.ic_air_quality_circle),
    HUMIDITY(R.string.app_name, R.drawable.ic_air_quality, R.drawable.ic_air_quality_circle),
    TEMPERATURE(R.string.app_name, R.drawable.ic_temperature, R.drawable.ic_air_quality_circle),
    PRECIPITATION(R.string.precipitation, R.drawable.ic_precipitation, R.drawable.ic_air_quality_circle),
    WIND(R.string.wind, R.drawable.ic_wind, R.drawable.ic_air_quality),
    RAIN(R.string.rain, R.drawable.ic_precipitation, R.drawable.ic_air_quality_circle),
    PRESSURE(R.string.pressure, R.drawable.ic_air_quality, R.drawable.ic_air_quality_circle),
    VISIBILITY(R.string.visibility, R.drawable.ic_air_quality, R.drawable.ic_air_quality_circle),
    UV_INDEX(R.string.uv_index, R.drawable.ic_air_quality, R.drawable.ic_air_quality_circle), NONE(0, 0, 0),
}