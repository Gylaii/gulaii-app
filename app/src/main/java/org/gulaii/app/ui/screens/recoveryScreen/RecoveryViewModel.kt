package org.gulaii.app.ui.screens.recoveryScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class RecoveryUiState(
  val email: String = "",
) {
  fun isClickable(email: String): Boolean {
    return email.trim() != ""
  }
}

class RecoveryViewModel : ViewModel() {

  private val _uiState = MutableStateFlow(RecoveryUiState())
  val uiState: StateFlow<RecoveryUiState> = _uiState

  fun onEmailChange(newEmail: String) =
    _uiState.update { it.copy(email = newEmail) }

}
