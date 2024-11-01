package com.example.weather.util

import com.example.weather.R
import com.example.weather.WeatherApplication
import com.example.weather.configuration.Constant
import com.example.weather.data.repository.SettingRepository
import com.example.weather.injection.EntryPointRepository
import dagger.hilt.android.EntryPointAccessors
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

object WeatherUtil {

    private val settingRepository: SettingRepository = EntryPointAccessors
        .fromApplication<EntryPointRepository>(WeatherApplication.application)
        .settingRepository()

    fun Float.toReadableTemperature(): String {
        val isCelsiusEnabled = settingRepository.isCelsiusEnabled()

        if (isCelsiusEnabled) {
            val celsiusValue = ((this - 32) * 0.56F).roundToInt()
            return "$celsiusValue ${Constant.CELSIUS_SYMBOL}"
        } else {
            return "${this.roundToInt()} ${Constant.FAHRENHEIT_SYMBOL}"
        }
    }

    fun Float.toCalculableTemperature(): Int {
        val isCelsiusEnabled = settingRepository.isCelsiusEnabled()

        return if (isCelsiusEnabled) {
            ((this - 32) * 0.56F).roundToInt()
        } else {
            this.roundToInt()
        }
    }

    fun Long.toTime(): String {


        val dateTime = LocalDateTime.ofInstant(Instant.ofEpochSecond(this), ZoneId.systemDefault())
        val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
        val formattedDate = dateTime.format(formatter)
        return formattedDate
    }


    fun convertToWeatherIcon(weatherIcon: Int): Int {
        return when (weatherIcon) {
            1 -> R.drawable.ic_01
            2 -> R.drawable.ic_02
            3 -> R.drawable.ic_03
            4 -> R.drawable.ic_04
            5 -> R.drawable.ic_05
            6 -> R.drawable.ic_06
            7 -> R.drawable.ic_07
            8 -> R.drawable.ic_08
            11 -> R.drawable.ic_11
            12 -> R.drawable.ic_12
            13 -> R.drawable.ic_13
            14 -> R.drawable.ic_14
            15 -> R.drawable.ic_15
            16 -> R.drawable.ic_16
            17 -> R.drawable.ic_17
            18 -> R.drawable.ic_18
            19 -> R.drawable.ic_19
            20 -> R.drawable.ic_20
            21 -> R.drawable.ic_21
            22 -> R.drawable.ic_22
            23 -> R.drawable.ic_23
            24 -> R.drawable.ic_24
            25 -> R.drawable.ic_25
            26 -> R.drawable.ic_26
            29 -> R.drawable.ic_29
            30 -> R.drawable.ic_30
            31 -> R.drawable.ic_31
            32 -> R.drawable.ic_32
            33 -> R.drawable.ic_33
            34 -> R.drawable.ic_34
            35 -> R.drawable.ic_35
            36 -> R.drawable.ic_36
            37 -> R.drawable.ic_37
            38 -> R.drawable.ic_38
            39 -> R.drawable.ic_39
            40 -> R.drawable.ic_40
            41 -> R.drawable.ic_41
            42 -> R.drawable.ic_42
            43 -> R.drawable.ic_43
            else -> R.drawable.ic_44
        }
    }
}