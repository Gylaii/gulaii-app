package org.gulaii.app.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun ActionCardButton(
  icon: ImageVector,
  contentDescription: String?,
  onClick: () -> Unit
) = ElevatedCard(
  shape = RoundedCornerShape(12.dp),
  colors = CardDefaults.elevatedCardColors(
    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
  ),
  elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp),
  modifier = Modifier
    .size(48.dp)
    .clickable(onClick = onClick)
) {
  Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
    Icon(
      imageVector = icon,
      contentDescription = contentDescription,
      tint = MaterialTheme.colorScheme.onSurfaceVariant
    )
  }
}
