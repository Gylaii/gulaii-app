package org.gulaii.app.ui.screens.food

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import org.gulaii.app.data.repository.Dish
import org.gulaii.app.data.repository.FoodEntry
import org.gulaii.app.data.repository.FoodRepository
import org.gulaii.app.di.ServiceLocator
import java.time.LocalDateTime

data class DishUi(
  var name : String = "",
  var grams: String = "",
  var kcal : String = "",

  var isSaved: Boolean = false
) {
  val kcalValue get() = kcal.replace(',', '.').toDoubleOrNull() ?: 0.0
}

data class AddFoodUiState(
  val currentMeal: MealType = MealType.BREAKFAST,
  val editDishes: List<DishUi> = listOf(DishUi()),
  val dateTime: LocalDateTime = LocalDateTime.now(),
  val savedMeals: Map<MealType, List<DishUi>> = emptyMap()
)

enum class MealType(val ru: String) {
  BREAKFAST("Завтрак"), SNACK1("Перекус 1"), LUNCH("Обед"),
  SNACK2("Перекус 2"), DINNER("Ужин"), AFTERNOON("Полдник")
}

class AddFoodEntryViewModel(
  private val foodRepo: FoodRepository = ServiceLocator.foodRepo()
) : ViewModel(), FoodEntryContract {
  private val _ui = mutableStateOf(AddFoodUiState())
  override val ui: State<AddFoodUiState> get() = _ui

  override fun selectMeal(t: MealType) { _ui.update { it.copy(currentMeal = t) } }
  override fun setDate(dt: LocalDateTime) { _ui.update { it.copy(dateTime = dt) } }

  override fun onDishChange(index: Int, mut: DishUi.() -> Unit) {
    _ui.update {
      it.copy(
        editDishes = it.editDishes.mapIndexed { idx, d ->
          if (idx == index)
            d.copy().apply(mut)
          else d
        }
      )
    }
  }

  override fun saveDish(index: Int) = _ui.update {
    it.copy(editDishes = it.editDishes.mapIndexed { idx, d ->
      if (idx == index) d.copy(isSaved = true) else d
    })
  }

  override fun addDish() = _ui.update { it.copy(editDishes = it.editDishes + DishUi()) }

  override fun removeDish(index: Int) = _ui.update {
    val remained = it.editDishes.toMutableList().also { l -> l.removeAt(index) }

    val ensuredList = if (remained.isEmpty()) listOf(DishUi()) else remained

    it.copy(editDishes = ensuredList)
  }

  override fun applyTemplate(tpl: DishUi) {
    val firstEditable = _ui.value.editDishes.indexOfFirst { !it.isSaved && it.name.isBlank() }
    if (firstEditable >= 0) {
      onDishChange(firstEditable) {
        name = tpl.name; grams = tpl.grams; kcal = tpl.kcal
      }
    } else {
      addDish()
      onDishChange(_ui.value.editDishes.lastIndex) {
        name = tpl.name; grams = tpl.grams; kcal = tpl.kcal
      }
    }
  }

  override fun saveAll() = _ui.update { state ->
    val eaten = state.editDishes.filter { it.isSaved }
    val dishes = state.editDishes.filter { it.isSaved }.map {
      Dish(
        name = it.name,
        grams = it.grams.toIntOrNull() ?: 0,
        kcal  = it.kcal .toIntOrNull() ?: 0
      )
    }

    foodRepo.upsert(
      FoodEntry(
        meal    = state.currentMeal,
        dishes   = dishes,
        dateTime = state.dateTime
      )
    )

    state.copy(
      savedMeals = state.savedMeals + (state.currentMeal to eaten),
      editDishes = listOf(DishUi())
    )
  }


  override val templates = listOf(
    DishUi("Яичница", "100", "100"),
    DishUi("Салат Цезарь", "100", "200")
  )

  private inline fun MutableState<AddFoodUiState>.update(mut: (AddFoodUiState) -> AddFoodUiState) {
    value = mut(value)
  }
}

