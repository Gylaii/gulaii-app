package org.gulaii.app.ui.screens.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.gulaii.app.R
import org.gulaii.app.data.repository.ActivityRepository
import org.gulaii.app.data.repository.FoodRepository
import org.gulaii.app.di.ServiceLocator
import org.gulaii.app.ui.composables.ActivityCard
import org.gulaii.app.ui.composables.BottomNavBar
import org.gulaii.app.ui.composables.MetricWideCardCustom
import org.gulaii.app.ui.composables.StatSmallCard
import org.gulaii.app.ui.composables.StepsCard
import org.gulaii.app.ui.composables.TodayHeader
import org.gulaii.app.ui.navigation.Screen
import org.gulaii.app.ui.screens.walk.iconByType
import org.gulaii.app.ui.screens.walk.toDurationString
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
  onNavigateToFood: () -> Unit,
  onNavigateToWalk: () -> Unit,
  onNavigateToProfile: () -> Unit,
  foodRepo: FoodRepository = ServiceLocator.foodRepo(),
  activityRepo: ActivityRepository = ServiceLocator.activityRepo()
) {
  val todayMeals by foodRepo.entries.collectAsState(initial = emptyList())
  val todayActivities by activityRepo.entries.collectAsState(emptyList())

  val today = LocalDate.now()
  val mealsToday = remember(todayMeals) {
    todayMeals.filter { it.dateTime.toLocalDate() == today }
  }
  val activitiesToday = remember(todayActivities) {
    todayActivities.filter { it.dateTime.toLocalDate() == today }
  }

  val totalKcalToday = mealsToday.sumOf { it.calories }
  val totalKmToday   = activitiesToday.sumOf { it.distanceKm }
  val stepsToday     = (totalKmToday * 1312).toInt()

  Scaffold(
    topBar  = { TodayHeader() },
    bottomBar = { BottomNavBar(
      current = Screen.Home,
      onNavigate = { screen ->
        when (screen) {
          Screen.Food    -> onNavigateToFood()
          Screen.Walk    -> onNavigateToWalk()
          Screen.Profile -> onNavigateToProfile()
          else           -> {}
        }
      }
    ) }
  ) { pad ->

    Column(
      modifier = Modifier
        .padding(pad)
        .padding(
          start  = 24.dp,
          end    = 24.dp,
          bottom = 16.dp
        )
        .verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

      Spacer(modifier = Modifier.height(8.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
      ) {
        StepsCard(steps = stepsToday, modifier = Modifier.weight(1f).aspectRatio(1f))

        Column(
          modifier = Modifier.weight(1f),
          verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
          StatSmallCard(
            value = totalKcalToday.toString(),
            label = "Ккал"
          )
          StatSmallCard(value = "%.1f".format(totalKmToday), label = "км")
        }
      }

      Text("Питание", style = MaterialTheme.typography.headlineSmall)

      if (mealsToday.isEmpty()) {
        Text(
          text  = "Записей пока нет",
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      } else {
        mealsToday.forEach { entry ->
          MetricWideCardCustom(
            icon = R.drawable.ic_food,
            title = entry.meal.ru,
            value = entry.calories.toString(),
            unit = "Ккал"
          )
        }
      }

      Text("Активность", style = MaterialTheme.typography.headlineSmall)

      if (activitiesToday.isEmpty()) {
        Text(
          text  = "Записей пока нет",
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant
        )
      } else {
        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
          activitiesToday.forEach { act ->
            ActivityCard(
              title = act.type.ru,
              subtitle = act.durationMin.toDurationString(),
              iconRes = iconByType.getValue(act.type),
              time = "${act.distanceKm} км"
            )
          }
        }
      }
    }
  }
}
