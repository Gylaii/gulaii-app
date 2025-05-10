package org.gulaii.app.ui.screens.food

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.gulaii.app.ui.composables.CustomTextField
import org.gulaii.app.ui.composables.PillButton

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun FoodEntryScreen(
  nav: NavHostController,
  onSaved: () -> Unit,
  vm: FoodEntryContract =
    androidx.lifecycle.viewmodel.compose.viewModel<AddFoodEntryViewModel>()   // по‑умолчанию “добавление”
) {
  val uiState by vm.ui
  val scroll  = rememberScrollState()

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text(if (vm is AddFoodEntryViewModel) "Новая запись" else "Редактировать запись") },
        navigationIcon = {
          IconButton({ nav.popBackStack() }) {
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
        clickAction = { vm.saveAll(); onSaved() }
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

private val numberRegex = Regex("""[0-9.,]*""")
@Composable
private fun DishListSection(vm: FoodEntryContract) {
  val uiState by vm.ui
  Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {

    uiState.editDishes.forEachIndexed { idx, dish ->
      DishCard(idx, dish, vm)
    }

    OutlinedButton(
      modifier = Modifier.fillMaxWidth().height(56.dp),
      onClick  = vm::addDish,
      enabled  = uiState.editDishes.any { it.isSaved }
    ) { Text("Добавить ещё блюдо") }
  }
}

@Composable
private fun DishCard(index: Int, dish: DishUi, vm: FoodEntryContract) {
  OutlinedCard {
    Column(
      Modifier.padding(12.dp),
      verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
      CustomTextField(
        label         = "Название",
        value         = dish.name,
        onValueChange = { vm.onDishChange(index) { name = it } },
        enabled       = !dish.isSaved
      )
      Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        CustomTextField(
          modifier      = Modifier.weight(1f),
          label = "грамм",
          value = dish.grams,
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
          onValueChange = { new ->
            if (numberRegex.matches(new))
              vm.onDishChange(index) { grams = new }
          },
          enabled = !dish.isSaved,
        )
        CustomTextField(
          modifier      = Modifier.weight(1f),
          label = "Ккал",
          value = dish.kcal,
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
          onValueChange = { new ->
            if (numberRegex.matches(new))
              vm.onDishChange(index) { kcal = new }
          },
          enabled = !dish.isSaved,
        )
      }

      Row(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment     = Alignment.CenterVertically
      ) {
        if (!dish.isSaved) {
          TextButton(
            onClick  = { vm.saveDish(index) },
            enabled  = dish.name.isNotBlank()
          ) { Text("Сохранить блюдо") }
        } else {
          Text("Сохранено", style = MaterialTheme.typography.bodySmall)
        }

        TextButton(
          onClick  = { vm.removeDish(index) }
        ) { Text("Удалить блюдо") }
      }
    }
  }
}
