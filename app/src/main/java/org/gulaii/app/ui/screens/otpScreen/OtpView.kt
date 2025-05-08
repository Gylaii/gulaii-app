package org.gulaii.app.ui.screens.otpScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.gulaii.app.R
import org.gulaii.app.ui.composables.PillButton
import org.gulaii.app.ui.theme.GulaiiTheme

@Composable
fun OtpView(
  modifier: Modifier = Modifier,
  codeLength: Int = 4,
  viewModel: OtpViewModel = viewModel(),
  onVerifyClick: () -> Unit = {},
) {
  val uiState by viewModel.uiState.collectAsState()
  val focusManager = LocalFocusManager.current


  val isDark = isSystemInDarkTheme()
  val otpImage = if (isDark) {
    R.drawable.otp_light_image
  } else {
    R.drawable.otp_dark_image
  }



  Surface(
    modifier = modifier.fillMaxSize(),
    color = MaterialTheme.colorScheme.background
  ) {
    Column(
      modifier = Modifier
        .padding(30.dp)
        .fillMaxSize(),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Image(
        painter = painterResource(id = otpImage),
        contentDescription = "One time password image",
        contentScale = ContentScale.Crop,
        modifier = Modifier.padding(top = 50.dp)
      )

      Spacer(Modifier.height(100.dp))

      Text(
        text = "OTP verification",
        style = MaterialTheme.typography.bodyLarge.copy(
          fontSize = 28.sp,
        ),
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center
      )

      Text(
        text = "We Will send you a one time password on provided Email",
        style = MaterialTheme.typography.bodySmall.copy(
          fontSize = 22.sp,
          lineHeight = 30.sp,
        ),
        color = MaterialTheme.colorScheme.secondary,
        textAlign = TextAlign.Center,
        modifier = Modifier.padding(top = 20.dp),
      )

      BasicTextField(
        value = uiState.code,
        onValueChange = { newCode ->
          if (newCode.length <= 4) {
            viewModel.onCodeChange(newCode)

            if (newCode.length == 4) {
              focusManager.clearFocus()
            }
          }
        },
        modifier = Modifier.padding(16.dp),
        keyboardOptions = KeyboardOptions(
          keyboardType = KeyboardType.NumberPassword,
        ),
        decorationBox = { innerTextField ->
          Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
          ) {
            for (i in 0 until codeLength) {
            }
          }
        }
      )

      Spacer(Modifier.height(50.dp))
      PillButton(
        isEnabled = uiState.isClickable,
        buttonColor = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer),
        clickAction = onVerifyClick,
        modifier = Modifier
          .width(140.dp)
          .height(56.dp)
      ) {
        Text(
          text = "Verify",
          style = MaterialTheme.typography.bodyMedium,
          fontSize = 22.sp,
        )
      }
    }
  }
}


@Composable
@Preview
fun OtpViewPreview() {
  GulaiiTheme {
    OtpView()
  }
}
