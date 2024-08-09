package com.example.weather.domain.status

sealed class Status<T>(
    val data: T? = null,
    val message: String? = null
) {
    class Success<T>(data: T?): Status<T>(data)

    class Failure<T>(data: T? = null, message: String?): Status<T>(data = data, message= message)

    class Loading<T>(val enabled: Boolean = true): Status<T>(data = null, message = null)
}