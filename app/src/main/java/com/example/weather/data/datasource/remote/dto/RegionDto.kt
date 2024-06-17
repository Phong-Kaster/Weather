package com.example.jetpack.network.dto

import com.google.gson.annotations.SerializedName
/**
 * # DTO is Data transfer object
 */
data class RegionDto (
    @SerializedName("ID"            ) val ID            : String? = null,
    @SerializedName("LocalizedName" ) val LocalizedName : String? = null,
    @SerializedName("EnglishName"   ) val EnglishName   : String? = null
)