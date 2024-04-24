package me.pegbeer.filmku.util

import org.threeten.bp.LocalDateTime
import org.threeten.bp.format.DateTimeFormatter


fun Int.toHourMinuteString(): String {
    val hours = this / 60
    val minutes = this % 60
    return if (hours > 0) {
        if (minutes > 0) {
            String.format("%dh %dm",hours,minutes)
        } else {
            String.format("%dh",hours)
        }
    } else {
        String.format("%dm",minutes)
    }
}

fun String.toDate():LocalDateTime{
    val format = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return LocalDateTime.parse(this,format)
}