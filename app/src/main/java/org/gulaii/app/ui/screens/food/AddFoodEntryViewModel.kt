package org.gulaii.app.ui.screens.food

import androidx.compose.foundation.clickable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.time.LocalDateTime
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.gulaii.app.data.repository.Dish
import org.gulaii.app.data.repository.FoodRepository
import org.gulaii.app.data.repository.FoodEntry
import org.gulaii.app.di.ServiceLocator
import org.gulaii.app.ui.composables.CustomTextField
import java.time.format.DateTimeParseException
import java.time.*

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

  override fun onDishChange(i: Int, mut: DishUi.() -> Unit) {
    _ui.update {
      it.copy(
        editDishes = it.editDishes.mapIndexed { idx, d ->
          if (idx == i)
            d.copy().apply(mut)
          else d
        }
      )
    }
  }

  override fun saveDish(i: Int) = _ui.update {
    it.copy(editDishes = it.editDishes.mapIndexed { idx, d ->
      if (idx == i) d.copy(isSaved = true) else d
    })
  }

  override fun addDish() = _ui.update { it.copy(editDishes = it.editDishes + DishUi()) }

  override fun removeDish(i: Int) = _ui.update {
    val remained = it.editDishes.toMutableList().also { l -> l.removeAt(i) }

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
      addDish();
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateTimeSection(
  dateTime: LocalDateTime,
  onDateChange: (LocalDateTime) -> Unit
) {
  var isEditing by remember { mutableStateOf(false) }
  var editedDateTime by remember { mutableStateOf(dateTime) }

  val toggleEditing = {
    isEditing = !isEditing
    if (!isEditing) {
      onDateChange(editedDateTime)
    }
  }

  fun formatDate(date: String): String {
    val parts = date.split("-")
    return if (parts.size == 3) {
      val month = parts[1].padStart(2, '0')
      val day = parts[2].padStart(2, '0')
      "${parts[0]}-$month-$day"
    } else {
      date
    }
  }

  fun formatTime(time: String): String {
    val parts = time.split(":")
    return if (parts.size == 2) {
      val hour = parts[0].padStart(2, '0')
      val minute = parts[1].padStart(2, '0')
      "$hour:$minute"
    } else {
      time
    }
  }

  OutlinedCard(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { if (isEditing) toggleEditing() }
  ) {
    Row(
      Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Column(Modifier.weight(1f)) {
        CustomTextField(
          label = "Дата",
          value = editedDateTime.toLocalDate().toString(),
          onValueChange = { newValue ->
            try {
              val formattedDate = formatDate(newValue)
              val newDate = LocalDate.parse(formattedDate)
              editedDateTime = LocalDateTime.of(newDate, editedDateTime.toLocalTime())
            } catch (e: DateTimeParseException) {
            }
          },
          enabled = isEditing,
          modifier = Modifier.fillMaxWidth()
        )

        CustomTextField(
          label = "Время",
          value = editedDateTime.toLocalTime().withSecond(0).withNano(0).toString(),
          onValueChange = { newTime ->
            try {
              val formattedTime = formatTime(newTime)
              val newLocalTime = LocalTime.parse(formattedTime)
              editedDateTime = LocalDateTime.of(editedDateTime.toLocalDate(), newLocalTime)
            } catch (e: DateTimeParseException) {
            }
          },
          enabled = isEditing,
          modifier = Modifier.fillMaxWidth()
        )
      }

      Icon(
        imageVector = if (isEditing) Icons.Filled.Check else Icons.Filled.Edit,
        contentDescription = if (isEditing) "Сохранить" else "Редактировать",
        modifier = Modifier
          .clickable { toggleEditing() }
          .padding(start = 16.dp)
          .size(24.dp)
      )
    }
  }
}
