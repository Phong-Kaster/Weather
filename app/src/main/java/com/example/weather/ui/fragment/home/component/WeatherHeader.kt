package com.example.weather.ui.fragment.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weather.ui.theme.customizedTextStyle

@Composable
fun WeatherHeader(
    page: Int = 0,
) {
    val context = LocalContext.current

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
                    color = Color.White,
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
                    color = Color.White,
                    modifier = Modifier
                )

                Text(
                    text = "${25 + page}°C",
                    style = customizedTextStyle(fontWeight = 600, fontSize = 110),
                    /*color = if (isNight) Color.White else Color.Black,*/
                    color = Color.White,
                )
            }
        }
    }
}

@Preview(
    showBackground = true,
    backgroundColor = 0xFFD6949A,
)
@Composable
private fun PreviewWeatherHeader() {
    WeatherHeader()
}