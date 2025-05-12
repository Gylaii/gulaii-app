package org.gulaii.app.ui.screens.walk

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.gulaii.app.R
import org.gulaii.app.data.repository.ActivityRepository
import org.gulaii.app.data.repository.ActivityType
import org.gulaii.app.di.ServiceLocator
import org.gulaii.app.ui.composables.ActivityCard
import org.gulaii.app.ui.composables.BottomNavBar
import org.gulaii.app.ui.navigation.Screen
import org.gulaii.app.ui.util.dateLabel
import org.gulaii.app.ui.util.plus
import java.time.LocalDate
import java.util.Collections.list

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WalkView(
  onNavigate: (Screen) -> Unit,
  activityRepo: ActivityRepository = ServiceLocator.activityRepo()
) {
  val activities by activityRepo.entries.collectAsState(emptyList())
  var selectMode  by remember { mutableStateOf(false) }
  var selectedIds by remember { mutableStateOf(setOf<String>()) }
  fun toggle(id: String) {
    selectedIds = if (id in selectedIds) selectedIds - id else selectedIds + id
  }

  Scaffold(
    topBar = {
      TopAppBar(
        title = { Text("Активность") },
        actions = {
          if (activities.isNotEmpty()) {
            IconButton(
              onClick = {
                if (selectMode) {
                  selectedIds.forEach(activityRepo::delete)
                  selectedIds = emptySet()
                  selectMode = false
                } else selectMode = true
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
        FloatingActionButton(onClick = { onNavigate(Screen.AddActivityEntry) }) {
          Icon(Icons.Default.Add, null)
        }
    },
    bottomBar = {
      BottomNavBar(
        current = Screen.Walk,
        onNavigate = onNavigate
      )
    }
  ) { pad ->

    LazyColumn(
      contentPadding = pad + PaddingValues(horizontal = 24.dp, vertical = 16.dp),
      verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
      activities.groupBy { it.dateTime.toLocalDate() }
        .toSortedMap(compareByDescending<LocalDate> { it })
        .forEach { (date, list) ->

          item {
            Text(
              text = date.dateLabel(),
              modifier = Modifier.padding(vertical = 4.dp),
              style = MaterialTheme.typography.headlineSmall
            )
          }

          items(list, key = { it.id }) { act ->
            ActivityCard(
              title      = act.type.ru,
              subtitle   = act.durationMin.toDurationString(),
              iconRes    = iconByType.getValue(act.type),
              time       = "${act.distanceKm} км",
              selectable = selectMode,
              selected   = act.id in selectedIds,
              onClick    = { if (selectMode) toggle(act.id) }
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
  ActivityType.RUN to R.drawable.ic_run,
  ActivityType.BIKE to R.drawable.ic_bike
)
