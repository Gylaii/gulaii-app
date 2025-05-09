package org.gulaii.app.ui.screens.profileWizard

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.gulaii.app.ui.composables.PillButton

private val activities = listOf("низкий", "средний", "высокий")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityPage(
  vm: WizardVM = viewModel(),
  onNext: () -> Unit
) {
  val act by vm.activity.collectAsState()
  var expanded by remember { mutableStateOf(false) }

  WizardScaffold("Уровень активности") {
    Spacer(Modifier.height(24.dp))

    ExposedDropdownMenuBox(
      expanded = expanded,
      onExpandedChange = { expanded = !expanded },
      modifier = Modifier
        .fillMaxWidth()
        .height(72.dp)
    ) {
      TextField(
        value = act,
        onValueChange = {},
        readOnly = true,
        label = { Text("Выберите активность") },
        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
        modifier = Modifier
          .fillMaxWidth()
          .menuAnchor()
      )
      ExposedDropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
      ) {
        activities.forEach { option ->
          DropdownMenuItem(
            text = { Text(option) },
            onClick = {
              vm.setActivity(option)
              expanded = false
            }
          )
        }
      }
    }

    Spacer(Modifier.height(40.dp))

    PillButton(
      modifier = Modifier
        .fillMaxWidth()
        .height(56.dp),
      isEnabled = act.isNotBlank(),
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
