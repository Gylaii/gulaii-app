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
  val primaryButtonText get() = if (mode == AuthMode.SignIn) "Login" else "Register"
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

  fun onPrimary(onSuccess: () -> Unit) = viewModelScope.launch {
    _ui.update { it.copy(isLoading = true, error = null) }
    runCatching {
      if (_ui.value.mode == AuthMode.SignIn)
        repo.login(_ui.value.email, _ui.value.password)
      else
        repo.register(_ui.value.email, _ui.value.password, "Alice") // имя можно спросить доп‑полем
    }.onSuccess {
      _events.send(AuthEvent.Success(_ui.value.mode))   // сообщаем в UI
      onSuccess()
    }.onFailure { e ->
      Log.e("AuthVM", "Registration failed", e)
      _events.send(AuthEvent.Error(e.message ?: "Неизвестная ошибка"))
      _ui.update { it.copy(isLoading = false) }
    }
  }
}
