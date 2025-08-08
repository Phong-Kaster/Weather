package com.example.weather.data.datasource.remotektor

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.Android
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

/**
 *
 */
val ktorHttpClient = HttpClient(
    engineFactory = Android,
    block = { // this is HttpClientConfig where installs plugin

        //1. Install the Content Negotiation plugin to handle JSON serialization/deserialization
        install(
            plugin = ContentNegotiation,
            configure = {
                json(
                    json = Json(
                        builderAction = {
                            prettyPrint = true
                            isLenient = true
                        }
                    )
                )
            })

        //2. Set request timeout in milliseconds by installing the HttpTimeout plugin
        install(
            plugin = HttpTimeout,
            configure = { requestTimeoutMillis = 100000 },
        )

        //3. 
    }
)