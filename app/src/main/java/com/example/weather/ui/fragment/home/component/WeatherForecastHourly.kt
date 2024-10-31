package com.example.weather.ui.fragment.home.component

import android.util.Log
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weather.R
import com.example.weather.domain.enums.ConditionType
import com.example.weather.domain.model.HourlyForecast
import com.example.weather.ui.component.effect.shimmerEffect
import com.example.weather.ui.theme.brushSunset
import com.example.weather.ui.theme.customizedTextStyle
import com.example.weather.util.LocalDateUtil.convertToTimezone
import com.example.weather.util.LocalDateUtil.toDate
import com.example.weather.util.TemperatureUtil.toCorrespondingTemperatureUnit
import com.example.weather.util.TemperatureUtil.toTime


val LineSpacing = 30.dp
var lastFilter: ConditionType = ConditionType.TEMPERATURE

@Composable
fun WeatherForecastHourly(
    showLoading: Boolean,
    modifier: Modifier = Modifier,
    hourlyForecasts: List<HourlyForecast> = listOf(),
    timezone: String,
) {
    var filterChange by remember { mutableStateOf(lastFilter) }
    val filter by remember { derivedStateOf { filterChange } }

    val density = LocalDensity.current

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(20.dp))
            .background(color = Color.White.copy(alpha = 0.25f))
            .padding(horizontal = 16.dp, vertical = 16.dp)
    ) {
        AnimatedVisibility(
            visible = !showLoading,
            content = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    // 72 - HOURLY FORECAST & 3 BUTTONS
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                    ) {
                        Text(
                            text = "72-Hour",
                            style = customizedTextStyle(fontWeight = 600, fontSize = 14),
                            color = Color.White,
                            modifier = Modifier.weight(1f),
                            textAlign = TextAlign.Start,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )

                        /*3 BUTTONS: TEMPERATURE, PRECIPITATION & WIND*/
                        Row(
                            horizontalArrangement = Arrangement.spacedBy(
                                space = 10.dp,
                                alignment = Alignment.CenterHorizontally
                            ),
                            modifier = Modifier
                        ) {
                            // 1. TEMPERATURE
                            Icon(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(shape = RoundedCornerShape(5.dp))
                                    .clickable {
//                            if (filter != ConditionType.TEMPERATURE) {
//                                FirebaseUtils.trackEvent(com.weatherapp.weatherforecast.weatheradar.weatherwidget.utils.firebase.EventName.HOME_SCR_TEMP_HOURLY_FC_CLICK.eventName())
//                            }
                                        filterChange = ConditionType.TEMPERATURE
                                        lastFilter = filter
                                    }
                                    .background(
                                        color = if (filter == ConditionType.TEMPERATURE)
                                            Color.White.copy(alpha = 0.4f)
                                        else
                                            Color.White.copy(0.1f)
                                    )
                                    .padding(5.dp),
                                painter = painterResource(R.drawable.ic_temperature),
                                contentDescription = "icon next hourly",
                                tint = Color.White,
                            )

                            // 2. PRECIPITATION
                            Icon(
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .clickable {
                                        filterChange = ConditionType.PRECIPITATION
                                        lastFilter = filter
                                    }
                                    .background(
                                        color = if (filter == ConditionType.PRECIPITATION)
                                            Color.White.copy(alpha = 0.4f)
                                        else
                                            Color.White.copy(alpha = 0.1f)
                                    )
                                    .padding(5.dp),
                                painter = painterResource(R.drawable.ic_precipitation),
                                contentDescription = "icon next hourly",
                                tint = Color.White)

                            // 3. WIND
                            Icon(
                                painter = painterResource(R.drawable.ic_wind),
                                contentDescription = "icon next hourly",
                                tint = Color.White,
                                modifier = Modifier
                                    .size(24.dp)
                                    .clip(RoundedCornerShape(5.dp))
                                    .clickable {
                                        filterChange = ConditionType.WIND
                                        lastFilter = filter
                                    }
                                    .background(
                                        color = if (filter == ConditionType.WIND)
                                            Color.White.copy(alpha = 0.4f)
                                        else
                                            Color.White.copy(0.1f)
                                    )
                                    .padding(5.dp),
                            )

                            // 4. OPEN HOURLY FORECAST
                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier
                                    .clip(shape = CircleShape)
                                    .clickable { }
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
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(30.dp),
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        itemsIndexed(
                            items = hourlyForecasts,
                            key = { index, hourlyForecast -> "$index - ${hourlyForecast.id}" },
                            itemContent = { index: Int, hourlyForecast: HourlyForecast ->
                                WeatherForecastHourlyItem(
                                    modifier = Modifier,
                                    hourlyForecast = hourlyForecast,
                                    timezone = timezone,
                                    conditionType = filter,
                                    onClick = { },
                                )
                            }
                        )
                    }
                }
            })


        AnimatedVisibility(
            visible = showLoading,
            enter = slideInVertically {
                // Slide in from 40 dp from the top.
                with(density) { -40.dp.roundToPx() }
            } + expandVertically(
                // Expand from the top.
                expandFrom = Alignment.Top
            ) + fadeIn(
                // Fade in with the initial alpha of 0.3f.
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut(),
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .shimmerEffect()
                )
            }
        )

    }
}


