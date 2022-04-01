package io.github.boguszpawlowski.composecalendar.header

import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.setValue
import java.time.YearMonth

@Suppress("FunctionName") // Factory function
public fun MonthState(
  initialMonth: YearMonth,
  previousMonth: YearMonth = initialMonth,
): MonthState = MonthStateImpl(initialMonth, previousMonth)

@Stable
public interface MonthState {
  public var currentMonth: YearMonth

  public fun gePreviousMonth(): YearMonth

  public companion object {
    @Suppress("FunctionName") // Factory function
    public fun Saver(): Saver<MonthState, String> = Saver(
      save = {
        it.currentMonth.toString() + "##" + it.gePreviousMonth().toString()
      },
      restore = {
        MonthState(
          YearMonth.parse(it.split("##").first()),
          YearMonth.parse(it.split("##").last())
        )
      }
    )
  }
}

@Stable
private class MonthStateImpl(
  initialMonth: YearMonth,
  previousMonth: YearMonth,
) : MonthState {

  private var _currentMonth by mutableStateOf<YearMonth>(initialMonth)

  private var _previousMonth: YearMonth = previousMonth

  override var currentMonth: YearMonth
    get() = _currentMonth
    set(value) {
      _previousMonth = currentMonth
      _currentMonth = value
    }

  override fun gePreviousMonth(): YearMonth {
    return _previousMonth
  }
}
