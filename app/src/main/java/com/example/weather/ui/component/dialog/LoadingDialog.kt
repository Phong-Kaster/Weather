package com.example.weather.ui.component.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.weather.R
import com.example.weather.ui.theme.customizedTextStyle

@Composable
fun LoadingDialog(
    enable: Boolean,
    cornerRadius: Dp = 16.dp,
) {
    if (enable) {
        Dialog(
            onDismissRequest = {},
            content = {
                Surface(
                    modifier = Modifier
                        .background(color = Color(0xFF3D4145), shape = RoundedCornerShape(10.dp))
                        .width(220.dp)
                        .wrapContentHeight(),
                    shape = RoundedCornerShape(cornerRadius),
                ) {
                    Column(
                        modifier = Modifier
                            .background(
                                color = Color(0xFF3D4145),
                                shape = RoundedCornerShape(10.dp)
                            )
                            .padding(start = 56.dp, end = 56.dp, top = 16.dp)
                            .wrapContentWidth()
                            .wrapContentHeight(),
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {

                        CircularProgressIndicator(
                            color = Color(0xFF1165FF),
                            modifier = Modifier.size(25.dp)
                        )

                        Text(
                            modifier = Modifier
                                .padding(bottom = 16.dp)
                                .fillMaxWidth(),
                            text = stringResource(R.string.please_wait_a_minute),
                            textAlign = TextAlign.Center,
                            overflow = TextOverflow.Ellipsis,
                            style = customizedTextStyle(fontSize = 14, fontWeight = 600, color = Color.White)
                        )
                    }
                }
            })
    }
}