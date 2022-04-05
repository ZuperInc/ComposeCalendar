package io.github.boguszpawlowski.composecalendar.util

import org.threeten.bp.DayOfWeek
import org.threeten.bp.LocalDate

internal fun Collection<LocalDate>.addOrRemoveIfExists(date: LocalDate) =
  if (contains(date)) {
    this - date
  } else {
    this + date
  }

internal infix fun DayOfWeek.daysUntil(other: DayOfWeek) = (7 + (value - other.value)) % 7
