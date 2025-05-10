package org.gulaii.app.ui.screens.food

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import org.gulaii.app.data.repository.Dish
import org.gulaii.app.data.repository.FoodEntry
import org.gulaii.app.data.repository.FoodRepository
import androidx.compose.runtime.State
import java.time.LocalDateTime

class EditFoodVM(
  private val original: FoodEntry,
  private val repo: FoodRepository
) : ViewModel(), FoodEntryContract {

  private val _ui = mutableStateOf(
    AddFoodUiState(
      currentMeal = original.meal,
      dateTime    = original.dateTime,
      editDishes  = original.dishes.map {
        DishUi(it.name, it.grams.toString(), it.kcal.toString(), true)
      }
    )
  )
  override val ui: State<AddFoodUiState> = _ui

  private inline fun update(mut: (AddFoodUiState) -> AddFoodUiState) {
    _ui.value = mut(_ui.value)
  }

  override fun selectMeal(t: MealType)      = update { it.copy(currentMeal = t) }
  override fun setDate(dt: LocalDateTime)   = update { it.copy(dateTime = dt) }

  override fun onDishChange(i: Int, mut: DishUi.() -> Unit) = update {
    it.copy(editDishes = it.editDishes.mapIndexed { idx, d ->
      if (idx == i) d.copy().apply(mut) else d
    })
  }

  override fun saveDish(i: Int) = update {
    it.copy(editDishes = it.editDishes.mapIndexed { idx, d ->
      if (idx == i) d.copy(isSaved = true) else d
    })
  }

  override fun addDish()  = update { it.copy(editDishes = it.editDishes + DishUi()) }
  override fun removeDish(i: Int) = update {
    it.copy(editDishes = it.editDishes.filterIndexed { idx, _ -> idx != i })
  }

  override fun applyTemplate(tpl: DishUi) {
    val first = _ui.value.editDishes.indexOfFirst { !it.isSaved && it.name.isBlank() }
    if (first >= 0) onDishChange(first) { name = tpl.name; grams = tpl.grams; kcal = tpl.kcal }
    else {
      addDish()
      onDishChange(_ui.value.editDishes.lastIndex) {
        name = tpl.name; grams = tpl.grams; kcal = tpl.kcal
      }
    }
  }

  override val templates                   = listOf(
    DishUi("Яичница", "100", "100"),
    DishUi("Салат Цезарь", "100", "200")
  )

  override fun saveAll() = update { state ->
    val dishes = state.editDishes.filter { it.isSaved }.map {
      Dish(it.name, it.grams.toInt(), it.kcal.toInt())
    }
    repo.upsert(
      FoodEntry(
        meal    = state.currentMeal,
        dishes   = dishes,
        dateTime = state.dateTime
      )
    )
    state
  }

  companion object {
    fun factory(e: FoodEntry, r: FoodRepository) =
      viewModelFactory { EditFoodVM(e, r) }
  }
}

