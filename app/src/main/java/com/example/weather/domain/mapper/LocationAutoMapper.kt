package com.example.weather.domain.mapper

import com.example.weather.data.datasource.remote.response.LocationAutoResponse
import com.example.weather.domain.model.AdministrativeArea
import com.example.weather.domain.model.Country
import com.example.weather.domain.model.LocationAuto

object LocationAutoMapper {
    fun LocationAutoResponse.toModel() : LocationAuto {
        return LocationAuto(
            version = this.Version,
            key = this.Key,
            type = this.Type,
            rank = this.Rank,
            localizedName = this.LocalizedName,
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
            )
        )
    }
}