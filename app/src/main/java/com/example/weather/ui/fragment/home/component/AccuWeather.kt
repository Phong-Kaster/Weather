package com.example.weather.ui.fragment.home.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather.R
import com.example.weather.ui.theme.customizedTextStyle
import com.example.weather.util.AppUtil

@Composable
fun AccuWeather(
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
    ) {

        Text(
            text = stringResource(R.string.data_provided_in_part_by),
            style = customizedTextStyle(10, 400),
            color = Color.White,
        )

        Image(
            painter = painterResource(id = R.drawable.ic_accuweather),
            contentDescription = null,
            modifier = Modifier
                .height(12.dp)
                .clickable {
                    AppUtil.openWebsite(context, "https://www.accuweather.com")
                },
        )
    }
}

@Preview
@Composable
private fun Preview() {
    AccuWeather()
}