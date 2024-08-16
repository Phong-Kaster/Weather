package com.example.weather.domain.model

import kotlin.random.Random

/**
 * # [Snowfall Effect](https://proandroiddev.com/animating-inside-and-outside-the-box-with-jetpack-compose-a56eba1b6af6)
 */
data class Snowflake(
    var x: Float,
    var y: Float,
    var radius: Float,
    var speed: Float
){
    companion object {
        fun generateRandomSnowflake(): Snowflake {
            return Snowflake(
                x = Random.nextFloat(),
                y = Random.nextFloat() * 1000f,
                radius = Random.nextFloat() * 2f + 2f, // Snowflake size
                speed = Random.nextFloat() * 1.2f + 1f  // Falling speed
            )
        }
    }
}