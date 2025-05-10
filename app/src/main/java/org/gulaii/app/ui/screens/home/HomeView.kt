package org.gulaii.app.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.snapping.SnapPosition
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import org.gulaii.app.R
import org.gulaii.app.data.repository.FoodRepository
import org.gulaii.app.di.ServiceLocator
import org.gulaii.app.ui.navigation.BottomNavBar
import org.gulaii.app.ui.navigation.Screen
import org.gulaii.app.ui.theme.roseLight
import java.time.LocalDate
import androidx.compose.runtime.getValue
import org.gulaii.app.data.repository.ActivityRepository
import org.gulaii.app.ui.util.ActivityCard
import org.gulaii.app.ui.util.plus
import androidx.compose.runtime.*
import org.gulaii.app.ui.screens.walk.toDurationString
import org.gulaii.app.ui.screens.walk.iconByType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
  nav: NavHostController,
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
    bottomBar = { BottomNavBar(nav, Screen.Home) }
  ) { pad ->

    Column(
      modifier = Modifier
        .padding(pad)
        .padding(horizontal = 24.dp, vertical = 16.dp)
        .verticalScroll(rememberScrollState()),
      verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {

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
            icon  = R.drawable.ic_food,
            title = entry.meal.ru,
            value = entry.calories.toString(),
            unit  = "Ккал"
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
        activitiesToday.forEach { act ->
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

@Composable
private fun StepsCard(
  steps: Int,
  modifier: Modifier = Modifier
) = Card(
  modifier = modifier,
  colors = CardDefaults.cardColors(containerColor = roseLight),
  shape  = RoundedCornerShape(16.dp)
) {
  Box(Modifier.fillMaxSize().padding(16.dp)) {
    Row(
      modifier = Modifier.align(Alignment.TopStart),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconInCircle(iconRes = R.drawable.ic_steps, modifier = Modifier.size(44.dp))
      Spacer(Modifier.width(8.dp))
      Text("Шаги",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.primary,
        fontSize = 25.sp
      )
    }
    Text(
      text = steps.toString(),
      style = MaterialTheme.typography.bodyLarge,
      modifier = Modifier.align(Alignment.Center),
      fontSize = 35.sp
    )
  }
}

@Composable
private fun StatSmallCard(
  value: String,
  label: String
) = OutlinedCard(
  modifier = Modifier
    .fillMaxWidth()
    .height(79.dp),
  shape  = RoundedCornerShape(16.dp),
  border = CardDefaults.outlinedCardBorder().copy(width = 1.dp)
) {
  Column(
    Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = value,
      style = MaterialTheme.typography.bodyLarge,
      textAlign = TextAlign.Center,
      fontSize = 20.sp
    )

    Text(
      text = label,
      style = MaterialTheme.typography.bodyLarge,
      textAlign = TextAlign.Center,
      fontSize = 20.sp
    )
  }
}

@Composable
private fun MetricWideCardCustom(
  icon: Int,
  title: String,
  value: String,
  unit: String,
  extra: String? = null
) = Card(
  modifier = Modifier
    .fillMaxWidth()
    .heightIn(min = 80.dp),
  colors = CardDefaults.cardColors(
    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
  ),
  shape = RoundedCornerShape(16.dp)
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .padding(16.dp),
    verticalAlignment = Alignment.CenterVertically
  ) {

    IconInCircle(
      iconRes = icon,
      modifier = Modifier.size(44.dp)
    )
    Spacer(Modifier.width(12.dp))

    Column(
      modifier = Modifier.weight(1f)
    ) {
      Text(
        text = title
      )
      extra?.let {
        Text(
          text = it,
          style = MaterialTheme.typography.bodySmall
        )
      }
    }

    Row(
      verticalAlignment = Alignment.Bottom
    ) {
      Text(value)
      Spacer(Modifier.width(4.dp))
      Text(unit)
    }
  }
}

@Composable
private fun IconInCircle(iconRes: Int, modifier: Modifier = Modifier) = Box(
  modifier = modifier
    .clip(CircleShape)
    .background(Color.White),
  contentAlignment = Alignment.Center
) {
  Icon(
    painter = painterResource(iconRes),
    contentDescription = null,
    tint = MaterialTheme.colorScheme.onBackground,
    modifier = Modifier.size(30.dp)
  )
}

@Composable
private fun TodayHeader() = Box(
  modifier = Modifier
    .fillMaxWidth()
    .background(roseLight)
    .padding(
      WindowInsets.statusBars
        .asPaddingValues() + PaddingValues(vertical = 12.dp, horizontal = 24.dp)
    ),
  contentAlignment = Alignment.Center
) {
  Text(
    text = "Ваши данные за сегодня",
    style = MaterialTheme.typography.headlineMedium,
    color = MaterialTheme.colorScheme.onPrimary,
    textAlign = TextAlign.Center,
    modifier = Modifier.fillMaxWidth()
  )
}


