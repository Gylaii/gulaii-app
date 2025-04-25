package org.gulaii.app.ui.screens.authScreen

import AuthScreenViewModel
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.gulaii.app.ui.composables.CustomTextField
import org.gulaii.app.ui.composables.PillButton
import org.gulaii.app.ui.theme.GulaiiTheme
import org.gulaii.app.ui.theme.PurpleGrey40
import org.gulaii.app.ui.theme.PurpleGrey80
import org.gulaii.app.ui.theme.SecondaryBlue
import org.gulaii.app.ui.theme.SecondaryOrange
import org.gulaii.app.ui.theme.SoftBlue

@Composable
fun AuthScreenView(
  modifier: Modifier = Modifier,
  viewModel: AuthScreenViewModel = viewModel(),
  onForgotPasswordClick: () -> Unit = {},
  onHaveAnAccountLinkCLick: () -> Unit = {},
  onSubmit: () -> Unit = {},
) {
  val uiState by viewModel.uiState.collectAsState()


  Surface(
    modifier = modifier.fillMaxSize(),
    color = SecondaryOrange
  ) {

    Column(
      modifier = modifier.padding(24.dp),
      horizontalAlignment = Alignment.CenterHorizontally,
    ) {
      Text(
        text = viewModel.headerText,
        style = MaterialTheme.typography.bodySmall,
        fontSize = 34.sp,
        color = PurpleGrey40,
        modifier = Modifier.padding(top = 50.dp),
        textAlign = TextAlign.Center,
        lineHeight = 34.sp
      )
      Text(
        text = viewModel.logoText,
        style = MaterialTheme.typography.bodyLarge,
        fontSize = 48.sp,
        color = SecondaryBlue,
        modifier = Modifier.padding(top = 20.dp),
        textAlign = TextAlign.Center,
      )

      CustomTextField(
        label = "Email",
        value = uiState.email,
        onValueChange = viewModel::onEmailChange,
        modifier = Modifier.padding(top = 50.dp),
        leadingIcon = null,
        onTrailingIconClick = { },
        trailingIcon = null,
        cornerRadius = 12.dp,
      )

      Spacer(Modifier.height(20.dp))

      CustomTextField(
        label = "Password",
        value = uiState.password,
        onValueChange = viewModel::onPasswordChange,
        modifier = Modifier,
        leadingIcon = null,
        onTrailingIconClick = { },
        trailingIcon = null,
        cornerRadius = 12.dp
      )

      Spacer(Modifier.height(25.dp))

      Text(
        text = "Forgot your password?",
        style = MaterialTheme.typography.bodySmall,
        textDecoration = TextDecoration.Underline,
        fontSize = 16.sp,
        modifier = Modifier.clickable(role = Role.Button) {
          onForgotPasswordClick()
        },
      )

      Spacer(Modifier.height(170.dp))

      PillButton(
        modifier = Modifier
          .width(200.dp)
          .height(50.dp),
        isEnabled = true,
        buttonColor = ButtonDefaults.buttonColors(containerColor = SoftBlue),
        clickAction = {}
      ) {
        Text(
          text = "Login",
          modifier = Modifier,
          style = MaterialTheme.typography.bodyLarge,
          fontSize = 24.sp,
          color = Color.Black
        )
      }

      Spacer(Modifier.height(10.dp))

      Text(
        text = "or",
        modifier = Modifier,
        style = MaterialTheme.typography.bodySmall,
        fontSize = 20.sp,
        color = Color.Black
      )

      Spacer(Modifier.height(10.dp))

      PillButton(
        modifier = Modifier
          .width(200.dp)
          .height(50.dp),
        isEnabled = true,
        buttonColor = ButtonDefaults.buttonColors(containerColor = PurpleGrey80),
        clickAction = onSubmit
      ) {
        Text(
          text = "Google",
          modifier = Modifier,
          style = MaterialTheme.typography.bodyLarge,
          fontSize = 24.sp,
          color = Color.Black
        )
      }

      Spacer(Modifier.height(25.dp))

      Text(
        text = "Already have an account?",
        style = MaterialTheme.typography.bodySmall,
        textDecoration = TextDecoration.Underline,
        fontSize = 16.sp,
        modifier = Modifier.clickable(role = Role.Button) {
          onHaveAnAccountLinkCLick()
        },
      )

    }
  }
}

@Preview
@Composable
fun AuthScreenViewPreview() {
  GulaiiTheme {
    AuthScreenView()
  }
}
