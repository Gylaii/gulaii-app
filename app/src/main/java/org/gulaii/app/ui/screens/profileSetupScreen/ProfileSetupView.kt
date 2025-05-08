package org.gulaii.app.ui.screens.profileSetupScreen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.gulaii.app.ui.composables.CustomTextField
import org.gulaii.app.ui.composables.PillButton
import org.gulaii.app.ui.theme.GulaiiTheme

@Composable
fun ProfileSetupView(
  modifier: Modifier = Modifier,
  vm: ProfileSetupViewModel = viewModel(),
  onFinished: () -> Unit = {}
) {
  val state by vm.uiState.collectAsState()

  Surface(modifier.fillMaxSize()) {
    Column(
      modifier.padding(24.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text("Tell us about yourself", style = MaterialTheme.typography.headlineMedium)

      Spacer(Modifier.height(32.dp))

      CustomTextField(
        label = "Height (cm)",
        value = state.height,
        onValueChange = vm::onHeightChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
      )
      Spacer(Modifier.height(16.dp))
      CustomTextField(
        label = "Weight (kg)",
        value = state.weight,
        onValueChange = vm::onWeightChange,
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
      )
      Spacer(Modifier.height(16.dp))
      CustomTextField(
        label = "Goal (e.g. похудение)",
        value = state.goal,
        onValueChange = vm::onGoalChange,
      )
      Spacer(Modifier.height(16.dp))
      CustomTextField(
        label = "Activity level (низкий/средний/высокий)",
        value = state.activity,
        onValueChange = vm::onActivityChange,
      )

      Spacer(Modifier.height(40.dp))

      PillButton(
        isEnabled = state.canSubmit && !state.isSubmitting,
        buttonColor = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer),
        modifier = Modifier
          .width(220.dp)
          .height(56.dp),
        clickAction = { vm.submit(onFinished) }
      ) {
        if (state.isSubmitting) {
          CircularProgressIndicator(strokeWidth = 2.dp)
        } else {
          Text("Save and continue")
        }
      }
    }
  }
}

@Preview
@Composable
fun ProfileSetupPreview() {
  GulaiiTheme { ProfileSetupView() }
}
