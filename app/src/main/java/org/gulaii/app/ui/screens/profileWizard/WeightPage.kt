package org.gulaii.app.ui.screens.profileWizard

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.gulaii.app.ui.composables.CustomTextField
import org.gulaii.app.ui.composables.PillButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WeightPage(
  vm: WizardVM = viewModel(),
  onNext: () -> Unit
) {
  val weight by vm.weight.collectAsState()

  WizardScaffold("Введите вес") {
    CustomTextField(
      label = "Weight (kg)",
      value = weight,
      onValueChange = vm::setWeight,
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    Spacer(Modifier.height(32.dp))
    PillButton(
      isEnabled = weight.toIntOrNull() != null,
      buttonColor = ButtonDefaults.buttonColors(),
      modifier = Modifier.fillMaxWidth(),
      clickAction = onNext
    ) {
      Text("Next")
    }
  }
}
