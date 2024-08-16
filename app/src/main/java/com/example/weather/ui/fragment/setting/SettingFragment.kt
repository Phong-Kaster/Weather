package com.example.weather.ui.fragment.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.weather.R
import com.example.weather.configuration.Constant
import com.example.weather.core.CoreTopBar3
import com.example.weather.ui.theme.colorNight
import com.example.weather.ui.theme.customizedTextStyle
import com.example.weather.util.DateUtil
import com.example.weather.util.DateUtil.formatWithPattern
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class SettingFragment : CoreFragment() {

    private val viewModel: SettingViewModel by viewModels()

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        SettingLayout(
            lastTimeDate = viewModel.lastTimeUpdate.collectAsState().value,
        )
    }
}

@Composable
fun SettingLayout(
    lastTimeDate: Date = Date(),
    onBack: () -> Unit = {},
) {
    CoreLayout(
        backgroundColor = colorNight,
        topBar = {
            CoreTopBar3(
                titleArrangement = Arrangement.Center,
                onLeftClick = onBack,
                textColor = Color.White,
                iconColor = Color.White,
                title = stringResource(id = R.string.settings)
            )
        },
        content = {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item(key = "LastTimeUpdate") {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = "Last time: ${lastTimeDate.formatWithPattern(DateUtil.PATTERN_FULL_DATE_TIME_REVERSED)}",
                            color = Color.White,
                            style = customizedTextStyle(
                                fontWeight = 500,
                                fontSize = 16
                            )
                        )
                    }
                }
            }
        }
    )
}

@Preview
@Composable
fun PreviewSettingLayout() {
    SettingLayout(
        lastTimeDate = Date(),
    )
}