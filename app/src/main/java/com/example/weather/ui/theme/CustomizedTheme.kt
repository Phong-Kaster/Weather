package com.example.weather.ui.theme

import androidx.compose.ui.graphics.Color

/**
 * We can define a customized theme like the following below:
 * Next, we need to open Core Fragment to provide the theme as reference to whole app
 */
data class CustomizedTheme(
    val primary: Color,
    val onPrimary: Color,
    val secondary: Color,
    val onSecondary: Color,
    val background: Color,
    val error: Color,
    val onError: Color,
    val textColor: Color,
    val dim: Color,
    val onDim: Color,
)

val DarkCustomizedTheme = CustomizedTheme(
    primary = Color(0xFF9EFFFF),
    onPrimary = Color(0xFF2C3141),
    secondary = Color(0xFF004BDC),
    onSecondary = Color(0xFF2C3141),
    background = Color(0xFF2C3141),
    error = Color(0xFFC20000),
    onError = Color(0xFFD9D9D9),
    textColor = Color.White,
    dim = Color(0xFF8C8C8C),
    onDim = Color(0xFF2C3141)
)

val LightCustomizedTheme = CustomizedTheme(
    primary = Color(0xFF9EFFFF),
    onPrimary = Color(0xFF2C3141),
    secondary = Color(0xFF004BDC),
    onSecondary = Color(0xFF2C3141),
    background = Color(0xFFFFFFFF),
    error = Color(0xFFC20000),
    onError = Color(0xFFD9D9D9),
    textColor = Color.Black,
    dim = Color(0xFF8C8C8C),
    onDim = Color(0xFF2C3141)
)

