package com.example.weather.domain.status

sealed class Status<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?): Status<T>(data)

    class Failure<T>(data: T? = null, message: String?): Status<T>(data, message)

    class Loading<T>(val showLoading: Boolean = true): Status<T>(null)
}