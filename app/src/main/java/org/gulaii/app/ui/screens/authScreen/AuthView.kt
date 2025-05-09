package org.gulaii.app.ui.screens.authScreen

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.collectLatest
import org.gulaii.app.R
import org.gulaii.app.ui.composables.CustomTextField
import org.gulaii.app.ui.composables.PillButton
import org.gulaii.app.ui.theme.GulaiiTheme

@Composable
fun AuthScreenView(
  modifier: Modifier = Modifier,
  viewModel: AuthScreenViewModel = viewModel(),
  onForgotPasswordClick: () -> Unit = {},
  onAuthResult: (needWizard: Boolean) -> Unit = {},
) {
  val uiState by viewModel.ui.collectAsState()
  var passwordVisible by rememberSaveable { mutableStateOf(false) }
  val snackbarHostState = remember { SnackbarHostState() }

  LaunchedEffect(Unit) {
    viewModel.events.collectLatest { ev: AuthEvent ->
      when (ev) {
        is AuthEvent.Success -> {
          val msg = if (ev.mode == AuthMode.SignUp)
            "Аккаунт создан"
          else
            "Вход выполнен"
          snackbarHostState.showSnackbar(msg)
        }
        is AuthEvent.Error -> {
          val msg = ev.message.ifBlank { "Не удалось создать аккаунт" }
          snackbarHostState.showSnackbar(msg)
        }
      }
    }
  }

  Scaffold(
    snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
  ) { innerPadding ->
    Column(
      modifier = modifier
        .padding(innerPadding)
        .padding(24.dp)
        .fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {

      AnimatedContent(
        targetState = uiState.mode,
        transitionSpec = { fadeIn() togetherWith fadeOut() }
      ) { mode ->
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .padding(top = 60.dp),
          horizontalAlignment = Alignment.CenterHorizontally
        ) {
          if (mode == AuthMode.SignIn) {
            Text(
              text = "Добро пожаловать",
              style = MaterialTheme.typography.headlineSmall,
              fontSize = 32.sp,
              color = MaterialTheme.colorScheme.onBackground,
              textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(8.dp))
            Text(
              text = "в Gulaii",
              style = MaterialTheme.typography.headlineSmall,
              fontSize = 32.sp,
              color = MaterialTheme.colorScheme.primary,
              textAlign = TextAlign.Center
            )
          } else {
            Text(
              text = "Создайте",
              style = MaterialTheme.typography.headlineSmall,
              fontSize = 32.sp,
              color = MaterialTheme.colorScheme.onBackground,
              textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(8.dp))
            Text(
              text = "аккаунт",
              style = MaterialTheme.typography.headlineSmall,
              fontSize = 32.sp,
              color = MaterialTheme.colorScheme.primary,
              textAlign = TextAlign.Center
            )
          }
        }
      }

      Spacer(Modifier.height(50.dp))

      CustomTextField(
        label = "Электронная почта",
        value = uiState.email,
        onValueChange = viewModel::onEmailChange,
        leadingIcon = painterResource(R.drawable.outline_email_24)
      )

      Spacer(Modifier.height(20.dp))

      CustomTextField(
        label = "Пароль",
        value = uiState.password,
        onValueChange = viewModel::onPasswordChange,
        leadingIcon = painterResource(R.drawable.outline_lock_24),
        onTrailingIconClick = { passwordVisible = !passwordVisible },
        trailingIcon = painterResource(
          if (passwordVisible)
            R.drawable.outline_remove_red_eye_24
          else
            R.drawable.outline_visibility_off_24
        ),
        visualTransformation = if (passwordVisible)
          VisualTransformation.None
        else
          PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Password)
      )

      Spacer(Modifier.height(25.dp))

      Text(
        text = "Забыли ваш пароль?",
        style = MaterialTheme.typography.bodySmall,
        textDecoration = TextDecoration.Underline,
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.secondary,
        modifier = Modifier.clickable(role = Role.Button) {
          onForgotPasswordClick()
        }
      )

      Spacer(Modifier.height(170.dp))

      PillButton(
        modifier = Modifier
          .width(200.dp)
          .height(50.dp),
        isEnabled = !uiState.isLoading,
        buttonColor = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer),
        clickAction = {
          viewModel.onPrimary { needWizard ->
            onAuthResult(needWizard)
          }
        }
      ) {
        if (uiState.isLoading) {
          CircularProgressIndicator(strokeWidth = 2.dp)
        } else {
          Text(
            text = uiState.primaryButtonText,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 24.sp
          )
        }
      }

      Spacer(Modifier.height(10.dp))

      Text("или", style = MaterialTheme.typography.bodySmall, fontSize = 20.sp)
      Spacer(Modifier.height(10.dp))
      PillButton(
        modifier = Modifier
          .width(200.dp)
          .height(50.dp),
        isEnabled = true,
        buttonColor = ButtonDefaults.buttonColors(),
        clickAction = { /* TODO: Google */ }
      ) {
        Text(
          text = "Google",
          color = MaterialTheme.colorScheme.onPrimary,
          style = MaterialTheme.typography.bodyLarge,
          fontSize = 24.sp
        )
      }

      Spacer(Modifier.height(50.dp))

      Text(
        text = if (uiState.mode == AuthMode.SignIn)
          "Нужен новый аккаунт?" else "Уже имеете аккаунт?",
        style = MaterialTheme.typography.bodySmall,
        textDecoration = TextDecoration.Underline,
        fontSize = 16.sp,
        color = MaterialTheme.colorScheme.secondary,
        modifier = Modifier.clickable { viewModel.toggleMode() }
      )
    }
  }
}

@Preview
@Composable
fun AuthScreenViewPreview() {
  GulaiiTheme { AuthScreenView() }
}
