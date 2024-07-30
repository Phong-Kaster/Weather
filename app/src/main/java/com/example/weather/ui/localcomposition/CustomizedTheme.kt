package com.example.weather.ui.localcomposition

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val LocalCustomizedTheme = compositionLocalOf {
    DarkCustomizedTheme
}

val DarkCustomizedTheme = CustomizedTheme(
    backgroundColor = Color.White,
    backgroundBrush = Brush.linearGradient(listOf(Color.Yellow, Color.Green)),
)

val LightCustomizedTheme = CustomizedTheme(
    backgroundColor = Color.Black,
    backgroundBrush = Brush.linearGradient(listOf(Color.Magenta, Color.Cyan)),
)

data class CustomizedTheme
constructor(
    val backgroundColor: Color,
    val backgroundBrush: Brush,
){

}