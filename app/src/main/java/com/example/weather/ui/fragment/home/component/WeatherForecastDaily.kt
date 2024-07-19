package com.example.weather.ui.fragment.home.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weather.R
import com.example.weather.domain.model.DailyForecast
import com.example.weather.ui.theme.brushSunset
import com.example.weather.ui.theme.customizedTextStyle

@Composable
fun WeatherForecastDaily(
//    location: Location,
    modifier: Modifier = Modifier,
    dailyForecasts: List<DailyForecast> = listOf(),
    timeZone: String = "America/New_York",
    isDayTime: Boolean = remember { true },
    onDailyClick: (DailyForecast?) -> Unit = {}
) {
    var lowestDaily by remember { mutableStateOf(DailyForecast()) }
    var highestDaily by remember { mutableStateOf(DailyForecast()) }
    var dayOfRain by remember { mutableIntStateOf(0) }

    val daily0 by remember {
        derivedStateOf {
            dailyForecasts.firstOrNull() ?: DailyForecast()
        }
    }

    val daily1 by remember {
        derivedStateOf {
            dailyForecasts.getOrNull(1) ?: DailyForecast()
        }
    }

    val daily2 by remember {
        derivedStateOf {
            dailyForecasts.getOrNull(2) ?: DailyForecast()
        }
    }

    LaunchedEffect(dailyForecasts.size) {
        var min: DailyForecast = dailyForecasts.firstOrNull() ?: DailyForecast()
        var max: DailyForecast = dailyForecasts.firstOrNull() ?: DailyForecast()
        var dayOfRainCount = 0

        dailyForecasts.forEachIndexed { index, dailyForecast ->
            if (index != 0) {
                val temperature = dailyForecast.temperature
                if (temperature.min.value < min.temperature.min.value) {
                    min = dailyForecast
                }
                if (temperature.max.value > max.temperature.max.value) {
                    max = dailyForecast
                }
            }
            if (dailyForecast.day.precipitationProbability > 0f || dailyForecast.night.precipitationProbability > 0f) {
                dayOfRainCount++
            }
        }

        lowestDaily = min
        highestDaily = max
        dayOfRain = dayOfRainCount
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = Color.White.copy(alpha = 0.25f))
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            Text(
                text = "7 - Day Forecast",
                style = customizedTextStyle(14, 600),
                modifier = Modifier.weight(1f),
                color = Color.White,
                textAlign = TextAlign.Start,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )


            Row(
                horizontalArrangement = Arrangement.spacedBy(5.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(5.dp))
                    .clickable { onDailyClick(null) }
            ) {
                Text(
                    text = stringResource(R.string.view_all),
                    color = Color.White,
                    style = customizedTextStyle(fontWeight = 300, fontSize = 14),
                )

                Icon(
                    imageVector = Icons.Default.KeyboardArrowRight,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Spacer(
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth()
                .height(0.5.dp)
                .background(color = Color.White.copy(0.3f))
        )

        /*Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            if (dailyForecasts.isNotEmpty()) {
                DailyForecastItem(
                    dailyForecast = daily0,
                    timeZone = timeZone,
                    isDayTime = isDayTime,
                    onClick = {
                        onDailyClick(daily0)
                    }
                )

                Spacer(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                        .height(0.5.dp)
                        .background(color = Color.White.copy(alpha = 0.4f))
                )

                DailyForecastItem(
                    dailyForecast = daily1,
                    timeZone = timeZone,
                    isDayTime = isDayTime,
                    onClick = {
                        onDailyClick(daily1)
                    }
                )

                Spacer(
                    modifier = Modifier
                        .padding(vertical = 8.dp)
                        .fillMaxWidth()
                        .height(0.5.dp)
                        .background(color = Color.White.copy(alpha = 0.4f))
                )

                DailyForecastItem(
                    dailyForecast = daily2,
                    timeZone = timeZone,
                    isDayTime = isDayTime,
                    onClick = {
                        onDailyClick(daily2)
                    }
                )
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
                        .highlightShimmer()
                )
            }
        }*/

        AnimatedVisibility(
            visible = dailyForecasts.isEmpty(),
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)
//                        .highlightShimmer()
                )
            }
        )

        AnimatedVisibility(
            visible = dailyForecasts.isNotEmpty(),
            content = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    DailyForecastItem2(
                        dailyForecast = daily0,
                        timeZone = timeZone,
                        isDayTime = isDayTime,
                        onClick = { onDailyClick(daily0) }
                    )

                    DailyForecastItem2(
                        dailyForecast = daily1,
                        timeZone = timeZone,
                        isDayTime = isDayTime,
                        onClick = { onDailyClick(daily1) }
                    )

                    DailyForecastItem2(
                        dailyForecast = daily2,
                        timeZone = timeZone,
                        isDayTime = isDayTime,
                        onClick = { onDailyClick(daily2) }
                    )
                }
            }
        )

        /*Spacer(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth()
                .height(0.5.dp)
                .background(Color.White.copy(0.3f))
        )*/
        /*CenterRow(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(0.dp, 0.dp, 10.dp, 10.dp))
                .background(Color(0xFF375167))
                .highlightShimmer()
                .onClick { onDailyClick(null) }
                .padding(vertical = 10.dp, horizontal = 12.dp)
        ) {

            Text(
                text = stringResource(R.string.next_7_day_forecast),
                style = appFont(400, 16.sp.nonScaledSp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            AppIcon(
                modifier = Modifier
                    .padding(start = 6.dp)
                    .size(12.dp), icon = R.drawable.ic_next_arrow
            )
        }*/
    }
}


