package io.github.boguszpawlowski.composecalendar.week

import io.github.boguszpawlowski.composecalendar.day.WeekDay
import io.github.boguszpawlowski.composecalendar.util.daysUntil
import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate
import org.threeten.bp.YearMonth

internal const val DaysInAWeek = 7

internal fun YearMonth.getWeeks(
  includeAdjacentMonths: Boolean,
  firstDayOfTheWeek: DayOfWeek,
  today: LocalDate = LocalDate.now(),
): List<WeekDays> {
  val daysLength = lengthOfMonth()

  val starOffset = atDay(1).dayOfWeek daysUntil firstDayOfTheWeek
  val endOffset =
    DaysInAWeek - (atDay(daysLength).dayOfWeek daysUntil firstDayOfTheWeek) - 1

  return (1 - starOffset..daysLength + endOffset).chunked(DaysInAWeek).mapIndexed { index, days ->
    WeekDays(
      isFirstWeekOfTheMonth = index == 0,
      days = days.mapNotNull { dayOfMonth ->
        val (date, isFromCurrentMonth) = when (dayOfMonth) {
          in Int.MIN_VALUE..0 -> if (includeAdjacentMonths) {
            val previousMonth = this.minusMonths(1)
            previousMonth.atDay(previousMonth.lengthOfMonth() + dayOfMonth) to false
          } else {
            return@mapNotNull null
          }
          in 1..daysLength -> atDay(dayOfMonth) to true
          else -> if (includeAdjacentMonths) {
            val previousMonth = this.plusMonths(1)
            previousMonth.atDay(dayOfMonth - daysLength) to false
          } else {
            return@mapNotNull null
          }
        }

        WeekDay(
          date = date,
          isFromCurrentMonth = isFromCurrentMonth,
          isCurrentDay = date.equals(today),
        )
      }
    )
  }
}
