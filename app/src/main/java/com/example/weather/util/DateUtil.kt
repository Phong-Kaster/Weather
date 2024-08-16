package com.example.weather.util

import android.annotation.SuppressLint
import android.util.Log
import java.text.SimpleDateFormat
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

//        Log.d("TAG", "getCurrentCondition - fromDate = $fromDate")
//        Log.d("TAG", "getCurrentCondition - toDate = $toDate")
//        Log.d("TAG", "getCurrentCondition - $days days $hours hours $minutes minutes $seconds seconds")

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


//        Log.d("TAG", "calculatePercent --------------------")
//        Log.d("TAG", "calculatePercent - sunrise = $sunrise")
//        Log.d("TAG", "calculatePercent - sunset =  $sunset")
//        Log.d("TAG", "calculatePercent - distance = $distance")
//        Log.d("TAG", "calculatePercent - elapsedDistance = $elapsedDistance")
//        Log.d("TAG", "calculatePercent - percent = $percent ")
//        Log.d("TAG", "calculatePercent - percent = ${(elapsedDistance / distance)} ")
//        Log.d("TAG", "calculatePercent - percent = ${(elapsedDistance - distance)} ")
//        Log.d("TAG", "calculatePercent - percent = ${(elapsedDistance * distance)} ")
//        Log.d("TAG", "calculatePercent - percent = ${(elapsedDistance + distance)} ")
        return if (percent > 1) 1f else percent.toFloat()
    }

    fun fromLongToDate(time: Long?): Date {
        return if (time == null) Date() else Date(time)
    }

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
}