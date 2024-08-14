package com.example.weather.ui.theme

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val colorDay = Color(0xFF4387FF)
val brushDay = Brush.linearGradient(
    colors = listOf(
        Color(0xFFC3DDFF),
        Color(0xFFA8CBFF),
        Color(0xFF74A8FF),
        Color(0xFF4387FF),
    ),
    start = Offset(0f, 0f),
    end = Offset(2 * Float.POSITIVE_INFINITY, 2 * Float.POSITIVE_INFINITY),
)

val colorRain = Color(0xFF838383)
val brushRain = Brush.linearGradient(
    colors = listOf(
        Color(0xFFBBBBBB),
        Color(0xFFA3A3A3),
        Color(0xFF949494),
        Color(0xFF838383),
    ),
    start = Offset(0f, 0f),
    end = Offset(2 * Float.POSITIVE_INFINITY, 2 * Float.POSITIVE_INFINITY),
)

val colorDreary =  Color(0xFFA0A0A0)
val brushDreary = Brush.linearGradient(
    colors = listOf(
        Color(0xFFA0A0A0),
        Color(0xFFD2D1D1),
    ),
    start = Offset(0f, 0f),
    end = Offset(2 * Float.POSITIVE_INFINITY, 2 * Float.POSITIVE_INFINITY),
)

val colorFor = Color(0xFFA5AEBB)
val brushFog = Brush.linearGradient(
    colors = listOf(
        Color(0xFFBFD9FF),
        Color(0xFFA5AEBB),
    ),
    start = Offset(0f, 0f),
    end = Offset(2 * Float.POSITIVE_INFINITY, 2 * Float.POSITIVE_INFINITY),
)

val colorSunrise = Color(0xFF83B8DA)
val brushSunrise = Brush.linearGradient(
    colors = listOf(
        Color(0xFF83B8DA),
        Color(0xFF98B4BB),
        Color(0xFFA8B1A3),
        Color(0xFFB4AE91),
    ),
    start = Offset(0f, 0f),
    end = Offset(2 * Float.POSITIVE_INFINITY, 2 * Float.POSITIVE_INFINITY),
)

val colorSunset = Color(0xFFCB8AAA)
val brushSunset = Brush.linearGradient(
    colors = listOf(
        Color(0xFFBE83D1),
        Color(0xFFCB8AAA),
        Color(0xFFDD9476),
        Color(0xFFF19F3A),
    ),
    start = Offset(0f, 0f),
    end = Offset(2 * Float.POSITIVE_INFINITY, 2 * Float.POSITIVE_INFINITY),
)

val colorMidnight = Color(0xFF172A39)
val brushMidnight = Brush.linearGradient(
    colors = listOf(
        Color(0xFF172A39),
        Color(0xFF122432),
    ),
    start = Offset(0f, 0f),
    end = Offset(2 * Float.POSITIVE_INFINITY, 2 * Float.POSITIVE_INFINITY),
)

val colorNight = Color(0xFF171A1D)
val brushNight = Brush.linearGradient(
    colors = listOf(
        Color(0xFF172A39),
        Color(0xFF122432),
    ),
    start = Offset(0f, 0f),
    end = Offset(2 * Float.POSITIVE_INFINITY, 2 * Float.POSITIVE_INFINITY),
)

val colorSnow = Color(0xFFA0CAE8)
val brushSnow = Brush.linearGradient(
    colors = listOf(
        Color(0xFFA0CAE8),
        Color(0xFF8FBCDD),
        Color(0xFF79ABCE),
        Color(0xFF699EC4),
    ),
    start = Offset(0f, 0f),
    end = Offset(2 * Float.POSITIVE_INFINITY, 2 * Float.POSITIVE_INFINITY),
)

val brushManageLocation = Brush.linearGradient(
    colors = listOf(
        Color(0xFF171A1D),
        Color(0xFF09090A),
    ),
    start = Offset(0f, 0f),
    end = Offset(2 * Float.POSITIVE_INFINITY, 2 * Float.POSITIVE_INFINITY),
)

// TEXT COLOR
val PrimaryColor = Color(0xFFFFCC00)
val OppositePrimaryColor = Color.Black
val TextColor1 = PrimaryColor
val TextColor2 = Color(0xFF909090)
val TextColor3 = Color(0xFF686868)
val TextColor4 = Color(0xFF4B4B4B)
val TextColor5 = Color(0xFF333333)