@Composable
fun DailyForecastItem2(
    dailyForecast: DailyForecast = DailyForecast(),
    timeZone: String = "America/New_York",
    isDayTime: Boolean = true,
    onClick: () -> Unit
) {
//    val weatherIcon by remember {
//        mutableIntStateOf(
//            weatherIconByName(
//                if (isDayTime)
//                    dailyForecast.day.icon
//                else
//                    dailyForecast.night.icon
//            )
//        )
//    }

    val weatherIcon = R.drawable.ic_12

    val weatherIcon2 by remember(dailyForecast.date) {
        mutableIntStateOf(
            if (isDayTime)
                dailyForecast.day.icon
            else
                dailyForecast.night.icon
        )
    }


    val precipitationProbability by remember(dailyForecast, isDayTime) {
        derivedStateOf {
            if (isDayTime)
                dailyForecast.day.precipitationProbability
            else
                dailyForecast.night.precipitationProbability
        }
    }


    Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        // Fri 12 Jul
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
        ) {
            Text(
//                text = dailyForecast.date.convertTo(timeZone).formatDate(Constants.DATE_FORMAT_WEEK_DAY),
                text = "Fri",
                color = Color.White,
                style = customizedTextStyle(14, 400),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier,
            )

            Text(
//                text = dailyForecast.date.convertTo(timeZone).formatDate(Constants.DATE_FORMAT_DAY_MONTH_ONLY),
                text = "12 Jul",
                color = Color(0xFFE5E5E5),
                style = customizedTextStyle(14, 400),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                modifier = Modifier,
            )
        }

        Spacer(modifier = Modifier.width(10.dp))

        // Weather Icon
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
        ) {
            Image(
                modifier = Modifier.size(24.dp),
                contentDescription = null,
                painter = painterResource(id = weatherIcon)
            )


            Text(
//                text = "${precipitationProbability.toStr()}%",
                text = "10 %",
                style = customizedTextStyle(12, 400),
                color = Color.White,
                modifier = Modifier
                    .alpha(alpha = if (precipitationProbability > 0f) 1f else 0f)
            )
        }

        // Weather text

        Text(
//            text = stringResource(weatherText(weatherIcon2)),
            text = "Cloudy",
            style = customizedTextStyle(14, 500),
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .widthIn(0.dp, 150.dp)
        )

        Spacer(modifier = Modifier.weight(0.5F))

        Text(
//            text = buildAnnotatedString {
//                append(
//                    text = "${
//                        dailyForecast.temperature.temperatureMinValue().toStr()
//                    }\u00B0" + UnitUtils.temperatureUnit
//                )
//                append(text = "/")
//                append(
//                    text = "${
//                        dailyForecast.temperature.temperatureMaxValue().toStr()
//                    }\u00B0" + UnitUtils.temperatureUnit
//                )
//            },
            text = "20/35",
            style = customizedTextStyle(16, 400),
            color = Color.White,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

@Preview
@Composable
fun PreviewDailyForecastItem2() {
    Column(modifier = Modifier.background(brush = brushSunset)) {
        DailyForecastItem2(
            dailyForecast = DailyForecast(),
            timeZone = "America/New_York",
            isDayTime = true,
            onClick = {}
        )
    }
}

@Preview
@Composable
fun PreviewDetailDailyView() {
    Column(modifier = Modifier.background(brush = brushSunset)) {
        WeatherForecastDaily(
//            location = Location(),
            dailyForecasts = listOf(
                DailyForecast(),
                DailyForecast(),
                DailyForecast(),
                DailyForecast(),
                DailyForecast(),
            )
        )
    }
}