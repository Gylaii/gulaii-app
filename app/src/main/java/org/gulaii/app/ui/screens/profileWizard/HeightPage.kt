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
    Spacer(Modifier.height(24.dp))

    CustomTextField(
      label = "Рост (см)",
      value = value,
      onValueChange = vm::setHeight,
      keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
      modifier = Modifier
        .fillMaxWidth()
        .height(72.dp)
    )

    Spacer(Modifier.height(40.dp))

    PillButton(
      modifier = Modifier
        .fillMaxWidth()
        .height(56.dp),
      isEnabled = value.toIntOrNull() != null,
      buttonColor = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary
      ),
      clickAction = onNext
    ) {
      Text(
        text = "Далее",
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.onPrimary
      )
    }
  }
}

