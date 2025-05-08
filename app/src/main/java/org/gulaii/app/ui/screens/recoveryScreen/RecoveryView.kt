package org.gulaii.app.ui.screens.recoveryScreen

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import org.gulaii.app.R
import org.gulaii.app.ui.composables.CustomTextField
import org.gulaii.app.ui.composables.PillButton
import org.gulaii.app.ui.theme.GulaiiTheme

@Composable
fun RecoveryView(
  modifier: Modifier = Modifier,
  viewModel: RecoveryViewModel = viewModel(),
  onReturnClick: () -> Unit = {},
  onNextClick: () -> Unit = {},
) {
  val uiState by viewModel.uiState.collectAsState()
  val backScale  = remember { Animatable(1f) }
  val nextScale  = remember { Animatable(1f) }
  val scope = rememberCoroutineScope()

  Surface(
    modifier = modifier.fillMaxSize(),
    color = MaterialTheme.colorScheme.background,
  ) {
    Column(
      modifier = Modifier
        .padding(30.dp)
        .fillMaxSize(),
      verticalArrangement = Arrangement.Top,
      horizontalAlignment = Alignment.Start
    ) {
      PillButton(
        modifier = Modifier
          .padding(bottom = 20.dp)
          .height(40.dp)
          .width(60.dp)
          .scale(backScale.value),
        isEnabled = true,
        buttonColor = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.secondaryContainer),
        clickAction = {
          scope.launch {
            backScale.animateTo(
              targetValue = 1.3f,
              animationSpec = tween(70)
            )
            backScale.animateTo(
              targetValue = 1f,
              animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy
              )
            )
            onReturnClick()
          }
        }
      ) {
        Icon(
          painter = painterResource(R.drawable.arrow_back_ios),
          contentDescription = stringResource(id = R.string.arrow_forward_description),
          modifier = Modifier.scale(2.5F),
          tint = MaterialTheme.colorScheme.onSecondaryContainer
        )
      }

      Text(
        text = "Forgot password?",
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.headlineMedium,
        fontSize = 36.sp,
        color = MaterialTheme.colorScheme.primary,
        modifier = Modifier.padding(bottom = 8.dp)
      )

      Text(
        text = "Enter your email address below and we’ll send you a link to reset your password.",
        textAlign = TextAlign.Start,
        style = MaterialTheme.typography.bodyLarge,
        fontSize = 18.sp,
        color = MaterialTheme.colorScheme.secondary,
        modifier = Modifier.padding(bottom = 32.dp)
      )

      Spacer(Modifier.height(400.dp))

      CustomTextField(
        label = "Recovery Email",
        value = uiState.email,
        onValueChange = viewModel::onEmailChange,
        modifier = Modifier
          .fillMaxWidth()
          .padding(bottom = 32.dp),
        leadingIcon = painterResource(R.drawable.outline_email_24),
        trailingIcon = null,
        cornerRadius = 12.dp,
      )

      PillButton(
        modifier = Modifier
          .width(200.dp)
          .height(50.dp)
          .align(Alignment.CenterHorizontally)
          .scale(nextScale.value),
        isEnabled = uiState.isClickable(uiState.email),
        buttonColor = ButtonDefaults.buttonColors(),
        clickAction = {
          scope.launch {
            nextScale.animateTo(
              targetValue = 1.3f,
              animationSpec = tween(100)
            )
            nextScale.animateTo(
              targetValue = 1f,
              animationSpec = spring(
                dampingRatio = Spring.DampingRatioLowBouncy
              )
            )
            onNextClick()
          }
        }
      ) {
        Text(
          text = "Next",
          color = MaterialTheme.colorScheme.onPrimary,
          style = MaterialTheme.typography.bodyLarge,
          fontSize = 20.sp
        )
      }
    }
  }
}


@Preview
@Composable
fun RecoveryViewPreview() {
  GulaiiTheme {
    RecoveryView()
  }
}
