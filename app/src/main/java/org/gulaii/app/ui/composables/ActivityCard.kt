package org.gulaii.app.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun ActivityCard(
  title: String,
  subtitle: String,
  iconRes: Int,
  time: String = "",
  selectable: Boolean = false,
  selected:  Boolean = false,
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
      if (selectable) {
        Checkbox(
          checked   = selected,
          onCheckedChange = { onClick() }
        )
        Spacer(Modifier.width(8.dp))
      }

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
