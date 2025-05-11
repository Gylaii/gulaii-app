package org.gulaii.app.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import org.gulaii.app.R
import org.gulaii.app.ui.navigation.Screen
import org.gulaii.app.ui.theme.roseLight

@Composable
fun BottomNavBar(
  current: Screen,
  onNavigate: (Screen) -> Unit
) {
  Box(
    Modifier
      .fillMaxWidth()
      .padding(16.dp)
      .height(64.dp)
      .clip(RoundedCornerShape(24.dp))
      .background(roseLight)
  ) {
    Row(
      Modifier.fillMaxSize(),
      horizontalArrangement = Arrangement.SpaceEvenly,
      verticalAlignment = Alignment.CenterVertically
    ) {
      @Composable
      fun Item(icon: Int, dest: Screen) {
        IconButton({ onNavigate(dest) }) {
          Icon(
            painterResource(icon),
            contentDescription = dest.toString(),
            modifier = Modifier.size(28.dp),
            tint = if (dest == current)
              MaterialTheme.colorScheme.primary
            else
              MaterialTheme.colorScheme.onSurface
          )
        }
      }

      Item(R.drawable.ic_home   , Screen.Home)
      Item(R.drawable.ic_food   , Screen.Food)
      Item(R.drawable.ic_walk   , Screen.Walk)
      Item(R.drawable.ic_profile, Screen.Profile)
    }
  }
}
