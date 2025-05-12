package org.gulaii.app.ui.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.gulaii.app.R
import org.gulaii.app.ui.theme.roseLight


@Composable
fun StepsCard(
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
