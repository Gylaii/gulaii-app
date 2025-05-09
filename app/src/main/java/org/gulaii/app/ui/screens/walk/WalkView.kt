package org.gulaii.app.ui.screens.walk

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.gulaii.app.R
import org.gulaii.app.ui.navigation.BottomNavBar
import org.gulaii.app.ui.navigation.Screen
import java.time.LocalDate
import org.gulaii.app.ui.util.plus
import org.gulaii.app.ui.util.dateLabel
import org.gulaii.app.ui.util.ActivityCard

data class WalkActivity(
  val title: String,
  val duration: String,
  val distance: String,
  val date: LocalDate,
  val icon: Int = R.drawable.ic_walk
)

@Composable
fun WalkView(
  nav: NavHostController,
  onAdd: () -> Unit = {},
  entries: List<WalkActivity> = remember { demoWalks }
) {
  val cs = MaterialTheme.colorScheme
  Scaffold(
    floatingActionButton = {
      FloatingActionButton(
        onClick = onAdd,
        containerColor = cs.primaryContainer
      ) { Icon(Icons.Default.Add, contentDescription = "add") }
    },
    bottomBar = { BottomNavBar(nav, Screen.Walk) }
  ) { pad ->
    LazyColumn(
      contentPadding = pad + PaddingValues(horizontal = 24.dp, vertical = 16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      entries
        .groupBy { it.date }
        .toSortedMap(compareByDescending { it })
        .forEach { (date, walks) ->
          item {
            Text(
              text = dateLabel(date),
              modifier = Modifier.padding(vertical = 4.dp)
            )
          }

          items(walks) { walk ->
            ActivityCard(
              title = walk.title,
              subtitle = walk.duration,
              iconRes = walk.icon,
              time = walk.distance
            )
          }
        }
    }
  }
}

private val demoWalks = listOf(
  WalkActivity("Вечерняя прогулка",  "32 минуты", "1.51 км", LocalDate.now()),
  WalkActivity("Утренняя прогулка", "1 час 14 минут", "3 км", LocalDate.now()),
  WalkActivity("Прогулка",  "32 минуты", "1.51 км", LocalDate.now().minusDays(1)),
)
