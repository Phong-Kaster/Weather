package com.example.weather.ui.fragment.home.component

import android.widget.Toast
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.core.LocalTheme
import com.example.weather.domain.model.HourlyForecast
import com.example.weather.ui.theme.customizedTextStyle
import com.example.weather.util.LocalDateUtil.toDateWithPattern

/**
 * [Learn Jetpack Compose Canvas Cubic and Quadratic Bezier And Its Usage](https://medium.com/mobile-app-development-publication/learn-jetpack-compose-canvas-cubic-and-quadratic-bezier-and-its-usage-96a4d9a7e3fb)
 *
 * [Plotting Gradient Bezier Trends with Jetpack Compose](https://medium.com/@kezhang404/either-compose-is-elegant-or-if-you-want-to-draw-something-with-an-android-view-you-have-to-7ce00dc7cc1)
 */
@Composable
fun WeatherHourlyChartElement(
    previousHourlyForecast: HourlyForecast,
    hourlyForecast: HourlyForecast,
    maxTemperature: Float = 100f,
    minTemperature: Float = 0f,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(space = 10.dp),
        modifier = modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .clickable {},
        content = {
            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                val path = Path()

                val spacing = size.height / maxTemperature

                val previousX = 0f
                val previousY = spacing * (maxTemperature - previousHourlyForecast.temperature)

                val currentX = size.width
                val currentY = spacing * (maxTemperature - hourlyForecast.temperature)

                path.moveTo(x = previousX, y = previousY)
                path.cubicTo(
                    x1 = (previousX + currentX) * 0.5f,
                    y1 = previousY,
                    x2 = (previousX + currentX) * 0.5f,
                    y2 = currentY,
                    x3 = currentX,
                    y3 = currentY
                )


                drawPath(
                    path = path,
                    color = Color.White,
                    style = Stroke(
                        width = 2.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                )
            }

            Text(
                text = hourlyForecast.epochDateTime.toDateWithPattern(pattern = "dd/MM"),
                style = customizedTextStyle(
                    fontSize = 12,
                    fontWeight = 500,
                    color = Color.White
                ),
            )
        },
    )
}

@Preview
@Composable
private fun Preview() {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = LocalTheme.current.background)
            .aspectRatio(16 / 10f)
            .padding(16.dp)
    ) {
        WeatherHourlyChartElement(
            previousHourlyForecast = HourlyForecast.getFakeInstance(),
            hourlyForecast = HourlyForecast.getFakeInstance(),
            modifier = Modifier
        )
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxHeight()
                .drawChartBaseline(
                    textMeasurer = rememberTextMeasurer(),
                    minAlignment = 0f,
                    maxAlignment = 100f,
                    numberLine = 4,
                    dateHeight = 30.dp,
                    enableDashedLine = false
                )
        ) {

        }
    }
}