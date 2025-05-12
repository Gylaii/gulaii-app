package org.gulaii.app.ui.screens.food

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import org.gulaii.app.R
import org.gulaii.app.data.repository.FoodRepository
import org.gulaii.app.di.ServiceLocator
import org.gulaii.app.ui.composables.ActivityCard
import org.gulaii.app.ui.composables.BottomNavBar
import org.gulaii.app.ui.navigation.Screen
import org.gulaii.app.ui.util.dateLabel
import org.gulaii.app.ui.util.plus
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FoodView(
  onNavigateToAddEntry: () -> Unit,
  onNavigateToEditEntry: (String) -> Unit,
  onNavigate: (Screen) -> Unit,
  foodRepo: FoodRepository = ServiceLocator.foodRepo(),
) {
  val meals by foodRepo.entries.collectAsState(initial = emptyList())
  val timeFmt = remember { DateTimeFormatter.ofPattern("HH:mm") }

  var selectMode      by remember { mutableStateOf(false) }
  var selectedIds     by remember { mutableStateOf(setOf<String>()) }
  fun toggleSelect(id: String) {
    selectedIds = if (id in selectedIds) selectedIds - id else selectedIds + id
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Питание") },
        actions = {
          if (meals.isNotEmpty()) {
            IconButton(
              onClick = {
                if (selectMode) {
                  selectedIds.forEach(foodRepo::delete)
                  selectedIds = emptySet()
                  selectMode  = false
                } else {
                  selectMode = true
                }
              }
            ) {
              Icon(
                imageVector = if (selectMode) Icons.Default.Check
                else Icons.Default.Delete,
                contentDescription = if (selectMode)
                  "Подтвердить удаление" else "Удалить"
              )
            }
          }
        }
      )
    },
    floatingActionButton = {
      if (!selectMode)
        FloatingActionButton(onClick = onNavigateToAddEntry) {
          Icon(Icons.Default.Add, null)
        }
    },
    bottomBar = {
      BottomNavBar(
        current = Screen.Food,
        onNavigate = onNavigate
      )
    }
  ) { pad ->

    LazyColumn(
      contentPadding = pad + PaddingValues(horizontal = 24.dp, vertical = 16.dp),
      verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
      meals.groupBy { it.dateTime.toLocalDate() }
        .toSortedMap(compareByDescending<LocalDate> { it })
        .forEach { (date, list) ->

          item {
            Text(
              text  = date.dateLabel(),
              style = MaterialTheme.typography.headlineSmall
            )
          }

          items(list, key = { it.id }) { entry ->
            ActivityCard(
              title       = entry.meal.ru,
              subtitle    = "${entry.calories} Ккал",
              iconRes     = R.drawable.ic_food2,
              time        = entry.dateTime.format(timeFmt),
              selectable  = selectMode,
              selected    = entry.id in selectedIds,
              onClick     = {
                if (selectMode)   toggleSelect(entry.id)
                else              onNavigateToEditEntry(entry.id)
              }
            )
          }
        }
    }
  }
}
