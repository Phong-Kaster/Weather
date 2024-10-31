package com.example.weather.ui.fragment.splash

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.jetpack.core.LocalTheme
import com.example.weather.R
import com.example.weather.ui.theme.colorDay
import com.example.weather.ui.theme.customizedTextStyle
import com.example.weather.util.NavigationUtil.safeNavigate
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : CoreFragment() {

    private val viewModel: SplashViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.isDatabaseEmpty()
    }

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        SplashLayout(
            gotoNextScreen = {
                Log.d(TAG, "isDatabaseEmpty: ${viewModel.isDatabaseEmpty.value}")
                if (viewModel.isDatabaseEmpty.value) {
                    safeNavigate(destination = R.id.toSearch)
                } else {
                    safeNavigate(destination = R.id.toHome)
                }
            }
        )
    }
}

@Composable
fun SplashLayout(
    gotoNextScreen: () -> Unit = {}
) {
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        scope.launch {
            for (index in 1..100) {
                if (index == 100) {
                    gotoNextScreen()
                    break
                }
                delay(5)
            }
        }
    }

    CoreLayout(
        backgroundColor = colorDay,
        bottomBar = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, bottom = 10.dp, top = 0.dp)
            ) {

                Text(
                    text = "${stringResource(R.string.exclusively_from)} Jestapol, Inc.",
                    style = customizedTextStyle(
                        fontWeight = 600,
                        fontSize = 14,
                        color = LocalTheme.current.textColor,
                        fontStyle = FontStyle.Italic
                    ),
                    maxLines = 1,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                LinearProgressIndicator(
                    color = Color.White,
                    trackColor = LocalTheme.current.primary,
                    strokeCap = StrokeCap.Round,
                )
            }

        },
        content = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_sunny),
                    contentDescription = null,
                    modifier = Modifier
                        .width(100.dp)
                        .aspectRatio(1f)
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(20.dp)
                )

                Text(
                    text = stringResource(id = R.string.app_name),
                    style = customizedTextStyle(
                        fontWeight = 600,
                        fontSize = 24,
                        color = Color.White
                    ),
                    modifier = Modifier
                )

            }
        }
    )

}

@Preview
@Composable
private fun PreviewSplash() {
    SplashLayout()
}