package org.gulaii.app.ui.screens.authScreen

import android.util.Log
import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.gulaii.app.di.ServiceLocator

enum class AuthMode { SignIn, SignUp }

data class AuthUiState(
  val email: String = "",
  val password: String = "",
  val mode: AuthMode = AuthMode.SignIn,
  val isLoading: Boolean = false,
  val emailError: String? = null,
  val passwordError: String? = null
) {
  val primaryButtonText get() = if (mode == AuthMode.SignIn) "Войти" else "Регистрация"
}

sealed interface AuthEvent {
  data class Success(val mode: AuthMode, val needWizard: Boolean) : AuthEvent
  data class Error(val message: String) : AuthEvent
}

class AuthScreenViewModel : ViewModel() {

  private val repo = ServiceLocator.authRepo()

  private val _ui = MutableStateFlow(AuthUiState())
  val ui: StateFlow<AuthUiState> = _ui

  private val _events = Channel<AuthEvent>(Channel.BUFFERED)
  val events = _events.receiveAsFlow()

  fun onEmailChange(e: String) {
    _ui.update {
      it.copy(email = e)
    }
    validate()
  }

  fun onPasswordChange(p: String) {
    _ui.update {
      it.copy(password = p)
    }
    validate()
  }

  private fun validate(): Boolean {
    val email = _ui.value.email
    val password = _ui.value.password

    val emailErr = when {
      email.isBlank() -> "E‑mail не может быть пустым"
      !Patterns.EMAIL_ADDRESS.matcher(email).matches() -> "Неверный формат e‑mail"
      else -> null
    }

    val passErr = when {
      password.isBlank() -> "Пароль не может быть пустым"
      password.length < 6 -> "Не меньше 6 символов"
      else -> null
    }

    _ui.update {
      it.copy(
        emailError = emailErr,
        passwordError = passErr
      )
    }

    return emailErr == null && passErr == null
  }


  fun toggleMode() = _ui.update {
    it.copy(mode = if (it.mode == AuthMode.SignIn) AuthMode.SignUp else AuthMode.SignIn)
  }

  fun onPrimary() = viewModelScope.launch {
    _ui.update { it.copy(isLoading = true) }

    runCatching {
      if (_ui.value.mode == AuthMode.SignIn)
        repo.login(_ui.value.email, _ui.value.password)
      else
        repo.register(_ui.value.email, _ui.value.password, "Alice")
    }.onSuccess {
      val needWizard = _ui.value.mode == AuthMode.SignUp
      _events.send(AuthEvent.Success(_ui.value.mode, needWizard))
    }.onFailure { e ->
      Log.e("AuthVM", "Auth failed", e)
      _ui.update { it.copy(isLoading = false) }
      _events.send(AuthEvent.Error(e.message ?: "Неизвестная ошибка"))
    }
  }
}
