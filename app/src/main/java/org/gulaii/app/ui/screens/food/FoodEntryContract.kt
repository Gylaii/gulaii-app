package org.gulaii.app.ui.screens.food

import androidx.compose.runtime.State
import java.time.LocalDateTime

interface FoodEntryContract {
  val ui: State<AddFoodUiState>

  fun selectMeal(t: MealType)
  fun setDate(dt: LocalDateTime)

  fun onDishChange(index: Int, mut: DishUi.() -> Unit)
  fun saveDish(index: Int)
  fun addDish()
  fun removeDish(index: Int)

  fun applyTemplate(tpl: DishUi)

  fun saveAll()
  val templates: List<DishUi>
}