@Composable
fun WeatherForecastHourlyItem(
    modifier: Modifier,
    hourlyForecast: HourlyForecast = HourlyForecast(),
    timezone: String = "America/New_York",
    conditionType: ConditionType,
    onClick: () -> Unit
) {
    /*    val time by remember(timeZone, hourlyForecast.date) {
            derivedStateOf {
                hourlyForecast.date
                    .convertTo(timeZone)
                    .formatDate(
                        if (UnitUtils.timeUnit == Constants.TIME_UNIT_24H) "H:mm" else "h a"
                    ).replace("AM", "am").replace("PM", "pm")
            }
        }*/

    val time by remember(timezone, hourlyForecast.epochDateTime) {
        derivedStateOf {
            hourlyForecast.epochDateTime.toTime()
        }
    }

    LaunchedEffect(timezone, hourlyForecast.epochDateTime) {
        Log.d("TAG", "WeatherForecastHourlyItem -------------------- ")
        Log.d("TAG", "WeatherForecastHourlyItem - timezone = ${timezone} ")
        Log.d("TAG", "WeatherForecastHourlyItem - date = ${hourlyForecast.epochDateTime.toDate()} ")
        Log.d("TAG", "WeatherForecastHourlyItem - convertToTimezone = ${hourlyForecast.epochDateTime.toDate().convertToTimezone(timezone = timezone)}")
    }


    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .clip(shape = RoundedCornerShape(5.dp))
            .clickable { onClick() }
    ) {
        // 23:22
        Text(
            text = hourlyForecast.epochDateTime.toTime(),
            modifier = Modifier.height(18.dp),
            style = customizedTextStyle(fontWeight = 400, fontSize = 14),
            color = Color.White
        )

        AnimatedContent(targetState = conditionType, label = "conditionType") { it ->
            when (it) {
                ConditionType.TEMPERATURE -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_12),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp),
                        )

                        Text(
                            text = "${hourlyForecast.temperature.toCorrespondingTemperatureUnit()}",
                            style = customizedTextStyle(fontWeight = 400, fontSize = 14),
                            color = Color.White,
                            modifier = Modifier.height(22.dp)
                        )
                    }
                }

                ConditionType.WIND -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_navigate),
                            contentDescription = "",
                            tint = Color.White,
                            modifier = Modifier
                                .size(24.dp)
                                .rotate(hourlyForecast.windDirection.degree - 90f)
                        )

                        Text(
                            modifier = Modifier.height(16.dp),
                            text = hourlyForecast.windDirection.localized,
                            style = customizedTextStyle(fontWeight = 400, fontSize = 14),
                            color = Color.White
                        )
                    }
                }

                ConditionType.PRECIPITATION -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_water),
                            contentDescription = "",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )

                        Text(
                            text = "10mm",
                            style = customizedTextStyle(12, 400),
                            color = Color.White,
                            modifier = Modifier
                                .height(16.dp)
                                .alpha(
                                    alpha =
                                    if (hourlyForecast.rain.value == 0f)
                                        0f
                                    else
                                        1f
                                ),
                        )
                    }
                }

                else -> {

                }
            }
        }
    }


}

@Preview
@Composable
private fun PreviewWeatherForecastHourlyItem() {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .background(brush = brushSunset)
    ) {
        WeatherForecastHourlyItem(
            modifier = Modifier,
            hourlyForecast = HourlyForecast(),
            timezone = "America/New_York",
            conditionType = ConditionType.TEMPERATURE,
            onClick = {}
        )

        WeatherForecastHourlyItem(
            modifier = Modifier,
            hourlyForecast = HourlyForecast(),
            timezone = "America/New_York",
            conditionType = ConditionType.WIND,
            onClick = {}
        )

        WeatherForecastHourlyItem(
            modifier = Modifier,
            hourlyForecast = HourlyForecast(),
            timezone = "America/New_York",
            conditionType = ConditionType.PRECIPITATION,
            onClick = {}
        )
    }
}

@Preview
@Composable
fun PreviewDetailHourlyView() {
    Column(modifier = Modifier.background(brush = brushSunset)) {
        WeatherForecastHourly(
            showLoading = false,
            timezone = "",
            hourlyForecasts = listOf(
                HourlyForecast(),
                HourlyForecast(),
                HourlyForecast(),
            )
        )
    }
}

@Preview
@Composable
fun PreviewDetailHourlyViewShowLoading() {
    Column(modifier = Modifier.background(brush = brushSunset)) {
        WeatherForecastHourly(
            showLoading = true,
            timezone = "",
            hourlyForecasts = listOf(
                HourlyForecast(),
                HourlyForecast(),
                HourlyForecast(),
            )
        )
    }
}