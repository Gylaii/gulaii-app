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
import org.gulaii.app.R
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import org.gulaii.app.ui.navigation.BottomNavBar
import org.gulaii.app.ui.navigation.Screen
import org.gulaii.app.ui.util.plus

data class WalkActivity(
  val title: String,
  val duration: String,
  val distance: String,
  val points: Int,
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
              style = MaterialTheme.typography.headlineSmall,
              modifier = Modifier.padding(vertical = 4.dp)
            )
          }

          items(walks) { walk ->
            ActivityCard(
              title    = walk.title,
              subtitle = "${walk.duration} • ${walk.distance} • ${walk.points} pts",
              iconRes  = walk.icon
            )
          }
        }
    }
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityCard(
  title: String,
  subtitle: String,
  iconRes: Int,
  onClick: () -> Unit = {}
) = ElevatedCard(
  onClick = onClick,
  colors = CardDefaults.elevatedCardColors(
    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
  ),
  modifier = Modifier.fillMaxWidth()
) {
  ListItem(
    headlineContent   = { Text(title) },
    supportingContent = { Text(subtitle) },
    trailingContent   = { Text("12:00 PM") },      // TODO время из модели
    leadingContent    = {
      Icon(
        painterResource(iconRes),
        contentDescription = null,
        tint = MaterialTheme.colorScheme.onTertiaryContainer,
        modifier = Modifier
          .size(36.dp)
          .padding(4.dp)
      )
    }
  )
}

/* ---------- Utils & Mock ---------- */

private val demoWalks = listOf(
  WalkActivity("Lunch walk",  "32 min", "1,51 km", 5, LocalDate.now()),
  WalkActivity("Late night bike ride", "1h 14min", "—", 4, LocalDate.now()),
  WalkActivity("Lunch walk",  "32 min", "1,51 km", 5, LocalDate.now().minusDays(1)),
  WalkActivity("Evening walk","34 min", "1,34 km", 6, LocalDate.now().minusDays(1)),
  WalkActivity("Afternoon walk","29 min","1,45 km", 8, LocalDate.now().minusDays(1)),
)

fun dateLabel(date: LocalDate): String =
  when (date) {
    LocalDate.now() -> "Today"
    LocalDate.now().minusDays(1) -> "Yesterday"
    else -> date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
  }
