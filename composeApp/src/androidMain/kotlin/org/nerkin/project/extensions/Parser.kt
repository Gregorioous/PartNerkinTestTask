package org.nerkin.project.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun String.parseDayMonth(): Pair<String, String> {
    return try {
        val dt = LocalDate.parse(this.substring(0, 10), DateTimeFormatter.ISO_DATE)
        val day = dt.dayOfMonth.toString()
        val month = dt.month.getDisplayName(TextStyle.SHORT, Locale.ENGLISH).lowercase()
        day to month
    } catch (e: Exception) {
        "" to ""
    }
}

@RequiresApi(Build.VERSION_CODES.O)
fun parseDate(dateStr: String): String {
    val date = LocalDate.parse(dateStr)
    return "${date.dayOfMonth} ${
        date.month.getDisplayName(
            TextStyle.FULL,
            Locale("ru")
        )
    } ${date.year}"
}


