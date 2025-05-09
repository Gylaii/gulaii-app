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
    ExposedDropdownMenuBox(expanded, { expanded = !expanded }) {
      TextField(
        value = goal,
        onValueChange = {},
        readOnly = true,
        label = { Text("Goal") },
        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
        modifier = Modifier.menuAnchor()
      )
      ExposedDropdownMenu(expanded, { expanded = false }) {
        goals.forEach {
          DropdownMenuItem(
            text = { Text(it) },
            onClick = { vm.setGoal(it); expanded = false }
          )
        }
      }
    }
    Spacer(Modifier.height(32.dp))
    PillButton(
      isEnabled = goal.isNotBlank(),
      buttonColor = ButtonDefaults.buttonColors(),
      modifier = Modifier.fillMaxWidth(),
      clickAction = onNext
    ) { Text("Next") }
  }
}

