package org.gulaii.app.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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
import org.gulaii.app.ui.navigation.BottomNavBar
import org.gulaii.app.ui.navigation.Screen
import org.gulaii.app.ui.theme.roseLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(nav: NavHostController) {
  Scaffold(
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
        StepsCard(
          modifier = Modifier
            .weight(1f)
            .aspectRatio(1f)
        )

        Column(
          modifier = Modifier.weight(1f),
          verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
          StatSmallCard(value = "300", label = "Ккал")
          StatSmallCard(value = "15",  label = "км")
        }
      }

      MetricWideCardCustom(
        icon  = R.drawable.ic_walk,
        title = "Прогулка",
        value = "2.8",
        unit  = "км",
        extra = "1 час 34 минуты"
      )

      MetricWideCardCustom(
        icon  = R.drawable.ic_food,
        title = "Ужин",
        value = "300",
        unit  = "Ккал"
      )
    }
  }
}

@Composable
private fun StepsCard(modifier: Modifier = Modifier) = Card(
  modifier = modifier,
  colors = CardDefaults.cardColors(containerColor = roseLight),
  shape  = RoundedCornerShape(16.dp)
) {
  Box(
    modifier = Modifier
      .fillMaxSize()
      .padding(16.dp)
  ) {

    Row(
      modifier = Modifier.align(Alignment.TopStart),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconInCircle(
        iconRes = R.drawable.ic_steps,
        modifier = Modifier.size(44.dp)
      )
      Spacer(Modifier.width(8.dp))
      Text(
        text = "Шаги",
        style = MaterialTheme.typography.bodyLarge,
        color = MaterialTheme.colorScheme.primary,
        textAlign = TextAlign.Center,
        fontSize = 25.sp
      )
    }

    Text(
      text = "8 225",
      style = MaterialTheme.typography.bodyLarge,
      modifier = Modifier.align(Alignment.Center),
      textAlign = TextAlign.Center,
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
