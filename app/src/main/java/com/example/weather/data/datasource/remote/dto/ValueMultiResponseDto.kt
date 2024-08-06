package com.example.weather.data.datasource.remote.dto

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ValueMultiResponseDto(
    @SerializedName("Metric")
    val metric: ValueResponseDto = ValueResponseDto(),

    @SerializedName("Imperial")
    val imperial: ValueResponseDto = ValueResponseDto()
) : Parcelable