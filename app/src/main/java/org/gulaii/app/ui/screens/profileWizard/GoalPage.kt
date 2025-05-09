package org.gulaii.app.ui.screens.profileWizard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.gulaii.app.ui.composables.PillButton

private val goals = listOf("Похудение", "Поддержание", "Набор")

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalPage(
  vm: WizardVM = viewModel(),
  onNext: () -> Unit
) {
  val goal by vm.goal.collectAsState()
  var expanded by remember { mutableStateOf(false) }

  WizardScaffold("Цель") {
    Spacer(Modifier.height(24.dp))

    ExposedDropdownMenuBox(
      expanded = expanded,
      onExpandedChange = { expanded = !expanded },
      modifier = Modifier
        .fillMaxWidth()
        .height(72.dp)
    ) {
      TextField(
        value = goal,
        onValueChange = {},
        readOnly = true,
        label = { Text("Выберите цель") },
        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
        modifier = Modifier
          .fillMaxWidth()
          .menuAnchor()
      )
      ExposedDropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
      ) {
        goals.forEach { option ->
          DropdownMenuItem(
            text = { Text(option) },
            onClick = {
              vm.setGoal(option)
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
      isEnabled = goal.isNotBlank(),
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
