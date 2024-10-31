package com.example.weather.util

import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.TimeZone


object LocalDateUtil {

    fun currentEpochDay(): Long = LocalDate.now().toEpochDay()


    fun LocalDateTime.elapsedMinutes(): Int {
        return hour * 60 + minute
    }

    /**
     * return the number of seconds have been elapsed.
     * for instance, now, 15h30 then elapsed seconds is
     * 15(hours) * 60 * 60 + 30(minutes) * 60 + seconds
     * */
    fun LocalDateTime.elapsedSeconds(): Int {
        return this.hour * 60 * 60 + minute * 60 + second
    }

    /**
     * convert from local date to date
     */
    fun LocalDate.toDate(): Date {
        return Date.from(this.atStartOfDay(ZoneId.systemDefault()).toInstant())
    }

    /**
     * convert from Epoch day to date string
     * for example: LocalDate.now().toEpochDay().toDateString() -> 04/04/2024
     */
    fun Long.toDateWithPattern(pattern: String = "dd/MM/yyyy"): String {
        val localDate = LocalDate.ofEpochDay(this)
        val formatter = DateTimeFormatter.ofPattern(pattern)
        val formattedString = localDate.format(formatter)

        return formattedString
    }

    /**
     * for instance: today is 12-04-2024 then start day of week is Monday, 08-04-2024
     */
    fun LocalDate.startDayOfWeek(): LocalDate {
        return this.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY))
    }

    /**
     * for instance: today is 12-04-2024 then last day of week is SUNDAY, 14-04-2024
     */
    fun LocalDate.lastDayOfWeek(): LocalDate {
        return this.with(TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY))
    }


    /**
     * Epoch Date Time to Date
     */
    fun Long.toDate(): Date {
        val instant = Instant.ofEpochSecond(this)
        val dateTime = LocalDateTime.ofInstant(instant, ZoneId.systemDefault())
        val outcome = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant())
        return outcome
    }

    fun Date.convertToTimezone(timezone: String): Date {
        return try {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd hh:mm:ssa", Locale.getDefault())
            simpleDateFormat.timeZone = TimeZone.getTimeZone(timezone)

            val sdfLocal = SimpleDateFormat("yyyy-MM-dd hh:mm:ssa", Locale.getDefault())
            sdfLocal.parse(simpleDateFormat.format(this)) ?: Date()
        } catch (ex: Exception) {
            Calendar.getInstance().time
        }
    }
}