package com.example.jetpack.network.dto

import com.google.gson.annotations.SerializedName
/**
 * # [Serializable](https://levelup.gitconnected.com/serialization-with-kotlin-and-jetpack-compose-3ab36055fd59)
 * # DTO is Data transfer object
 */
data class ImperialDto (
    @SerializedName("Value"    ) val Value    : Int?    = null,
    @SerializedName("Unit"     ) val Unit     : String? = null,
    @SerializedName("UnitType" ) val UnitType : Int?    = null
)