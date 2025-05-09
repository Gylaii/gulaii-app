package org.gulaii.app.ui.screens.profileWizard

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.gulaii.app.ui.composables.CustomTextField
import org.gulaii.app.ui.composables.PillButton

@Composable
fun HeightPage(
  vm: WizardVM = viewModel(),
  onNext: () -> Unit
) {
  val value by vm.height.collectAsState()
  WizardScaffold("Введите рост") {
    CustomTextField(
      label = "Height (cm)",
      value = value,
      onValueChange = vm::setHeight,
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
    )
    Spacer(Modifier.height(32.dp))
    PillButton(
      isEnabled = value.toIntOrNull() != null,
      buttonColor = ButtonDefaults.buttonColors(),
      modifier = Modifier.fillMaxWidth(),
      clickAction = onNext
    ) { Text("Next") }
  }
}

