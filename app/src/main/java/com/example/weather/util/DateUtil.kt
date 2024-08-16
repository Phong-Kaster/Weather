package com.example.weather.util

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.Instant
import java.util.Calendar
import java.util.Date
import java.util.Locale
import kotlin.math.abs

object DateUtil {
    /**
     * HOUR FORMAT - https://www.digitalocean.com/community/tutorials/java-simpledateformat-java-date-format
     * K -> Hour in am/pm for 12 hour format (0-11)
     * H -> Hour in the day (0-23)
     * h -> Hour in am/pm for 12 hour format (1-12)
     * */
    const val PATTERN_HH_mm = "HH:mm" // 09:15
    const val PATTERN_HH_mm_ss = "HH:mm:ss" // 09:15:45
    const val PATTERN_hh_mm_ss = "hh:mm:ss" // 09:15:45
    const val PATTERN_hh_mm_aa = "hh:mm aa" // 09:15 AM
    const val PATTERN_hh_mm_ss_aa = "KK:mm:ss aa" // 09:15:50 AM

    // DAY FORMAT
    const val PATTERN_EEE_MMM_dd = "EEE, MMM dd" // Mon, December 01
    const val PATTERN_EEEE = "EEEE" // Monday
    const val PATTERN_EEE = "EEE" // Mon
    const val PATTERN_YYYY = "YYYY" // 2024
    const val PATTERN_dd_MMM = "dd MMM" // 14 DEC
    const val PATTERN_MMM = "MMM" // 14 DEC
    const val PATTERN_dd = "dd" // 14
    const val PATTERN_FULL_DATE_TIME = "hh:mm aa EEE, MMM dd YYYY"
    const val PATTERN_FULL_DATE_TIME_REVERSED = "EEE, MMM dd YYYY hh:mm aa "

    @SuppressLint("SimpleDateFormat")
    fun Date.formatWithPattern(pattern: String, locale: Locale = Locale.getDefault()): String {
        val simpleDateFormat = SimpleDateFormat(pattern, locale)
        return simpleDateFormat.format(this@formatWithPattern)
    }


    /*******************************
     * compute difference days between two dates
     * for instance: from 08-08-2024 to 09-08-2024 differs 1 days 2 hours 34 minutes 42 seconds+
     */
    fun computeDifferenceDaysBetweenTwoDates(
        fromDate: Date,
        toDate: Date
    ): Long {

        val difference: Long = toDate.time - fromDate.time
        val seconds = difference / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        val days = hours / 24

        return days
    }

    /*******************************
     * compute difference days between two dates
     * for instance: from 08-08-2024 to 09-08-2024 differs 1 days 2 hours 34 minutes 42 seconds+
     */
    fun computeDifferenceHoursBetweenTwoDates(
        fromDate: Date,
        toDate: Date
    ): Long {
        val difference: Long = fromDate.time - toDate.time
        val seconds = difference / 1000
        val minutes = seconds / 60
        val hours = minutes / 60
        return hours
    }

    /*******************************
     * compute difference days between two dates
     * for instance: from 08-08-2024 to 09-08-2024 differs 1 days 2 hours 34 minutes 42 seconds+
     */
    fun computeDifferenceMinutesBetweenTwoDates(
        fromDate: Date,
        toDate: Date
    ): Long {
        val difference: Long = fromDate.time - toDate.time
        val seconds = difference / 1000
        val minutes = seconds / 60
        return minutes
    }

    /*******************************
     * compute difference days between two dates
     * for instance: from 08-08-2024 to 09-08-2024 differs 1 days 2 hours 34 minutes 42 seconds+
     */
    fun computeDifferenceSecondsBetweenTwoDates(
        fromDate: Date,
        toDate: Date
    ): Long {
        val difference: Long = fromDate.time - toDate.time
        val seconds = difference / 1000
        return seconds
    }

    fun isLocalNotOutdated(fromDate: Date, toDate: Date): Boolean {
        val differenceHour =
            computeDifferenceDaysBetweenTwoDates(fromDate = fromDate, toDate = toDate)
        val expiryHour = 12
        return differenceHour < expiryHour
    }

    /*********************************************************
     * calculate percent from sunrise to sunset
     */
    fun calculatePercent(sunrise: Date, sunset: Date): Float {
        val calendarSunrise = Calendar.getInstance()
        calendarSunrise.time = sunrise

        val calendarSunset = Calendar.getInstance()
        calendarSunset.time = sunset

        val calendarNow = Calendar.getInstance()
        calendarNow.time = Date()

        val distance: Long = abs(calendarSunrise.timeInMillis - calendarSunset.timeInMillis)
        val elapsedDistance: Long = abs(calendarNow.timeInMillis - calendarSunrise.timeInMillis)
        val percent = (elapsedDistance.toDouble() / distance)

        return if (percent > 1) 1f else percent.toFloat()
    }

    /*********************************************************
     * from Long to Date
     */
    fun fromLongToDate(time: Long?): Date {
        return if (time == null) Date() else Date(time)
    }

    /*******************************************************
     * create date with specific year, month, day of month
     */
    fun createDate(
        year: Int = Calendar.getInstance().get(Calendar.YEAR),
        month: Int = Calendar.getInstance().get(Calendar.MONTH),
        dayOfMonth: Int = Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
        hourOfDay: Int = Calendar.getInstance().get(Calendar.HOUR_OF_DAY),
        minute: Int = Calendar.getInstance().get(Calendar.MINUTE),
    ): Date {
        val calendar = Calendar.getInstance()

        calendar.time = Date()
        calendar.set(Calendar.YEAR, year)
        calendar.set(Calendar.MONTH, month)
        calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)

        return calendar.time
    }

    /*******************************************************
     * calculate Initial Delay
     */
    fun calculateInitialDelay(
        targetHour: Int, targetMinute: Int
    ): Long {
        val now = Calendar.getInstance()
        val targetTime = Calendar.getInstance().apply {
            timeInMillis = now.timeInMillis
            set(Calendar.HOUR_OF_DAY, targetHour)
            set(Calendar.MINUTE, targetMinute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (timeInMillis < now.timeInMillis) { // If target time is in the past, add a day
                add(Calendar.DAY_OF_YEAR, 1)
            }
        }
        return targetTime.timeInMillis - now.timeInMillis
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun fromEpochDateTimeToDate(epochDateTime: Long): Date{
        val instant = Instant.ofEpochSecond(epochDateTime)
        val date = Date.from(instant)
        return date
    }
}