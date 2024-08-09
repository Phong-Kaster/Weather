package com.example.weather.ui.fragment.search

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.example.jetpack.core.CoreFragment
import com.example.jetpack.core.CoreLayout
import com.example.weather.R
import com.example.weather.domain.model.LocationAuto
import com.example.weather.lifecycleobserver.LocationPermissionLifecycleObserver
import com.example.weather.ui.fragment.search.component.SearchList
import com.example.weather.ui.fragment.search.component.SearchTopBar
import com.example.weather.ui.theme.brushManageLocation
import com.example.weather.ui.theme.customizedTextStyle
import com.example.weather.util.NavigationUtil.safeNavigateUp
import com.example.weather.util.PermissionUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

@AndroidEntryPoint
class SearchFragment : CoreFragment() {

    private lateinit var locationPermissionObserver: LocationPermissionLifecycleObserver
    private var showLoadingDialog by mutableStateOf(false)
    private var showLoadingAnimationCircular by mutableStateOf(false)

    private val viewModel: SearchViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupLocationObserver()
    }

    override fun onResume() {
        super.onResume()
        verifyLocationEnabled()
    }

    private fun verifyLocationEnabled() {
        val enableAllPermissions = PermissionUtil.hasPermissions(
            context = requireContext(),
            permissions = LocationPermissionLifecycleObserver.mandatoryPermissions
        )

        if(!enableAllPermissions) return

        locationPermissionObserver.setPermissionsEnabled()
    }

    /*************************************************
     * setupLocationObserver
     */
    private fun setupLocationObserver() {
        locationPermissionObserver = LocationPermissionLifecycleObserver(
            activity = requireActivity(),
            registry = requireActivity().activityResultRegistry,
            callback = callback
        )
        lifecycle.addObserver(locationPermissionObserver)
    }

    /*************************************************
     * for request multiple permissions
     */
    private var showLocationDialog: Boolean by mutableStateOf(false)
    private val callback = object : LocationPermissionLifecycleObserver.Callback {
        override fun onOpenRationaleDialog() {
            showLocationDialog = true
        }

        override fun onRequestPermissionsOneMoreTime() {
            requestLocationPermissions()
        }

        override fun onEnabledAllPermissions() {
            Toast.makeText(requireContext(), getString(R.string.location_enabled), Toast.LENGTH_SHORT).show()
        }
    }

    /*************************************************
     * request Location Permissions
     */
    private fun requestLocationPermissions() {
        val enableAllPermissions = PermissionUtil.hasPermissions(
            context = requireContext(),
            permissions = LocationPermissionLifecycleObserver.mandatoryPermissions
        )

        if (enableAllPermissions) {
            locationPermissionObserver.setPermissionsEnabled()
            return
        } else {
            locationPermissionObserver.launcher.launch(LocationPermissionLifecycleObserver.mandatoryPermissions)
        }
    }

//    private fun gotoHome(locationKey: String?) {
//        safeNav(
//            action = R.id.toHome,
//            bundle = bundleOf(Constants.KEY_LOCATION to locationKey)
//        )
//    }

    private fun searchLocationByName(locationName: String?, locationKey: String?) {
        if (locationName == null || locationKey == null) {
            showToast(getString(R.string.wrong_please_try_again_later))
            return
        }

        //viewModel.searchLocationByName(locationName, locationKey)
    }

    @Composable
    override fun ComposeView() {
        super.ComposeView()
        SearchLayout(
            isLocationEnabled = locationPermissionObserver.hasPermissionsEnabled.collectAsState().value,
            locations = viewModel.locations.collectAsState().value,
            onBack = { safeNavigateUp() },
            onAllow = { requestLocationPermissions() },
            onValueChange = { query -> viewModel.searchAutocomplete(keyword = query) },
            onClickLocation = { locationAuto ->

                val cityName = locationAuto.localizedName
                val countryName = locationAuto.country?.localizedName
                val locationKey = locationAuto.key

                Log.d(TAG, "ComposeView - onClickLocation")
                Log.d(TAG, "ComposeView - location ${cityName}, $countryName with location key $locationKey")

                if (locationKey.isNullOrEmpty()) {
                    showToast("There is something wrong. Please, try again !")
                    return@SearchLayout
                }

                viewModel.getWeatherForecastForLocation(locationAuto)
            }
        )
    }
}

@Composable
fun SearchLayout(
    isLocationEnabled: Boolean,
    locations: ImmutableList<LocationAuto>?,
    onBack: ()->Unit = {},
    onAllow: ()->Unit = {},
    onValueChange: (String) -> Unit = {},
    onClickLocation: (LocationAuto) -> Unit = {},
) {
    CoreLayout(
        backgroundBrush = brushManageLocation,
        topBar = {
            SearchTopBar(
                onBack = onBack,
                onValueChange = onValueChange,
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 16.dp)
                    .statusBarsPadding()
            )
        },
        content = {
            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp, vertical = 16.dp)
            ) {
                // button Allow Location permission
                AnimatedVisibility(visible = !isLocationEnabled,
                    content = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(shape = RoundedCornerShape(15.dp))
                                .clickable { onAllow() }
                                .background(
//                                    color = Color(0xFF9BC2D7)
                                    color = Color.White.copy(alpha = 0.3f)
                                )
                                .padding(horizontal = 16.dp, vertical = 16.dp)
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_location_2),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(24.dp)
                            )

                            Spacer(modifier = Modifier.width(10.dp))

                            Text(
                                text = stringResource(R.string.allow_location_permission),
                                style = customizedTextStyle(fontWeight = 400, fontSize = 16, color = Color.White),
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis
                            )

                            Spacer(modifier = Modifier.weight(0.5F))

                            Icon(
                                imageVector = Icons.Default.KeyboardArrowRight,
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    })


                // search location that API returns
                SearchList(
                    locations = locations,
                    onClick = { locationAuto -> onClickLocation(locationAuto) },
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    )
}

@Preview
@Composable
private fun PreviewSearchLayout() {
    SearchLayout(
        isLocationEnabled = false,
        locations = persistentListOf()
    )
}