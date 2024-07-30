package com.example.weather.ui.localcomposition

import androidx.compose.runtime.compositionLocalOf
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val LocalCustomizedTheme = compositionLocalOf {
    DarkCustomizedTheme
}

val DarkCustomizedTheme = CustomizedTheme(
    textColor = Color.White,
    backgroundBrush = Brush.linearGradient(listOf(Color.Yellow, Color.Green)),
)

val LightCustomizedTheme = CustomizedTheme(
    textColor = Color.Black,
    backgroundBrush = Brush.linearGradient(listOf(Color.Magenta, Color.Cyan)),
)

/**
 * We can define a customized theme like the following below:
 * Next, we need to open Core Fragment to provide the theme as reference to whole app
 */
data class CustomizedTheme(
    val textColor: Color,
    val backgroundBrush: Brush,
){

}