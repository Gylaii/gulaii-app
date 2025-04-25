import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

data class AuthUiState(
  val email: String = "",
  val password: String = "",
)

class AuthScreenViewModel : ViewModel() {

  val headerText = "Welcome \nto"
  val logoText   = "Gulaii"

  private val _uiState = MutableStateFlow(AuthUiState())
  val uiState = _uiState.asStateFlow()

  fun onEmailChange(newEmail: String) =
    _uiState.update { it.copy(email = newEmail) }

  fun onPasswordChange(newPass: String) =
    _uiState.update { it.copy(password = newPass) }

}
