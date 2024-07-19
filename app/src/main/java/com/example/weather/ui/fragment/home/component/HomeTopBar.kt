package com.example.weather.ui.fragment.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.weather.R
import com.example.weather.ui.theme.brushSunset
import com.example.weather.ui.theme.customizedTextStyle

@Composable
fun HomeTopBar(
    pageCurrent: Int,
    pageCount: Int,
    onMenuLeft: () -> Unit = {},
    onMenuRight: () -> Unit = {},

    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth()) {
        IconButton(
            onClick = onMenuLeft,
            content = {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            },
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .align(Alignment.Center)
        ) {
            Text(
//                text = location.name?.ifEmpty { "" } ?: "",
                text = stringResource(id = R.string.app_name),
                style = customizedTextStyle(fontWeight = 500, fontSize = 18),
                color = Color.White,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .widthIn(0.dp, 200.dp)
            )

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {
                itemsIndexed(items = (1..pageCount).toList(), key = { index, _ ->
                    index
                }) { index, _ ->
                    /*if (index == 0) {
                        AppIconColor(
                            modifier = Modifier.size(16.dp), icon = R.drawable.ic_navigate_green,
                            color = if (pageCurrent == index) ActiveColor else Color.White
                        )
                    } else {*/
                        Spacer(
                            modifier = Modifier
                                .size(6.dp)
                                .background(
                                    color = if (pageCurrent == index)
                                        Color.White
                                    else
                                        Color.DarkGray,
                                    shape = RoundedCornerShape(10.dp)
                                )
                        )
                    /*}*/
                }
            }
        }

        IconButton(
            onClick = onMenuRight,
            modifier = Modifier.align(Alignment.CenterEnd),
            content = {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.size(24.dp)
                )
            },
        )
    }
}

@Preview
@Composable
private fun PreviewHomeTopBar() {
    Column(modifier = Modifier.background(brush = brushSunset)) {
        HomeTopBar(
            pageCurrent = 1,
            pageCount = 5,
            onMenuLeft = {},
            onMenuRight = {},
            //location = Location.getFakeLocation()
        )
    }

}