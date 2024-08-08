package com.example.weather.lifecycleobserver

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.ActivityResultRegistry
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.example.weather.R
import com.example.weather.configuration.PermissionName
import com.example.weather.configuration.isEnabled
import com.example.weather.util.PermissionUtil

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class LocationPermissionLifecycleObserver
@Inject
constructor(
    private val registry: ActivityResultRegistry,
    private val activity: Activity,
    private val callback: Callback
) : DefaultLifecycleObserver {
    lateinit var launcher: ActivityResultLauncher<Array<PermissionName>>
    lateinit var settingLauncher: ActivityResultLauncher<Intent>

    private var _hasPermissionsEnabled = MutableStateFlow(false)
    val hasPermissionsEnabled = _hasPermissionsEnabled.asStateFlow()


    companion object {
        val mandatoryPermissions = arrayOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    }

    override fun onCreate(owner: LifecycleOwner) {
        launcher = createRuntimeLauncher(owner)
        settingLauncher = createSettingLauncher(owner)
    }

    fun setPermissionsEnabled() { _hasPermissionsEnabled.value = true }

    private fun createRuntimeLauncher(owner: LifecycleOwner): ActivityResultLauncher<Array<PermissionName>> {
        return registry.register(
            "locationPermissionLauncher",
            owner,
            ActivityResultContracts.RequestMultiplePermissions()
        ) { map: Map<PermissionName, isEnabled> ->
            val enableAccessCoarseLocation = map[Manifest.permission.ACCESS_COARSE_LOCATION]
            val enableAccessFineLocation = map[Manifest.permission.ACCESS_FINE_LOCATION]


            if (enableAccessCoarseLocation == true && enableAccessFineLocation == true) {
                // TODO: do nothing because everything we need is OK
                _hasPermissionsEnabled.value = true
                callback.onEnabledAllPermissions()
            } else {
                if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                    // TODO: do nothing because Android system will request automatically
                } else if (ActivityCompat.shouldShowRequestPermissionRationale(activity, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    // TODO: do nothing because Android system will request automatically
                } else {
                    callback.onOpenRationaleDialog()
                }
            }
        }
    }

    private fun createSettingLauncher(owner: LifecycleOwner): ActivityResultLauncher<Intent> {
        return registry.register(
            "SettingPermissionLauncher",
            owner,
            ActivityResultContracts.StartActivityForResult()
        ) {
            val enableAllPermissions = PermissionUtil.hasPermissions(
                context = activity,
                permissions = mandatoryPermissions
            )

            if (enableAllPermissions) {
                Toast.makeText(activity, activity.getString(R.string.location_enabled), Toast.LENGTH_SHORT).show()
                _hasPermissionsEnabled.value = true
                callback.onEnabledAllPermissions()
            } else {
                callback.onRequestPermissionsOneMoreTime()
            }
        }
    }


    interface Callback {
        fun onOpenRationaleDialog()

        fun onRequestPermissionsOneMoreTime()

        fun onEnabledAllPermissions()
    }
}