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
import com.example.jetpack.core.LocalTheme
import com.example.weather.R
import com.example.weather.core.CoreTopBar3
import com.example.weather.ui.fragment.setting.component.TemperatureSwitch
import com.example.weather.ui.theme.colorNight
import com.example.weather.ui.theme.customizedTextStyle
import com.example.weather.util.DateUtil
import com.example.weather.util.DateUtil.formatWithPattern
import com.example.weather.util.NavigationUtil.safeNavigateUp
import dagger.hilt.android.AndroidEntryPoint
import java.util.Date

@AndroidEntryPoint
class SettingFragment : CoreFragment() {

    private val viewModel: SettingViewModel by viewModels()

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        SettingLayout(
            isCelsiusEnabled = viewModel.isCelsiusEnabled.collectAsState().value,
            onBack = { safeNavigateUp() },
            onChangeCelsiusEnabled = { isCelsiusEnabled -> viewModel.setEnabledCelsius(boolean = isCelsiusEnabled) }
        )
    }
}

@Composable
fun SettingLayout(
    isCelsiusEnabled: Boolean = false,
    onBack: () -> Unit = {},
    onChangeCelsiusEnabled: (Boolean) -> Unit = {},
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
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
            ) {
                item(key = "isCelsiusEnabled") {
                    TemperatureSwitch(
                        isCelsiusEnabled = isCelsiusEnabled,
                        onChange = onChangeCelsiusEnabled,
                    )
                }
            }
        }
    )
}

@Preview
@Composable
fun PreviewSettingLayout() {
    SettingLayout()
}