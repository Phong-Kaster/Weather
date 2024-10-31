package com.example.weather.ui.fragment.search.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.PlatformTextStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.weather.domain.model.LocationAuto
import com.example.weather.ui.theme.brushDay
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
fun SearchList(
    locations: ImmutableList<LocationAuto>?,
    onClick: (LocationAuto) -> Unit = {},
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier) {
        AnimatedVisibility(visible = locations?.isEmpty() == true) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.fillMaxWidth(),
                content = {
                    items(
                        items = LocationAuto.getFakeData(),
                        itemContent = { location ->
                            LocationAutoElement(
                                location = location,
                                onClick = onClick
                            )
                        }
                    )
                })
        }

        AnimatedVisibility(
            visible = locations?.isNotEmpty() == true,
            enter = fadeIn(initialAlpha = 0.4f),
            exit = fadeOut(animationSpec = tween(durationMillis = 250))
        ) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(20.dp),
                modifier = Modifier.fillMaxWidth(),
                content = {
                items(
                    items = locations ?: listOf(),
                    itemContent = { location ->
                        LocationAutoElement(
                            location = location,
                            onClick = onClick
                        )
                    }
                )
            })
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun LocationAutoElement(
    location: LocationAuto,
    onClick: (LocationAuto) -> Unit = {},
) {
    val placeName by remember(location) { mutableStateOf("${location.localizedName}") }
    val countryName by remember(location) {
        derivedStateOf {
            val cityName = location.administrativeArea?.localizedName
            val countryName = location.country?.localizedName
            if (placeName == cityName) {
                "$countryName"
            } else {
                "$cityName, $countryName"
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(15.dp))
            .clickable {
                onClick(location)
            }
            .padding(vertical = 5.dp, horizontal = 5.dp)
    ) {
        Text(
            text = placeName,
            style = TextStyle(
                fontWeight = FontWeight(600),
                fontSize = 16.sp,
                platformStyle = PlatformTextStyle(includeFontPadding = false),
                color = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .basicMarquee(Int.MAX_VALUE)
        )

        Text(
            text = countryName,
            style = TextStyle(
                fontWeight = FontWeight(400),
                fontSize = 12.sp,
                platformStyle = PlatformTextStyle(includeFontPadding = false),
                color = Color(0xFFD5D5D5)
            ),
            modifier = Modifier
                .fillMaxWidth()
                .basicMarquee(Int.MAX_VALUE)
        )
    }
}

@Preview
@Composable
private fun PreviewSearchList() {
    Column(modifier = Modifier.background(brush = brushDay)) {
        SearchList(
            locations = LocationAuto.getFakeData().toImmutableList(),
            modifier = Modifier,
        )
    }
}

@Preview
@Composable
private fun PreviewSearchListForEmpty() {
    Column(modifier = Modifier.background(brush = brushDay)) {
        SearchList(
            locations = null,
            modifier = Modifier,
        )
    }
}