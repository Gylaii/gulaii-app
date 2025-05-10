package org.gulaii.app.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import org.gulaii.app.ui.screens.food.MealType
import java.time.LocalDateTime

data class Dish(
  val name: String,
  val grams: Int,
  val kcal: Int
)

data class FoodEntry(
  val meal    : MealType,
  val dishes  : List<Dish>,
  val dateTime: LocalDateTime
) {
  val id       get() = "${meal.name}_${dateTime.toLocalDate()}"
  val calories get() = dishes.sumOf { it.kcal }
}

class FoodRepository {

  private val _entries = MutableStateFlow(emptyList<FoodEntry>())
  val entries: StateFlow<List<FoodEntry>> = _entries.asStateFlow()

  fun upsert(entry: FoodEntry) {
    _entries.value = _entries.value
      .filterNot { it.id == entry.id } + entry
  }

  fun find(id: String): FoodEntry? =
    _entries.value.firstOrNull { it.id == id }
}
