package com.example.weather.ui.fragment.search.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather.R
import com.example.weather.ui.theme.brushManageLocation

@Composable
fun SearchTopBar(
    onBack: ()->Unit = {},
    onValueChange: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }
    var query by remember { mutableStateOf("") }

    /*SEARCH VIEW*/
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier.fillMaxWidth()
    ) {
        IconButton(
            /*enabled = enableBackButton && openFor == Constants.OPEN_FOR_NAVIGATE || openFor == Constants.OPEN_FOR_SEARCH_LOCATION,*/
            enabled = true,
            onClick = onBack,
            modifier = Modifier.size(24.dp),
            content = {
                Icon(
                    painter = painterResource(R.drawable.ic_keyboard_arrow_left),
                    contentDescription = null,
                    tint = Color.White
                )
            }
        )

        Row(
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clip(shape = RoundedCornerShape(10.dp))
                .background(color = Color.White.copy(alpha = 0.3f))
                .padding(horizontal = 10.dp, vertical = 5.dp),
        ) {
            Icon(
                painterResource(id = R.drawable.ic_search),
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier.size(15.dp)
            )

            BasicTextField(
                value = query,
                onValueChange = {
                    query = it
                    onValueChange(it)
                },
                cursorBrush = SolidColor(Color.Cyan),
                textStyle = TextStyle(
                    fontWeight = FontWeight(500),
                    fontSize = 14.sp,
                    platformStyle = PlatformTextStyle(includeFontPadding = false),
                    fontStyle = FontStyle.Normal,
                    color = Color.White,
                    textAlign = TextAlign.Start,
                ),
                decorationBox = { innerTextField ->
                    Box(
                        modifier = Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.Center
                    ) {
                        if (query.isEmpty()) {
                            Text(
                                text = stringResource(R.string.enter_a_name),
                                color = Color.White,
                                textAlign = TextAlign.Center,
                                maxLines = 1,
                                style = TextStyle(
                                    fontWeight = FontWeight(500),
                                    fontSize = 14.sp,
                                    platformStyle = PlatformTextStyle(includeFontPadding = false),
                                    fontStyle = FontStyle.Normal,
                                    color = Color.White
                                ),
                                overflow = TextOverflow.Ellipsis,
                                modifier = Modifier
                                    .align(BiasAlignment(horizontalBias = -1f, verticalBias = 0f))
                                    .wrapContentHeight()
                            )
                        }
                    }
                    innerTextField()
                },
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        onValueChange(query)
                    }
                ),
                modifier = Modifier
                    .padding(vertical = 5.dp)
                    .focusRequester(focusRequester = focusRequester)
            )


            Spacer(modifier = Modifier.weight(0.5F))

            Icon(
                imageVector = Icons.Default.Clear,
                contentDescription = null,
                tint = Color.White,
                modifier = Modifier
                    .size(15.dp)
                    .clickable(
                        enabled = query.isNotEmpty(),
                        onClick = {
                            query = ""
                            onValueChange("")
                        }
                    )
                    .alpha(alpha = if (query.isNotEmpty()) 1f else 0f)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewSearchTopBar() {
    SearchTopBar()
}