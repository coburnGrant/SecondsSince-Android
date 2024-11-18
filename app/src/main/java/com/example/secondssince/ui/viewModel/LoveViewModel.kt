package com.example.secondssince.ui.viewModel

import androidx.annotation.DrawableRes
import androidx.lifecycle.ViewModel
import com.example.secondssince.R
import com.example.secondssince.model.Love
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.ChronoUnit
import java.util.Date
import java.util.Locale
import java.util.Timer
import kotlin.concurrent.timerTask

data class SecondsSinceUIState(
    val totalYears: String = "",
    val totalMonths: String = "",
    val totalWeeks: String = "",
    val totalDays: String = "",
    val totalHours: String = "",
    val totalMinutes: String = "",
    val totalSeconds: String = "",
    val totalTimeTogether: String = ""
) {
    val details: List<String>
        get() {
            return listOf(totalYears, totalMonths, totalWeeks, totalDays, totalHours, totalMinutes, totalSeconds)
        }
}

class LoveViewModel(
    val love: Love
): ViewModel() {
    val timer = Timer()
    var userName: String = love.userName
    var loveName: String = love.loveName
    @DrawableRes var image: Int = R.drawable.grant_and_dream

    var formattedAnniversary: String = formatDate(love.anniversary)

    var combinedNames: String = "${love.userName} & ${love.loveName}"

    private val _secondsSinceUIState = MutableStateFlow(SecondsSinceUIState())
    val secondsSinceUIState: StateFlow<SecondsSinceUIState> = _secondsSinceUIState.asStateFlow()

    init {
        startTimer()
    }

    private fun startTimer() {
        timer.schedule(timerTask { updateUIState() }, 0, 1000)
    }

    private fun updateUIState() {
        _secondsSinceUIState.update { secondsSinceUIState ->
            val now = LocalDateTime.now()
            val anniversaryTime = love.anniversary
                .toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()

            val formatter = NumberFormat.getNumberInstance(Locale.getDefault())

            val years = ChronoUnit.YEARS.between(anniversaryTime, now)
            val months = ChronoUnit.MONTHS.between(anniversaryTime, now)
            val weeks = ChronoUnit.WEEKS.between(anniversaryTime, now)
            val days = ChronoUnit.DAYS.between(anniversaryTime, now)
            val hours = ChronoUnit.HOURS.between(anniversaryTime, now)
            val minutes = ChronoUnit.MINUTES.between(anniversaryTime, now)
            val seconds = ChronoUnit.SECONDS.between(anniversaryTime, now)

            // Format the long and short versions
            val totalTimeTogetherLong = listOfNotNull(
                if (years > 0) "$years years" else null,
                if (months % 12 > 0) "${months % 12} months" else null,
                if (hours % 24 > 0) "${hours % 24} hours" else null,
                if (minutes % 60 > 0) "${minutes % 60} minutes" else null,
                if (seconds % 60 > 0) "${seconds % 60} seconds" else null
            ).joinToString(", ")

            val totalYears = formatTime(formatter, years, "year")
            val totalMonths = formatTime(formatter, months, "month")
            val totalWeeks = formatTime(formatter, weeks, "week")
            val totalDays = formatTime(formatter, days, "day")
            val totalHours = formatTime(formatter, hours, "hour")
            val totalMinutes = formatTime(formatter, minutes, "minute")
            val totalSeconds = formatTime(formatter, seconds, "second")

            secondsSinceUIState.copy(
                totalYears = totalYears,
                totalWeeks = totalWeeks,
                totalMonths = totalMonths,
                totalDays = totalDays,
                totalHours = totalHours,
                totalMinutes = totalMinutes,
                totalSeconds = totalSeconds,
                totalTimeTogether = totalTimeTogetherLong
            )
        }
    }

    private fun formatTime(formatter: NumberFormat, duration: Long, unit: String): String {
        return "${formatter.format(duration)} ${if (duration == 1L) unit else "${unit}s"}"
    }

    private fun formatDate(date: Date): String {
        val formatter = SimpleDateFormat("MMMM d, yyyy", Locale.getDefault())
        return formatter.format(date)
    }
}