package com.example.weather.util

import android.util.Log
import androidx.compose.ui.graphics.Brush
import com.example.weather.ui.theme.brushDay
import com.example.weather.ui.theme.brushDreary
import com.example.weather.ui.theme.brushFog
import com.example.weather.ui.theme.brushHazyMoonlight
import com.example.weather.ui.theme.brushMidnight
import com.example.weather.ui.theme.brushMostlyCloudyAndShowers
import com.example.weather.ui.theme.brushMostlyCloudyOrSunny
import com.example.weather.ui.theme.brushNight
import com.example.weather.ui.theme.brushRain
import com.example.weather.ui.theme.brushSnow
import com.example.weather.ui.theme.brushSunrise
import com.example.weather.ui.theme.brushSunset
import com.example.weather.ui.theme.brushWindy
import com.example.weather.util.DateUtil.formatWithPattern
import com.example.weather.util.LocalDateUtil.convertToTimezone
import java.util.Date

object ColorUtil {
    private const val TAG = "ColorUtil"

    /********************************************************
     * return weather background base on weather Icon & is Day
     */
    fun createWeatherBrush(
        weatherIcon: Int,
        timezone: String
    ): Brush {
        // TODO: hour will be removed after completing this function and replease with formattedHour below
        // TODO: exactHour will replace hour position below
        val date = Date()
        val hour: Int = try {
            date.convertToTimezone(timezone).formatWithPattern("HH").toInt()
        } catch (ex: Exception) {
            8
        }

        return when (weatherIcon) {
            1 -> generateBrushForCloudyAndSunny(hour = hour)
            2 -> generateBrushForCloudyAndSunny(hour = hour)
            3 -> generateBrushForCloudyAndSunny(hour = hour)
            4 -> generateBrushForCloudyAndSunny(hour = hour)
            5 -> generateBrushForCloudyAndSunny(hour = hour)
            6 -> generateBrushForCloudyAndSunny(hour = hour)
            7 -> generateBrushForCloudyAndSunny(hour = hour)
            8 -> brushDreary
            11 -> when (hour) {
                in 5..7 -> brushSunrise
                in 8..16 -> brushFog
                in 17..18 -> brushSunset
                in 19..21 -> brushNight
                else -> brushMidnight
            }

            12 -> brushRain
            13 -> when (hour) {
                in 5..7 -> brushSunrise
                in 8..16 -> brushRain
                in 17..18 -> brushSunset
                in 19..23 -> brushNight
                else -> brushMidnight
            }

            14 -> when (hour) {
                in 5..7 -> brushSunrise
                in 8..16 -> brushMostlyCloudyOrSunny
                in 17..18 -> brushSunset
                in 19..23 -> brushNight
                else -> brushMidnight
            }

            15 -> when (hour) {
                in 5..18 -> brushDreary
                in 19..23 -> brushNight
                else -> brushMidnight
            }

            16 -> when (hour) {
                in 5..18 -> brushMostlyCloudyAndShowers
                in 19..23 -> brushNight
                else -> brushMidnight
            }

            17 -> when (hour) {
                in 5..18 -> brushMostlyCloudyAndShowers
                in 19..23 -> brushNight
                else -> brushMidnight
            }

            18 -> when (hour) {
                in 5..7 -> brushSunrise
                in 8..16 -> brushRain
                in 17..18 -> brushSunset
                in 19..23 -> brushNight
                else -> brushMidnight
            }

            19 -> when (hour) {
                in 5..7 -> brushSunrise
                in 8..16 -> brushRain
                in 17..18 -> brushSunset
                in 19..23 -> brushNight
                else -> brushMidnight
            }

            20 -> when (hour) {
                in 5..7 -> brushSunrise
                in 8..16 -> brushSnow
                in 17..18 -> brushSunset
                in 19..23 -> brushNight
                else -> brushMidnight
            }

            21 -> when (hour) {
                in 5..7 -> brushSunrise
                in 8..16 -> brushSnow
                in 17..18 -> brushSunset
                in 19..23 -> brushNight
                else -> brushMidnight
            }

            22, 23 -> when (hour) {
                in 5..7 -> brushSunrise
                in 8..16 -> brushSnow
                in 17..18 -> brushSunset
                in 19..23 -> brushNight
                else -> brushMidnight
            }

            24, 25 -> when (hour) {
                in 5..7 -> brushSunrise
                in 8..16 -> brushRain
                in 17..18 -> brushSunset
                in 19..23 -> brushNight
                else -> brushMidnight
            }

            26, 29 -> when (hour) {
                in 5..7 -> brushSunrise
                in 8..16 -> brushRain
                in 17..18 -> brushSunset
                in 19..23 -> brushNight
                else -> brushMidnight
            }

            30, 31, 32 -> when (hour) {
                in 5..7 -> brushSunrise
                in 8..16 -> brushWindy
                in 17..18 -> brushSunset
                in 19..23 -> brushNight
                else -> brushMidnight
            }

            33, 34, 35, 36 -> when (hour) {
                in 5..7 -> brushSunrise
                in 8..16 -> brushDay
                in 17..18 -> brushSunset
                in 19..23 -> brushNight
                else -> brushMidnight
            }

            37 -> when (hour) {
                in 5..7 -> brushSunrise
                in 8..18 -> brushDay
                in 19..23 -> brushHazyMoonlight
                else -> brushHazyMoonlight
            }

            38 -> when (hour) {
                in 5..7 -> brushSunrise
                in 8..16 -> brushDay
                in 17..18 -> brushSunset
                in 19..23 -> brushNight
                else -> brushMidnight
            }

            39 -> when (hour) {
                in 5..7 -> brushSunrise
                in 8..16 -> brushDay
                in 19..23 -> brushNight
                else -> brushMidnight
            }

            40 -> when (hour) {
                in 5..7 -> brushSunrise
                in 8..16 -> brushRain
                in 17..18 -> brushSunset
                in 19..23 -> brushNight
                else -> brushMidnight
            }

            41, 42 -> when (hour) {
                in 5..18 -> brushMostlyCloudyAndShowers
                in 19..23 -> brushNight
                else -> brushMidnight
            }

            43, 44 -> when (hour) {
                in 5..7 -> brushSunrise
                in 8..16 -> brushSnow
                in 17..18 -> brushSunset
                in 19..23 -> brushNight
                else -> brushMidnight
            }

            else -> {
                Log.d(TAG, "createWeatherBackground - lack of Background")
                generateBrushForCloudyAndSunny(hour = hour)
            }
        }
    }


