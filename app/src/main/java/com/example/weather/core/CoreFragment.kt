package com.example.jetpack.core

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.example.weather.data.repository.SettingRepository
import com.example.weather.ui.theme.DarkCustomizedTheme
import com.example.weather.ui.theme.LightCustomizedTheme
import com.example.weather.ui.theme.WeatherTheme
import com.example.weather.util.AppUtil
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale
import javax.inject.Inject


val LocalLocale = staticCompositionLocalOf { Locale.getDefault() }
val LocalNavController = staticCompositionLocalOf<NavController?> { null }
val LocalTheme = compositionLocalOf { DarkCustomizedTheme }

@AndroidEntryPoint
open class CoreFragment : Fragment(), CoreBehavior {
    protected val TAG = this.javaClass.simpleName

    @Inject
    lateinit var settingRepository: SettingRepository

    var enableDarkTheme by mutableStateOf(false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(TAG, "onViewCreated: ")
        setupDarkMode()
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        makeStatusBarTransparent()
        return ComposeView(requireActivity()).apply {
            setContent {
                CompositionLocalProvider(
                    LocalNavController provides findNavController(),
                    LocalLocale provides requireActivity().resources.configuration.locales[0],
                    *compositionLocalProvider().toTypedArray(),
                    LocalTheme provides if (enableDarkTheme) DarkCustomizedTheme else LightCustomizedTheme,
                ) {
//                    CustomizedWeatherTheme(
//                        darkTheme = enableDarkTheme,
//                        content = {
                            WeatherTheme {
                                ComposeView()
                            }
//                        })
                }
            }
        }
    }

    @Composable
    protected open fun compositionLocalProvider(): List<ProvidedValue<*>> {
        return listOf()
    }

    @Composable
    open fun ComposeView() {}

    override fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }

    override fun isInternetConnected(): Boolean {
        return AppUtil.isInternetConnected(context = requireContext())
    }

    override fun hideNavigationBar() {
        AppUtil.hideNavigationBar(window = requireActivity().window)
    }

    override fun trackEvent(name: String) {}

    override fun showLoading() {}
    override fun makeStatusBarTransparent() {
        /*with(requireActivity().window) {
            setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        }*/
    }

    private fun setupDarkMode() {
        enableDarkTheme = settingRepository.enableDarkTheme()
    }
}