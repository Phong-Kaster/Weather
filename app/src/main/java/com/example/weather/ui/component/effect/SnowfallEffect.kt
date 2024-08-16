package com.example.weather.ui.component.effect

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.weather.domain.model.Snowflake.Companion.generateRandomSnowflake

/**
 * # [Snowfall Effect](https://proandroiddev.com/animating-inside-and-outside-the-box-with-jetpack-compose-a56eba1b6af6)
 */
@Composable
fun SnowfallEffect(modifier: Modifier = Modifier) {
    val snowflakes = remember { List(200) { generateRandomSnowflake() } }
    val infiniteTransition = rememberInfiniteTransition(label = "infiniteTransition")

    val offsetY by infiniteTransition.animateFloat(
        label = "offsetY",
        initialValue = 0f,
        targetValue = 3000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 15000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
    )


    Canvas(
        modifier = modifier
        .fillMaxSize()
    ) {
        snowflakes.forEach { snowflake ->
            val newY = (snowflake.y + offsetY * snowflake.speed) % size.height
            drawCircle(
                color = Color.White,
                radius = snowflake.radius,
                center = Offset(
                    x = snowflake.x * size.width,
                    y = newY)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSnowfallEffect() {
    SnowfallEffect()
}