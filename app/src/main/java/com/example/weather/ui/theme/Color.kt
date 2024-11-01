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
val colorRain = Color(0xFF838383)
val colorDreary =  Color(0xFFA0A0A0)
val colorFor = Color(0xFFA5AEBB)
val colorSunrise = Color(0xFF83B8DA)
val colorSunset = Color(0xFFCB8AAA)
val colorMidnight = Color(0xFF172A39)
val colorNight = Color(0xFF171A1D)
val colorSnow = Color(0xFFA0CAE8)


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

val brushDay = Brush.linearGradient(
    colors = listOf(
        Color(0xFF74B8FF),
        Color(0xFF1165FF),
    ),
    start = Offset(0f, 0f),
    end = Offset(2 * Float.POSITIVE_INFINITY, 2 * Float.POSITIVE_INFINITY),
)

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

val brushDreary = Brush.linearGradient(
    colors = listOf(
        Color(0xFFA0A0A0),
        Color(0xFFD2D1D1),
    ),
    start = Offset(0f, 0f),
    end = Offset(2 * Float.POSITIVE_INFINITY, 2 * Float.POSITIVE_INFINITY),
)

val brushFog = Brush.linearGradient(
    colors = listOf(
        Color(0xFFBFD9FF),
        Color(0xFFA5AEBB),
    ),
    start = Offset(0f, 0f),
    end = Offset(2 * Float.POSITIVE_INFINITY, 2 * Float.POSITIVE_INFINITY),
)

val brushSunrise = Brush.linearGradient(
    colors = listOf(
        Color(0xFF81B9DD),
        Color(0xFF98B4BB),
        Color(0xFFA8B1A3),
        Color(0xFFF4A132),
    ),
    start = Offset(0f, 0f),
    end = Offset(2 * Float.POSITIVE_INFINITY, 2 * Float.POSITIVE_INFINITY),
)

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

val brushMidnight = Brush.linearGradient(
    colors = listOf(
        Color(0xFF172A39),
        Color(0xFF122432),
    ),
    start = Offset(0f, 0f),
    end = Offset(2 * Float.POSITIVE_INFINITY, 2 * Float.POSITIVE_INFINITY),
)


//val brushNight = Brush.linearGradient(
//    colors = listOf(
//        Color(0xFF172A39),
//        Color(0xFF122432),
//    ),
//    start = Offset(0f, 0f),
//    end = Offset(2 * Float.POSITIVE_INFINITY, 2 * Float.POSITIVE_INFINITY),
//)
val brushNight = Brush.linearGradient(
    colors = listOf(
        Color(0xFF0F1D28),
        Color(0xFF050F13),
    ),
    start = Offset(0f, 0f),
    end = Offset(2 * Float.POSITIVE_INFINITY, 2 * Float.POSITIVE_INFINITY),
)

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

val brushBackground = Brush.linearGradient(
    colors = listOf(
        Color(0xFF171A1D),
        Color(0xFF09090A),
    ),
    start = Offset(0f, 0f),
    end = Offset(2 * Float.POSITIVE_INFINITY, 2 * Float.POSITIVE_INFINITY),
)

val brushMostlyCloudyOrSunny = Brush.linearGradient(
    colors = listOf(
        Color(0xFF5A92BA),
        Color(0xFFA9D1EE),
    ),
    start = Offset(0f, 0f),
    end = Offset(2 * Float.POSITIVE_INFINITY, 2 * Float.POSITIVE_INFINITY),
)

val brushMostlyCloudyAndShowers = Brush.linearGradient(
    colors = listOf(
        Color(0xFF747474),
        Color(0xFFC2C2C2),
    ),
    start = Offset(0f, 0f),
    end = Offset(2 * Float.POSITIVE_INFINITY, 2 * Float.POSITIVE_INFINITY),
)

val brushWindy = Brush.linearGradient(
    colors = listOf(
        Color(0xFFBFD9FF),
        Color(0xFFA5AEBB),
    ),
    start = Offset(0f, 0f),
    end = Offset(2 * Float.POSITIVE_INFINITY, 2 * Float.POSITIVE_INFINITY),
)

val brushHazyMoonlight = Brush.linearGradient(
    colors = listOf(
        Color(0xFF0F1D28),
        Color(0xFF050F13),
    ),
    start = Offset(0f, 0f),
    end = Offset(2 * Float.POSITIVE_INFINITY, 2 * Float.POSITIVE_INFINITY),
)