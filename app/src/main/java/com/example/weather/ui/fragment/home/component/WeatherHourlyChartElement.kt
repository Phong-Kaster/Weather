package com.example.weather.ui.fragment.home.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weather.domain.model.HourlyForecast

@Composable
fun WeatherHourlyChartElement(
    hourlyForecast: HourlyForecast,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        content = {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val path = Path()


                val start = Offset(x = 0f, y = size.height)
                val middle = Offset(x = size.width * 0.5f, y = size.height * 0.5f)
                val end = Offset(x = size.width, y = size.height)


                path.quadraticBezierTo(
                    start.x,
                    start.y,
                    middle.x,
                    middle.y
                )
                path.quadraticBezierTo(middle.x, middle.y, size.width * 0.75f, size.height * 0.25f)
                //path.quadraticBezierTo(size.width*0.75f, size.height*0.25f, end.x, end.y)

                drawPath(
                    path = path,
                    color = Color.Red,
                    style = Stroke(
                        width = 2.dp.toPx(),
                        cap = StrokeCap.Round
                    )
                )
            }
        },
    )
}

@Preview
@Composable
private fun Preview() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
    ) {
        WeatherHourlyChartElement(
            hourlyForecast = HourlyForecast.getFakeInstance(),
            modifier = Modifier
                .fillMaxSize()
        )
    }
}