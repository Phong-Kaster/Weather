package com.example.weather.data.datasource.database.converter

import androidx.room.TypeConverter
import com.example.weather.domain.model.AdministrativeArea
import com.example.weather.domain.model.Country
import com.example.weather.domain.model.Value
import com.example.weather.domain.model.WindDirection
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.Date

object WeatherConverter {
    /**********************************
     * DATE
     * */
    @TypeConverter
    @JvmStatic
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    @JvmStatic
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time
    }

    // Value
    @TypeConverter
    @JvmStatic
    fun valueFromJsonString(json: String?): Value {
        val listType = object : TypeToken<Value?>() {}.type
        return if (json == null) {
            Value()
        } else {
            Gson().fromJson(json, listType)
        }
    }

    @TypeConverter
    @JvmStatic
    fun jsonStingFromValue(value: Value?): String {
        val gson = Gson()
        return gson.toJson(value)
    }

    // Wind direction
    @TypeConverter
    @JvmStatic
    fun directionFromJsonString(json: String?): WindDirection {
        val listType = object : TypeToken<WindDirection?>() {}.type
        return Gson().fromJson(json, listType)
    }

    @TypeConverter
    @JvmStatic
    fun jsonStingFromDirection(windDirection: WindDirection?): String {
        val gson = Gson()
        return gson.toJson(windDirection)
    }

    // Country
    @TypeConverter
    @JvmStatic
    fun countryFromJsonString(json: String?): Country {
        val listType = object : TypeToken<Country?>() {}.type
        return Gson().fromJson(json, listType)
    }

    @TypeConverter
    @JvmStatic
    fun jsonStingFromCountry(country: Country?): String {
        val gson = Gson()
        return gson.toJson(country)
    }


    // Administrative Area
    @TypeConverter
    @JvmStatic
    fun administrativeAreaFromJsonString(json: String?): AdministrativeArea {
        val listType = object : TypeToken<AdministrativeArea?>() {}.type
        return Gson().fromJson(json, listType)
    }

    @TypeConverter
    @JvmStatic
    fun jsonStingFromAdministrativeArea(administrativeArea: AdministrativeArea?): String {
        val gson = Gson()
        return gson.toJson(administrativeArea)
    }
}