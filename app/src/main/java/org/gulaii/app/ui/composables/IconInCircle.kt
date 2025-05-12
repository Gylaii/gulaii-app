package org.gulaii.app.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

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
    modifier = Modifier.size(30.dp)
  )
}
