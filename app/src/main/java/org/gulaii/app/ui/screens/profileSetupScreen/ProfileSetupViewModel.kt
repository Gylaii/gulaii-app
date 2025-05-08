package org.gulaii.app.ui.screens.profileSetupScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.gulaii.app.data.model.ProfileMetrics
import org.gulaii.app.data.repository.AuthRepository
import org.gulaii.app.data.repository.UserRepository

class ProfileSetupViewModel(
  private val authRepo: AuthRepository,
  private val userRepo: UserRepository
) : ViewModel() {

  private val _uiState = MutableStateFlow(ProfileSetupUiState())
  val uiState: StateFlow<ProfileSetupUiState> = _uiState

  fun onHeightChange(h: String) = _uiState.update { it.copy(height = h) }
  fun onWeightChange(w: String) = _uiState.update { it.copy(weight = w) }
  fun onGoalChange(g: String)   = _uiState.update { it.copy(goal = g) }
  fun onActivityChange(a: String) = _uiState.update { it.copy(activity = a) }

  fun submit(onSuccess: () -> Unit) = viewModelScope.launch {
    if (!_uiState.value.canSubmit) return@launch
    _uiState.update { it.copy(isSubmitting = true) }

    val metrics = ProfileMetrics(
      height = _uiState.value.height.toIntOrNull(),
      weight = _uiState.value.weight.toIntOrNull(),
      goal = _uiState.value.goal.ifBlank { null },
      activityLevel = _uiState.value.activity.ifBlank { null }
    )
    userRepo.setMetrics(metrics)
    onSuccess()
  }
}
