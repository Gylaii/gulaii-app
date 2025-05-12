package org.gulaii.app.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.gulaii.app.ui.screens.profile.ProfileUiState
import org.gulaii.app.ui.screens.profile.ProfileViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GoalField(ui: ProfileUiState, vm: ProfileViewModel) {
  if (ui.isEditing) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
      expanded = expanded,
      onExpandedChange = { expanded = !expanded }
    ) {
      OutlinedTextField(
        value = ui.goal,
        onValueChange = {},
        readOnly = true,
        label = { Text("Цель") },
        placeholder = { Text("—") },
        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
        modifier = Modifier
          .fillMaxWidth()
          .menuAnchor()
      )
      ExposedDropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
      ) {
        listOf("Похудение", "Поддержание", "Набор").forEach { g ->
          DropdownMenuItem(
            text = { Text(g) },
            onClick = {
              vm.onGoal(g)
              expanded = false
            }
          )
        }
      }
    }
  } else {
    InfoCard("Цель", ui.goal)
  }
}
