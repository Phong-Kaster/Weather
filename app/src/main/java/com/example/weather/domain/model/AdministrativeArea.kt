package com.example.weather.domain.model

class AdministrativeArea(
    val ID: String? = "",
    val localizedName: String? = "",
    val englishName: String? = "",
    val level: Int? = 0,
    val localizedType: String? = "",
    val englishType: String? = "",
    val countryId: String? = ""
){
    companion object {
        fun getFakeAdministrativeArea(): AdministrativeArea {
            return AdministrativeArea(
                ID = "BB",
                localizedName = "Brandenburg",
                englishName = "Brandenburg",
                level = 1,
                localizedType = "State",
                englishType = "State",
                countryId = "DE"
            )
        }
    }
}