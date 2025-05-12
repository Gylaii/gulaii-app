package org.gulaii.app.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.gulaii.app.ui.screens.food.DishUi
import org.gulaii.app.ui.screens.food.FoodEntryContract


private val numberRegex = Regex("""[0-9.,]*""")

@Composable
fun DishCard(index: Int, dish: DishUi, vm: FoodEntryContract) {
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
