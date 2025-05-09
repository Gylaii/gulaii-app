package org.gulaii.app.ui.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActivityCard(
  title: String,
  subtitle: String,
  iconRes: Int,
  time: String = "",
  onClick: () -> Unit = {}
) {
  Card(
    onClick = onClick,
    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow),
    modifier = Modifier
      .fillMaxWidth()
      .padding(8.dp)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      IconInCircle(iconRes, Modifier.size(44.dp))
      Spacer(Modifier.width(12.dp))
      Column(modifier = Modifier.weight(1f)) {
        Text(title, style = MaterialTheme.typography.bodyMedium)
        Spacer(Modifier.height(4.dp))
        Text(subtitle, style = MaterialTheme.typography.bodySmall)
      }
      if (time.isNotEmpty()) {
        Text(
          text = time,
          style = MaterialTheme.typography.bodyMedium,
          modifier = Modifier.padding(start = 8.dp)
        )
      }
    }
  }
}

@Composable
fun IconInCircle(iconRes: Int, modifier: Modifier = Modifier) = Box(
  modifier = modifier
    .clip(CircleShape)
    .background(Color.White),
  contentAlignment = Alignment.Center
) {
  Icon(
    painter = painterResource(iconRes),
    contentDescription = null,
    tint = MaterialTheme.colorScheme.onBackground,
    modifier = Modifier.size(24.dp)
  )
}

fun dateLabel(date: LocalDate): String =
  when (date) {
    LocalDate.now()            -> "Сегодня"
    LocalDate.now().minusDays(1) -> "Вчера"
    else                        -> date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.getDefault())
  }

