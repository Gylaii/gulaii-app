package org.gulaii.app.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
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


@Composable
fun MetricWideCardCustom(
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
