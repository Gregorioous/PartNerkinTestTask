package org.nerkin.project.extensions

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.temporal.ChronoUnit

@RequiresApi(Build.VERSION_CODES.O)
fun calculateDuration(startDate: String?, endDate: String?): String {
    if (startDate == null || endDate == null) return "1"
    val start = LocalDate.parse(startDate)
    val end = LocalDate.parse(endDate)
    val days = ChronoUnit.DAYS.between(start, end) + 1
    return days.toString()
}