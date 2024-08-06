package com.example.weather.data.datasource.remote.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ValueResponseDto(
    @SerializedName("Value")
    val value: Float = 0f,

    @SerializedName("Unit")
    val unit: String = "",

    @SerializedName("UnitType")
    val unitType: Int = 0
) : Parcelable