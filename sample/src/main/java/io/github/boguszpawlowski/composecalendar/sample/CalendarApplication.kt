package io.github.boguszpawlowski.composecalendar.sample

import android.app.Application
import com.jakewharton.threetenabp.AndroidThreeTen

public class CalendarApplication: Application() {
  override fun onCreate() {
    super.onCreate()
    AndroidThreeTen.init(this)
  }
}