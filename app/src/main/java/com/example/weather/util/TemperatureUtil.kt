package com.example.weather.util

import com.example.weather.WeatherApplication
import com.example.weather.configuration.Constant
import com.example.weather.data.repository.SettingRepository
import com.example.weather.injection.EntryPointRepository
import dagger.hilt.android.EntryPointAccessors
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import kotlin.math.roundToInt

object TemperatureUtil {

    private val settingRepository: SettingRepository = EntryPointAccessors
        .fromApplication<EntryPointRepository>(WeatherApplication.application)
        .settingRepository()

    fun Float.toCorrespondingTemperatureUnit(): String {
        val isCelsiusEnabled = settingRepository.isCelsiusEnabled()

        if (isCelsiusEnabled) {
            val celsiusValue = ((this - 32) * 0.56F).roundToInt()
            return "$celsiusValue ${Constant.CELSIUS_SYMBOL}"
        } else {
            return "${this.roundToInt()} ${Constant.FAHRENHEIT_SYMBOL}"
        }
    }

    fun Long.toTime(): String {


        val dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(this), ZoneId.systemDefault())
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val formattedDate = dateTime.format(formatter)
        return formattedDate
    }



}