package org.gulaii.app.ui.screens.food

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.gulaii.app.ui.composables.DateTimeSection
import org.gulaii.app.ui.composables.DishListSection
import org.gulaii.app.ui.composables.PillButton

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FoodEntryScreen(
  onBack: () -> Unit,
  onSaved: () -> Unit,
  vm: FoodEntryContract = androidx.lifecycle.viewmodel.compose.viewModel<AddFoodEntryViewModel>()
) {
  val uiState by vm.ui
  val scroll  = rememberScrollState()

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text(if (vm is AddFoodEntryViewModel) "Новая запись" else "Редактировать запись") },
        navigationIcon = {
          IconButton(onClick = onBack) {
            Icon(Icons.Filled.ArrowBack, contentDescription = "back")
          }
        }
      )
    }
  ) { pad ->
    Column(
      Modifier
        .padding(pad)
        .padding(24.dp)
        .fillMaxSize()
        .verticalScroll(scroll),
      verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
      Text("Приём пищи", style = MaterialTheme.typography.titleMedium)
      FlowRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        MealType.entries.forEach { t ->
          FilterChip(
            selected = uiState.currentMeal == t,
            onClick  = { vm.selectMeal(t) },
            label    = { Text(t.ru) }
          )
        }
      }

      DateTimeSection(uiState.dateTime, vm::setDate)

      Text("Блюда", style = MaterialTheme.typography.titleMedium)
      DishListSection(vm)

      PillButton(
        modifier    = Modifier.fillMaxWidth().height(56.dp),
        isEnabled   = uiState.editDishes.any { it.isSaved },
        buttonColor = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primary),
        clickAction = {
          vm.saveAll()
          onSaved()
        }
      ) { Text("Сохранить", color = MaterialTheme.colorScheme.onPrimary) }

      if (vm.templates.isNotEmpty()) {
        Text("Меню готовых блюд", style = MaterialTheme.typography.titleMedium)
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          vm.templates.forEach { tpl ->
            ElevatedCard(
              Modifier.fillMaxWidth().clickable { vm.applyTemplate(tpl) }
            ) {
              Row(
                Modifier.padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
              ) {
                Column(Modifier.weight(1f)) {
                  Text(tpl.name, style = MaterialTheme.typography.bodyLarge)
                  Text("${tpl.grams} г", style = MaterialTheme.typography.bodySmall)
                }
                Text("${tpl.kcal} ккал", style = MaterialTheme.typography.bodyLarge)
              }
            }
          }
        }
      }

      uiState.savedMeals[uiState.currentMeal]?.let { list ->
        if (list.isNotEmpty()) {
          Text("Сохранённые (${uiState.currentMeal.ru})", style = MaterialTheme.typography.titleMedium)
          Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
            list.forEach { d ->
              ElevatedCard(Modifier.fillMaxWidth()) {
                Row(
                  Modifier.padding(16.dp),
                  verticalAlignment = Alignment.CenterVertically
                ) {
                  Column(Modifier.weight(1f)) {
                    Text(d.name)
                    Text("${d.kcal} ккал")
                  }
                  Text("${d.grams} г")
                }
              }
            }
          }
        }
      }
    }
  }
}
