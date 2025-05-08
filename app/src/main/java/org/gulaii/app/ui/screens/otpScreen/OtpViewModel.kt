package org.gulaii.app.ui.screens.otpScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

data class OtpUiState(
    var code: String = "",
    var isClickable: Boolean = false,
)

class OtpViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(OtpUiState())
    val uiState: StateFlow<OtpUiState> = _uiState

  fun checkIfOtpComplete(otpValues: List<String>) {
    val isComplete = otpValues.all { it.length == 1 }
    _uiState.update { it.copy(isClickable = isComplete) }
  }
}
