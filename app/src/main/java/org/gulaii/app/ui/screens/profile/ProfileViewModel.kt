package org.gulaii.app.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.gulaii.app.data.model.ProfileMetrics
import org.gulaii.app.di.ServiceLocator

class ProfileViewModel : ViewModel() {

  private val authRepo = ServiceLocator.authRepo()
  private val userRepo = ServiceLocator.userRepo()

  private val _ui = MutableStateFlow(ProfileUiState())
  val ui: StateFlow<ProfileUiState> = _ui

  init {
    viewModelScope.launch {
      val token = authRepo.currentToken()
      if (token.isNullOrBlank()) return@launch

      try {
        val m = userRepo.getMetrics()
        _ui.update {
          it.copy(
            height    = m.height?.toString().orEmpty(),
            weight    = m.weight?.toString().orEmpty(),
            goal      = m.goal.orEmpty(),
            activity  = m.activityLevel.orEmpty()
          )
        }
      } catch (e: Exception) {
        _ui.update { it.copy(error = e.message ?: "Network error") }
      }
    }
  }

  fun onHeight(h: String)    = _ui.update { it.copy(height = h) }
  fun onWeight(w: String)    = _ui.update { it.copy(weight = w) }
  fun onGoal(g: String)      = _ui.update { it.copy(goal = g) }
  fun onActivity(a: String)  = _ui.update { it.copy(activity = a) }

  fun toggleEditing() = _ui.update { it.copy(isEditing = !it.isEditing) }

  fun logout(onDone: () -> Unit) = viewModelScope.launch {
    authRepo.clearToken()
    onDone()
  }

  fun save(onSaved: () -> Unit) = viewModelScope.launch {
    _ui.update { it.copy(isLoading = true, error = null) }

    try {
      userRepo.setMetrics( ProfileMetrics(
        height        = _ui.value.height.toIntOrNull(),
        weight        = _ui.value.weight.toIntOrNull(),
        goal          = _ui.value.goal,
        activityLevel = _ui.value.activity
      ))
      _ui.update { it.copy(isLoading = false,
        saved = true,
        isEditing = false) }      // ← выключаем режим правки
      onSaved()
    } catch (e: Exception) {
      _ui.update { it.copy(isLoading = false,
        error = e.message ?: "Network error") }
    }
  }
}
