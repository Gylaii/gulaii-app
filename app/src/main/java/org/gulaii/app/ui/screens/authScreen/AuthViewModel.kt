import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

enum class AuthMode { SignIn, SignUp }

data class AuthUiState(
  val email: String = "",
  val password: String = "",
  val mode: AuthMode = AuthMode.SignIn,
) {
  val primaryButtonText: String
    get() = if (mode == AuthMode.SignIn) "Login" else "Register"
}

class AuthScreenViewModel : ViewModel() {

  private val _uiState = MutableStateFlow(AuthUiState())
  val uiState: StateFlow<AuthUiState> = _uiState.asStateFlow()


  fun onEmailChange(newEmail: String) =
    _uiState.update { it.copy(email = newEmail) }

  fun onPasswordChange(newPass: String) =
    _uiState.update { it.copy(password = newPass) }

  fun toggleMode() = _uiState.update {
    it.copy(mode = if (it.mode == AuthMode.SignIn) AuthMode.SignUp else AuthMode.SignIn)
  }


  fun onPrimaryButtonClick() = viewModelScope.launch {
    when (_uiState.value.mode) {
      AuthMode.SignIn -> login()
      AuthMode.SignUp -> register()
    }
  }

  private suspend fun login() {
    // TODO: ...
  }

  private suspend fun register() {
    // TODO: ...
  }
}
