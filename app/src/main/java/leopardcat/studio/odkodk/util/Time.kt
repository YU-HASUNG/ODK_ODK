package leopardcat.studio.odkodk.util

import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun getCurrentDateFormatted(): String {
    val currentDate = LocalDate.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return currentDate.format(formatter)
}

fun getYesterdayDateFormatted(): String {
    val yesterdayDate = LocalDate.now().minusDays(1)
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    return yesterdayDate.format(formatter)
}

fun shouldUseYesterdayData(): Boolean {
    val currentTime = LocalTime.now()
    val thresholdTime = LocalTime.of(12, 0) //12시 기준
    return currentTime.isBefore(thresholdTime)
}

fun getDate(): String {
    val currentDateFormatted = if (shouldUseYesterdayData()) {
        getYesterdayDateFormatted()
    } else {
        getCurrentDateFormatted()
    }
    return currentDateFormatted
}

fun getYesterday(): String {
    val yesterdayDate = LocalDate.now().minusDays(1)
    val formatter = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")
    return yesterdayDate.format(formatter)
}