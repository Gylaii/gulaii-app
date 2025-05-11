package org.gulaii.app.ui.screens.food

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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

@Composable
fun FoodView(
  onNavigateToAddEntry: () -> Unit,
  onNavigateToEditEntry: (String) -> Unit,
  onNavigate: (Screen) -> Unit,
  foodRepo: FoodRepository = ServiceLocator.foodRepo(),
) {
  val meals by foodRepo.entries.collectAsState(initial = emptyList())
  val timeFormatter = remember { DateTimeFormatter.ofPattern("HH:mm") }
  val cs = MaterialTheme.colorScheme

  Scaffold(
    floatingActionButton = {
      FloatingActionButton(
        onClick = onNavigateToAddEntry,
        containerColor = cs.primaryContainer
      ) {
        Icon(Icons.Default.Add, contentDescription = "add")
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

          items(list) { entry ->
            ActivityCard(
              title    = entry.meal.ru,
              subtitle = "${entry.calories} Ккал",
              iconRes  = R.drawable.ic_food2,
              time     = entry.dateTime.format(timeFormatter),
              onClick  = { onNavigateToEditEntry(entry.id) }
            )
          }
        }
    }
  }
}
