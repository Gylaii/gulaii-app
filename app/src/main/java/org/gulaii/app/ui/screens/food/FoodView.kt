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
import org.gulaii.app.ui.util.plus
import org.gulaii.app.ui.navigation.BottomNavBar
import org.gulaii.app.ui.navigation.Screen
import org.gulaii.app.ui.util.ActivityCard
import org.gulaii.app.ui.util.dateLabel
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class FoodEntry(
  val title: String,
  val calories: Int,
  val dateTime: LocalDateTime,
  val icon: Int = R.drawable.ic_food2
)

@Composable
fun FoodView(
  nav: NavHostController,
  onAdd: () -> Unit = {},
  meals: List<FoodEntry> = remember { demoFood }
) {
  val cs = MaterialTheme.colorScheme

  val timeFormatter = DateTimeFormatter.ofPattern("HH:mm")
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
          .groupBy { it.dateTime.toLocalDate() }
          .toSortedMap(compareByDescending { it })
          .forEach { (date, list) ->
            item {
              Text(
                text = dateLabel(date),
                style = MaterialTheme.typography.headlineSmall
              )
            }
            items(list) { f ->
              ActivityCard(
                title    = f.title,
                subtitle = "${f.calories} Ккал",
                iconRes  = f.icon,

                time     = f.dateTime.format(timeFormatter)
              )
            }
          }
      }
    }
  )
}

private val demoFood = listOf(
  FoodEntry("Завтрак", 350, LocalDateTime.now().withHour(8).withMinute(0)),
  FoodEntry("Обед",    620, LocalDateTime.now().withHour(13).withMinute(45)),
  FoodEntry("Ужин",    280, LocalDateTime.now().minusDays(1).withHour(19).withMinute(30))
)