    /********************************************************
     * return mini weather background base on weather Icon & is Day
     */
    /*fun createMiniWeatherBackground(
        weatherIcon: Int,
        timezone: String
    ): Int {
        val date = Date()
        val hour: Int = try {
            date.convertTo(timezone).formatDate("HH").toInt()
        } catch (ex: Exception) {
            8
        }

        return when (weatherIcon) {
            1 -> when (hour) {
                in 5..7 -> R.drawable.img_mini_cloudy_morning
                in 8..16 -> R.drawable.img_mini_sunny
                in 17..18 -> R.drawable.img_mini_cloudy_sunset
                in 19..21 -> R.drawable.img_mini_cloudy_evening
                else -> R.drawable.img_mini_cloudy_night
            }

            2 -> when (hour) {
                in 5..7 -> R.drawable.img_mini_cloudy_morning
                in 8..16 -> R.drawable.img_mini_most_sunny
                in 17..18 -> R.drawable.img_mini_cloudy_sunset
                in 19..21 -> R.drawable.img_mini_cloudy_evening
                else -> R.drawable.img_mini_cloudy_night
            }

            3 -> when (hour) {
                in 5..7 -> R.drawable.img_mini_cloudy_morning
                in 8..16 -> R.drawable.img_mini_partly_cloudy
                in 17..18 -> R.drawable.img_mini_cloudy_sunset
                in 19..21 -> R.drawable.img_mini_cloudy_evening
                else -> R.drawable.img_mini_cloudy_night
            }

            4 -> when (hour) {
                in 5..7 -> R.drawable.img_mini_cloudy_morning
                in 8..16 -> R.drawable.img_mini_intermittent_cloudy
                in 17..18 -> R.drawable.img_mini_cloudy_sunset
                in 19..21 -> R.drawable.img_mini_cloudy_evening
                else -> R.drawable.img_mini_cloudy_night
            }

            5 -> when (hour) {
                in 5..7 -> R.drawable.img_mini_cloudy_morning
                in 8..16 -> R.drawable.img_mini_hazy_sunshine
                in 17..18 -> R.drawable.img_mini_cloudy_sunset
                in 19..21 -> R.drawable.img_mini_cloudy_evening
                else -> R.drawable.img_mini_cloudy_night
            }

            6, 38 -> when (hour) {
                in 5..7 -> R.drawable.img_mini_cloudy_morning
                in 8..16 -> R.drawable.img_mini_mostly_cloudy
                in 17..18 -> R.drawable.img_mini_cloudy_sunset
                in 19..21 -> R.drawable.img_mini_cloudy_evening
                else -> R.drawable.img_mini_cloudy_night
            }

            7 -> when (hour) {
                in 5..7 -> R.drawable.img_mini_cloudy_morning
                in 8..16 -> R.drawable.img_mini_cloudy
                in 17..18 -> R.drawable.img_mini_cloudy_sunset
                in 19..21 -> R.drawable.img_mini_cloudy_evening
                else -> R.drawable.img_mini_cloudy_night
            }

            8 -> R.drawable.img_mini_dreasy

            11 -> when (hour) {
                in 5..7 -> R.drawable.img_mini_fog_morning
                in 8..16 -> R.drawable.img_mini_fog
                in 17..18 -> R.drawable.img_mini_fog_sunset
                in 19..21 -> R.drawable.img_mini_fog_evening
                else -> R.drawable.img_mini_fog_night
            }

            12 -> when (hour) {
                in 5..7 -> R.drawable.img_mini_shower_morning
                in 8..16 -> R.drawable.img_mini_shower
                in 17..18 -> R.drawable.img_mini_shower_sunset
                in 19..21 -> R.drawable.img_mini_shower_evening
                else -> R.drawable.img_mini_shower_night
            }

            13 -> when (hour) {
                in 5..7 -> R.drawable.img_mini_mostly_cloudy_shower_morning
                in 8..16 -> R.drawable.img_mini_mostly_cloudy
                in 17..18 -> R.drawable.img_mini_mostly_cloudy_shower_sunset
                in 19..21 -> R.drawable.img_mini_mostly_cloudy_shower_evening
                else -> R.drawable.img_mini_mostly_cloudy_shower_night
            }

            14 -> when(hour) {
                in 5..7 -> R.drawable.img_mini_partly_sunny_shower_morning
                in 8..16 -> R.drawable.img_mini_partly_sunny_shower
                in 17..18 -> R.drawable.img_mini_partly_sunny_shower_sunset
                in 19..21 -> R.drawable.img_mini_partly_sunny_shower_evening
                else -> R.drawable.img_mini_partly_sunny_shower_night
            }

            15 -> when(hour) {
                in 5..7 -> R.drawable.img_mini_tstorm_morning
                in 8..16 -> R.drawable.img_mini_tstorm
                in 17..18 -> R.drawable.img_mini_tstorm_sunset
                in 19..21 -> R.drawable.img_mini_tstorm_evening
                else -> R.drawable.img_mini_tstorm_night
            }

            16, 17, 40 -> when(hour) {
                in 5..7 -> R.drawable.img_mini_mostly_cloudy_tstorm
                in 8..16 -> R.drawable.img_mini_mostly_cloudy_tstorm
                in 17..18 -> R.drawable.img_mini_mostly_cloudy_tstorm
                in 19..21 -> R.drawable.img_mini_mostly_cloudy_tstorm_evening
                else -> R.drawable.img_mini_mostly_cloudy_tstorm_night
            }

            18 -> when(hour) {
                in 5..7 -> R.drawable.img_mini_rain_morning
                in 8..16 -> R.drawable.img_mini_rain
                in 17..18 -> R.drawable.img_mini_rain_sunset
                in 19..21 -> R.drawable.img_mini_rain_evening
                else -> R.drawable.img_mini_rain_night
            }

            19 -> when(hour) {
                in 5..7 -> R.drawable.img_mini_flurries_morning
                in 8..16 -> R.drawable.img_mini_flurries_morning
                in 17..18 -> R.drawable.img_mini_flurries_sunset
                in 19..21 -> R.drawable.img_mini_flurries_evening
                else -> R.drawable.img_mini_flurries_night
            }

            20, 21 -> when(hour) {
                in 5..7 -> R.drawable.img_mini_mostly_cloud_flurries_morning
                in 8..16 -> R.drawable.img_mini_mostly_cloud_flurries
                in 17..18 -> R.drawable.img_mini_mostly_cloud_flurries_sunset
                in 19..21 -> R.drawable.img_mini_mostly_cloud_flurries_evening
                else -> R.drawable.img_mini_mostly_cloud_flurries_night
            }

            22 -> when(hour) {
                in 5..7 -> R.drawable.img_mini_snow_morning
                in 8..16 -> R.drawable.img_mini_snow
                in 17..18 -> R.drawable.img_mini_snow_sunset
                in 19..21 -> R.drawable.img_mini_snow_evening
                else -> R.drawable.img_mini_snow_night
            }

            23 -> when(hour) {
                in 5..7 -> R.drawable.img_mini_mostly_cloudy_snow_morning
                in 8..16 -> R.drawable.img_mini_mostly_cloudy_snow
                in 17..18 -> R.drawable.img_mini_mostly_cloudy_snow_sunset
                in 19..21 -> R.drawable.img_mini_mostly_cloudy_snow_evening
                else -> R.drawable.img_mini_mostly_cloudy_snow_night
            }

            25 -> when(hour) {
                in 5..7 -> R.drawable.img_mini_sleet_morning
                in 8..16 -> R.drawable.img_mini_sleet
                in 17..18 -> R.drawable.img_mini_sleet_sunset
                in 19..21 -> R.drawable.img_mini_sleet_evening
                else -> R.drawable.img_mini_sleet_night
            }

            29 -> when(hour) {
                in 5..7 -> R.drawable.img_mini_rain_snow_morning
                in 8..16 -> R.drawable.img_mini_rain_snow
                in 17..18 -> R.drawable.img_mini_rain_snow_sunset
                in 19..21 -> R.drawable.img_mini_rain_snow_evening
                else -> R.drawable.img_mini_rain_snow_night
            }

            32 -> when(hour) {
                in 5..7 -> R.drawable.img_mini_windy_morning
                in 8..16 -> R.drawable.img_mini_windy
                in 17..18 -> R.drawable.img_mini_windy_sunset
                in 19..21 -> R.drawable.img_mini_windy_evening
                else -> R.drawable.img_mini_windy_night
            }

            33 -> when(hour) {
                in 5..7 -> R.drawable.img_mini_windy_morning
                in 8..16 -> R.drawable.img_mini_windy
                in 17..18 -> R.drawable.img_mini_windy_sunset
                in 19..21 -> R.drawable.img_mini_clear_evening
                else -> R.drawable.img_mini_clear_night
            }

            34 -> when(hour) {
                in 5..7 -> R.drawable.img_mini_windy_morning
                in 8..16 -> R.drawable.img_mini_windy
                in 17..18 -> R.drawable.img_mini_windy_sunset
                in 19..21 -> R.drawable.img_mini_most_clear_evening
                else -> R.drawable.img_mini_most_clear_night
            }

            35, 39 -> when(hour) {
                in 5..7 -> R.drawable.img_mini_cloudy_morning
                in 8..16 -> R.drawable.img_mini_partly_cloudy
                in 17..18 -> R.drawable.img_mini_cloudy_sunset
                in 19..21 -> R.drawable.img_mini_partly_cloudy_evening
                else -> R.drawable.img_mini_partly_cloudy_night
            }

            36 -> when(hour) {
                in 5..7 -> R.drawable.img_mini_cloudy_morning
                in 8..16 -> R.drawable.img_mini_partly_cloudy
                in 17..18 -> R.drawable.img_mini_cloudy_sunset
                in 19..21 -> R.drawable.img_mini_intermittent_cloudy_evening
                else -> R.drawable.img_mini_intermittent_cloudy_night
            }

            37 -> when(hour) {
                in 5..7 -> R.drawable.img_mini_cloudy_morning
                in 8..16 -> R.drawable.img_mini_partly_cloudy
                in 17..18 -> R.drawable.img_mini_cloudy_sunset
                in 19..21 -> R.drawable.img_mini_hazy_moonlight_evening
                else -> R.drawable.img_mini_hazy_moonlight_night
            }

            else -> R.drawable.img_mini_sunny
        }
    }*/


    /******************************************
     * This function is used to generate brush for the following weather:
     * 1. Cloudy - OK
     * 2. Sunny - OK
     * 3. Mostly Sunny - OK
     * 4. Partly Cloudy - OK
     * 5. Intermittent Clouds - OK
     * 6. Sunny - OK
     * 7. Mostly Cloudy - OK
     * 8. Mostly Sunny - OK
     * 9. Partly Sunny - OK
     * 10. Hazy Sunshine - OK
     */
    private fun generateBrushForCloudyAndSunny(hour: Int): Brush {
        return when (hour) {
            in 5..7 -> brushSunrise
            in 8..16 -> brushDay
            in 17..18 -> brushSunset
            in 19..21 -> brushNight
            else -> brushMidnight
        }
    }
}