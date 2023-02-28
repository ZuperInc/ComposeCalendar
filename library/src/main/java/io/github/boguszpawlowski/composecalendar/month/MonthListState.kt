package io.github.boguszpawlowski.composecalendar.month

import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Stable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import io.github.boguszpawlowski.composecalendar.header.MonthState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.threeten.bp.YearMonth
import org.threeten.bp.temporal.ChronoUnit

@Stable
internal class MonthListState(
  private val coroutineScope: CoroutineScope,
  private val initialMonth: YearMonth,
  private val monthState: MonthState,
  private val listState: LazyListState,
) {

  private val currentlyVisibleMonth by derivedStateOf {
    getMonthForPage(listState.firstVisibleItemIndex)
  }

  init {
    snapshotFlow { monthState.currentMonth }.onEach { month ->
      moveToMonth(month)
    }.launchIn(coroutineScope)

    snapshotFlow { currentlyVisibleMonth }.onEach { newMonth ->
      monthState.currentMonth = newMonth
    }.launchIn(coroutineScope)
  }

  fun getMonthForPage(index: Int): YearMonth =
    initialMonth.plusMonths((index - StartIndex).toLong())

  private fun moveToMonth(month: YearMonth) {
    if (month == currentlyVisibleMonth) return
    initialMonth.minus(month).let { offset ->
      coroutineScope.launch {
        listState.animateScrollToItem((StartIndex - offset).toInt())
      }
    }
  }

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as MonthListState

    if (monthState != other.monthState) return false
    if (listState != other.listState) return false

    return true
  }

  override fun hashCode(): Int {
    var result = monthState.hashCode()
    result = 31 * result + listState.hashCode()
    return result
  }
}

private operator fun YearMonth.minus(other: YearMonth) =
  ChronoUnit.MONTHS.between(other, this)

internal const val PagerItemCount = 20_000
internal const val StartIndex = PagerItemCount / 2
