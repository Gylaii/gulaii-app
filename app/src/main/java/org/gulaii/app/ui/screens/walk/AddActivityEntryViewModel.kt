package org.gulaii.app.ui.screens.walk

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import org.gulaii.app.data.repository.ActivityEntry
import org.gulaii.app.data.repository.ActivityRepository
import org.gulaii.app.data.repository.ActivityType
import org.gulaii.app.di.ServiceLocator
import java.time.LocalDateTime
import androidx.compose.runtime.State

class AddActivityEntryViewModel(
  private val repo: ActivityRepository = ServiceLocator.activityRepo()
) : ViewModel(), ActivityEntryContract {

  private val _ui = mutableStateOf(ActivityUiState())
  override val ui: State<ActivityUiState> get() = _ui

  override fun selectType(t: ActivityType)    = update { it.copy(type = t) }
  override fun setDate(dt: LocalDateTime)     = update { it.copy(dateTime = dt) }
  override fun onDuration(v: String)          = update { it.copy(duration = v) }
  override fun onDistance(v: String)          = update { it.copy(distance = v) }

  override fun save() = update { st ->
    repo.upsert(
      ActivityEntry(
        st.type,
        st.duration.toIntOrNull() ?: 0,
        st.distance.replace(',', '.').toDoubleOrNull() ?: .0,
        st.dateTime
      )
    )
    ActivityUiState()
  }

  private inline fun update(mut: (ActivityUiState) -> ActivityUiState) {
    _ui.value = mut(_ui.value)
  }
}

