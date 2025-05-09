package org.gulaii.app.ui.screens.food

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.gulaii.app.R
import java.time.LocalDate
import org.gulaii.app.ui.util.plus
import org.gulaii.app.ui.navigation.BottomNavBar
import org.gulaii.app.ui.navigation.Screen

data class FoodEntry(
  val title: String,
  val calories: Int,
  val date: LocalDate,
  val icon: Int = R.drawable.ic_food2
)

@Composable
fun FoodView(
  nav: NavHostController,
  onAdd: () -> Unit = {},
  meals: List<FoodEntry> = remember { demoFood }
) {
  val cs = MaterialTheme.colorScheme
  Scaffold(
    floatingActionButton = {
      FloatingActionButton(onClick = onAdd, containerColor = cs.primaryContainer) {
        Icon(Icons.Default.Add, contentDescription = null)
      }
    },
    bottomBar = { BottomNavBar(nav, Screen.Food) },
    content = { paddingValues ->
      LazyColumn(
        contentPadding = paddingValues + PaddingValues(horizontal = 24.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        meals
          .groupBy { it.date }
          .toSortedMap(compareByDescending { it })
          .forEach { (date, list) ->
            item {
              Text(dateLabel(date), style = MaterialTheme.typography.headlineSmall)
            }
            items(list) { f ->
              org.gulaii.app.ui.screens.walk.ActivityCard(
                title    = f.title,
                subtitle = "${f.calories} Cal",
                iconRes  = f.icon
              )
            }
          }
      }
    }
  )
}

private val demoFood = listOf(
  FoodEntry("Chicken salad", 350, LocalDate.now()),
  FoodEntry("Protein shake", 220, LocalDate.now()),
  FoodEntry("Omelette",      280, LocalDate.now().minusDays(1)),
)
private fun dateLabel(d: LocalDate) = org.gulaii.app.ui.screens.walk.dateLabel(d)
