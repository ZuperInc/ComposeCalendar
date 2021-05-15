package com.bpawlowski.composecalendar.sample

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bpawlowski.composecalendar.Calendar
import com.bpawlowski.composecalendar.rememberCalendarState
import com.bpawlowski.composecalendar.selection.SelectionMode
import com.bpawlowski.composecalendar.selection.SelectionState
import java.time.DayOfWeek

class MainActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      MainScreen()
    }
  }
}

@Composable
fun MainScreen() {
  MaterialTheme {
    Column(
      modifier = Modifier
        .verticalScroll(rememberScrollState())
        .padding(horizontal = 8.dp)
    ) {

      val calendarState = rememberCalendarState()

      Calendar(
        calendarState = calendarState,
        modifier = Modifier
          .padding(vertical = 8.dp)
          .animateContentSize(),
        monthContainer = { MonthContainer(it) },
        firstDayOfWeek = DayOfWeek.MONDAY,
      )

      SelectionControls(selectionState = calendarState.selectionState)
    }
  }
}

@Composable
fun MonthContainer(content: @Composable () -> Unit) {
  Card(
    elevation = 0.dp,
    shape = RoundedCornerShape(10.dp),
    border = BorderStroke(1.dp, Color.LightGray),
    content = content,
  )
}

@Composable
fun SelectionControls(
  selectionState: SelectionState,
) {
  Text(
    text = "Calendar Selection Mode",
    style = MaterialTheme.typography.h5,
  )
  SelectionMode.values().forEach { selectionMode ->
    Row(modifier = Modifier.fillMaxWidth()) {
      RadioButton(
        selected = selectionState.selectionMode == selectionMode,
        onClick = { selectionState.selectionMode = selectionMode }
      )
      Text(text = selectionMode.name)
      Spacer(modifier = Modifier.height(4.dp))
    }
  }
}