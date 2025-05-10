package org.gulaii.app.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.time.LocalDateTime

enum class ActivityType(val ru: String) { RUN("Бег"), WALK("Ходьба"), BIKE("Велосипед") }

data class ActivityEntry(
  val type: ActivityType,
  val durationMin: Int,
  val distanceKm: Double,
  val dateTime: LocalDateTime
) {
  val id get() = "${type}_${dateTime.toLocalDate()}"
}

class ActivityRepository {
  private val _entries = MutableStateFlow(emptyList<ActivityEntry>())
  val entries: StateFlow<List<ActivityEntry>> = _entries.asStateFlow()

  fun upsert(e: ActivityEntry) {
    _entries.value = _entries.value.filterNot { it.id == e.id } + e
  }

  fun find(id: String) = _entries.value.firstOrNull { it.id == id }
}
