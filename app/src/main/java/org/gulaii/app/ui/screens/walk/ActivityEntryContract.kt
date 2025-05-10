package org.gulaii.app.ui.screens.walk

import org.gulaii.app.data.repository.ActivityType
import java.time.LocalDateTime
import androidx.compose.runtime.State

interface ActivityEntryContract {
  val ui: State<ActivityUiState>
  fun selectType(t: ActivityType)
  fun setDate(dt: LocalDateTime)
  fun onDuration(v: String)
  fun onDistance(v: String)
  fun save()
}

data class ActivityUiState(
  val type: ActivityType = ActivityType.WALK,
  val duration: String   = "",
  val distance: String   = "",
  val dateTime: LocalDateTime = LocalDateTime.now()
)
