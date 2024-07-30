package com.example.weather.ui.fragment.home.component

import android.content.res.Configuration.UI_MODE_NIGHT_MASK
import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weather.ui.localcomposition.DarkCustomizedTheme
import com.example.weather.ui.localcomposition.LightCustomizedTheme
import com.example.weather.ui.localcomposition.LocalCustomizedTheme
import com.example.weather.ui.theme.CustomizedWeatherTheme
import com.example.weather.ui.theme.customizedTextStyle

@Composable
fun WeatherHeader(
    page: Int = 0,
) {
    val context = LocalContext.current

    val textColor by
    animateColorAsState(
        targetValue = LocalCustomizedTheme.current.backgroundColor,
        animationSpec = tween(durationMillis = 1500),
        label = "textColor")

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            /*.padding(horizontal = 16.dp)*/
            .fillMaxWidth(),
    ) {

//        val isNight by remember(currentForecast.weatherIcon, currentForecast.isDayTime) {
//            derivedStateOf {
//                Background.backgroundByWeather(
//                    currentForecast.weatherIcon,
//                    currentForecast.isDayTime
//                )
//                    .isBackgroundBlack()
//            }
//        }

        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(0.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
            ) {
                Text(
                    text = "Thunder storm",
                    //style = customizedTextStyle(fontWeight = 400, fontSize = 16),
                    /*color = if (isNight) Color.White else Color.Black,*/
//                    color = Color.White,
                    color = textColor,
                    textAlign = TextAlign.Center,
                    maxLines = 1,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(10.dp)
                )

                Text(
                    text = "Feels like 30°C",
                    style = customizedTextStyle(fontWeight = 400, fontSize = 16),
                    /*color = if (isNight) Color.White else Color.Black,*/
//                    color = Color.White,
                    color = textColor,
                    modifier = Modifier
                )

                Text(
                    text = "${25 + page}°C",
                    style = customizedTextStyle(fontWeight = 600, fontSize = 110),
                    /*color = if (isNight) Color.White else Color.Black,*/
//                    color = Color.White,
                    color = textColor
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFD6949A,
    uiMode = UI_MODE_NIGHT_NO
)
@Preview(
    showBackground = true,
    backgroundColor = 0xFFD6949A,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
private fun PreviewWeatherHeader() {
    CustomizedWeatherTheme {
        WeatherHeader()
    }
}