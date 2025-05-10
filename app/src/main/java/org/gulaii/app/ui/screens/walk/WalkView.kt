package org.gulaii.app.ui.screens.walk

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.gulaii.app.R
import org.gulaii.app.data.repository.ActivityType
import org.gulaii.app.di.ServiceLocator
import org.gulaii.app.ui.navigation.BottomNavBar
import org.gulaii.app.ui.navigation.Screen
import org.gulaii.app.ui.util.ActivityCard
import org.gulaii.app.ui.util.dateLabel
import org.gulaii.app.ui.util.plus
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalkView(
  nav: NavHostController,
) {
  val cs = MaterialTheme.colorScheme
  val activities by ServiceLocator.activityRepo()
    .entries
    .collectAsState(initial = emptyList())

  Scaffold(
    floatingActionButton = {
      FloatingActionButton(
        onClick = { nav.navigate(Screen.AddActivityEntry) },
        containerColor = cs.primaryContainer
      ) { Icon(Icons.Default.Add, contentDescription = "add") }
    },
    bottomBar = { BottomNavBar(nav, Screen.Walk) }
  ) { pad ->

    LazyColumn(
      contentPadding = pad + PaddingValues(horizontal = 24.dp, vertical = 16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
      activities
        .groupBy { it.dateTime.toLocalDate() }
        .toSortedMap(compareByDescending<LocalDate> { it })
        .forEach { (date, listForDate) ->

          item {
            Text(
              text = dateLabel(date),
              modifier = Modifier.padding(vertical = 4.dp),
              style = MaterialTheme.typography.titleMedium
            )
          }

          items(listForDate) { act ->
            ActivityCard(
              title    = act.type.ru,
              subtitle = act.durationMin.toDurationString(),
              iconRes  = iconByType.getValue(act.type),
              time     = "${act.distanceKm} км"
            )
          }
        }
    }
  }
}

fun Int.toDurationString(): String {
  val hours = this / 60
  val minutes = this % 60
  fun hourWord(h: Int) = when {
    h % 10 == 1 && h % 100 != 11 -> "час"
    h % 10 in 2..4 && h % 100 !in 12..14 -> "часа"
    else -> "часов"
  }
  return when {
    this < 60 -> "$this мин"
    minutes == 0 -> "$hours ${hourWord(hours)}"
    else -> "$hours ${hourWord(hours)} $minutes мин"
  }
}

val iconByType: Map<ActivityType, Int> = mapOf(
  ActivityType.WALK to R.drawable.ic_walk,
  ActivityType.RUN  to R.drawable.ic_run,
  ActivityType.BIKE to R.drawable.ic_bike
)
