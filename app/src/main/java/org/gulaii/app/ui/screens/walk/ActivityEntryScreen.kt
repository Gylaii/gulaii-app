package org.gulaii.app.ui.screens.walk

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.gulaii.app.data.repository.ActivityType
import org.gulaii.app.ui.composables.CustomTextField
import org.gulaii.app.ui.composables.PillButton
import org.gulaii.app.ui.screens.food.DateTimeSection

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun ActivityEntryScreen(
  nav: NavHostController,
  vm: ActivityEntryContract =
    androidx.lifecycle.viewmodel.compose.viewModel<AddActivityEntryViewModel>(),
  onSaved: () -> Unit = {}
) {
  val ui by vm.ui
  val scroll = rememberScrollState()
  val number = Regex("""[0-9.,]*""")

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Новая активность") },
        navigationIcon = {
          IconButton({ nav.popBackStack() }) {
            Icon(Icons.Default.ArrowBack, "back")
          }
        }
      )
    }
  ) { pad ->
    Column(
      Modifier.padding(pad).padding(24.dp).verticalScroll(scroll),
      verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
      Text("Тип", style = MaterialTheme.typography.titleMedium)
      FlowRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        ActivityType.entries.forEach { t ->
          FilterChip(
            selected = ui.type == t,
            onClick = { vm.selectType(t) },
            label   = { Text(t.ru) }
          )
        }
      }

      DateTimeSection(ui.dateTime, vm::setDate)

      Text("Детали", style = MaterialTheme.typography.titleMedium)
      CustomTextField(
        label = "Длительность (мин)",
        value = ui.duration,
        onValueChange = { if (number.matches(it)) vm.onDuration(it) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
      )
      CustomTextField(
        label = "Расстояние (км)",
        value = ui.distance,
        onValueChange = { if (number.matches(it)) vm.onDistance(it) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
      )

      PillButton(
        modifier    = Modifier.fillMaxWidth().height(56.dp),
        isEnabled   = ui.duration.isNotBlank() && ui.distance.isNotBlank(),
        buttonColor = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
        clickAction = { vm.save(); onSaved() }
      ) { Text("Сохранить", color = MaterialTheme.colorScheme.onPrimary) }
    }
  }
}

