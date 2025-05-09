package org.gulaii.app.ui.screens.authScreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.gulaii.app.di.ServiceLocator
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

enum class AuthMode { SignIn, SignUp }

data class AuthUiState(
  val email: String = "",
  val password: String = "",
  val mode: AuthMode = AuthMode.SignIn,
  val isLoading: Boolean = false,
  val error: String? = null
) {
  val primaryButtonText get() = if (mode == AuthMode.SignIn) "Войти" else "Регистрация"
}

sealed interface AuthEvent {
  data class Success(val mode: AuthMode) : AuthEvent
  data class Error  (val message: String) : AuthEvent
}

class AuthScreenViewModel : ViewModel() {

  private val repo = ServiceLocator.authRepo()

  private val _ui = MutableStateFlow(AuthUiState())
  val ui: StateFlow<AuthUiState> = _ui

  private val _events = Channel<AuthEvent>(Channel.BUFFERED)
  val events = _events.receiveAsFlow()

  fun onEmailChange(e: String)    = _ui.update { it.copy(email = e) }
  fun onPasswordChange(p: String) = _ui.update { it.copy(password = p) }
  fun toggleMode()                = _ui.update {
    it.copy(mode = if (it.mode == AuthMode.SignIn) AuthMode.SignUp else AuthMode.SignIn)
  }

  fun onPrimary(onResult: (needWizard: Boolean) -> Unit) = viewModelScope.launch {
    _ui.update { it.copy(isLoading = true, error = null) }

    runCatching {
      if (_ui.value.mode == AuthMode.SignIn)
        repo.login(_ui.value.email, _ui.value.password)
      else
        repo.register(_ui.value.email, _ui.value.password, "Alice")
    }.onSuccess {
      val needWizard = (_ui.value.mode == AuthMode.SignUp)
      onResult(needWizard)
    }.onFailure { e ->
      Log.e("AuthVM", "Auth failed", e)
      _ui.update { it.copy(isLoading = false) }
      _events.send(AuthEvent.Error(e.message ?: "Unknown error"))
    }
  }
}
