package com.example.weather.domain.mapper

import com.example.weather.data.datasource.remote.response.SearchByLocationKeyResponse
import com.example.weather.domain.model.AdministrativeArea
import com.example.weather.domain.model.Country
import com.example.weather.domain.model.LocationInfo

object SearchByLocationKeyMapper {
    fun SearchByLocationKeyResponse.toLocationInfoModel(): LocationInfo {
        return LocationInfo(
            id = 0,
            locationKey = this.Key ?: "",
            localizedName = this.LocalizedName ?: "Germany",
            country = Country(
                id = this.CountryDto?.ID,
                localizedName = this.CountryDto?.LocalizedName,
                englishName = this.CountryDto?.EnglishName
            ),
            administrativeArea = AdministrativeArea(
                ID = this.AdministrativeAreaDto?.ID,
                localizedName = this.AdministrativeAreaDto?.LocalizedName,
                englishName = this.AdministrativeAreaDto?.EnglishName,
                level = this.AdministrativeAreaDto?.Level,
                localizedType =  this.AdministrativeAreaDto?.LocalizedType,
                englishType = this.AdministrativeAreaDto?.EnglishType,
                countryId = this.AdministrativeAreaDto?.CountryID
            ),
            timezone = this.TimeZone?.Name ?: "Europe/Berlin"
        )
    }
}