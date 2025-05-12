package org.gulaii.app.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.gulaii.app.ui.screens.food.FoodEntryContract

@Composable
fun DishListSection(vm: FoodEntryContract) {
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
