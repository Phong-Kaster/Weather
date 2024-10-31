package com.example.weather.ui.fragment.setting.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.jetpack.core.LocalTheme
import com.example.weather.R
import com.example.weather.configuration.Constant
import com.example.weather.ui.theme.customizedTextStyle

@Composable
fun TemperatureSwitch(
    isCelsiusEnabled: Boolean = true,
    onChange: (Boolean) -> Unit = { },
) {
    var enableCelsius by remember(isCelsiusEnabled) { mutableStateOf(isCelsiusEnabled) }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = stringResource(R.string.temperature),
            style = customizedTextStyle(
                fontSize = 16,
                fontWeight = 600,
                color = LocalTheme.current.textColor
            ),
            maxLines = 1,
            modifier = Modifier.fillMaxWidth(),
        )
        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = Constant.CELSIUS_SYMBOL,
                style = customizedTextStyle(
                    fontSize = 14,
                    fontWeight = 600,
                    color = Color.White,
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(color = if (enableCelsius) LocalTheme.current.secondary else Color.Transparent)
                    .clickable {
                        enableCelsius = !enableCelsius
                        onChange(enableCelsius)
                    }
                    .padding(vertical = 10.dp)
                    .weight(1f)
            )

            Text(
                text = Constant.FAHRENHEIT_SYMBOL,
                style = customizedTextStyle(
                    fontSize = 14,
                    fontWeight = 600,
                    color = Color.White,
                ),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(10.dp))
                    .background(color = if (enableCelsius) Color.Transparent else LocalTheme.current.secondary)
                    .clickable {
                        enableCelsius = !enableCelsius
                        onChange(enableCelsius)
                    }
                    .padding(vertical = 10.dp)
                    .weight(1f)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewTemperatureSwitch() {
    TemperatureSwitch(
        isCelsiusEnabled = true,
        onChange = { enableCelsius -> }
    )
}