package org.gulaii.app.ui.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import org.gulaii.app.ui.navigation.Screen
import org.gulaii.app.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(nav: NavHostController) {

  @Composable
  fun NavItem(icon: Int, dest: Screen) {
    IconButton({ nav.navigate(dest) }) {
      Icon(
        painterResource(icon),
        contentDescription = dest.toString(),
        modifier = Modifier.size(28.dp)
      )
    }
  }

  Scaffold(
    bottomBar = {
      Box(
        Modifier
          .fillMaxWidth()
          .padding(16.dp)
          .height(64.dp)
          .clip(RoundedCornerShape(24.dp))
          .background(MaterialTheme.colorScheme.surfaceVariant)
      ) {
        Row(Modifier.fillMaxSize(),
          horizontalArrangement = Arrangement.SpaceEvenly,
          verticalAlignment = Alignment.CenterVertically) {

          @Composable
          fun Item(icon: Int, dest: Screen) {
            IconButton({ nav.navigate(dest) }) {
              Icon(
                painterResource(icon),
                contentDescription = dest.toString(),
                modifier = Modifier.size(28.dp)
              )
            }
          }
          Item(R.drawable.ic_home , Screen.Home)
          Item(R.drawable.ic_food , Screen.Food)
          Item(R.drawable.ic_map  , Screen.Map )
          Item(R.drawable.ic_profile, Screen.Profile)
        }
      }
    }
  ) { pad ->
    Column(Modifier.padding(pad).padding(24.dp)) {

      Card(
        Modifier
          .fillMaxWidth()
          .aspectRatio(1f)
      ) { Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Шаги\n7", style = MaterialTheme.typography.headlineMedium,
          textAlign = TextAlign.Center)
      }
      }

      Spacer(Modifier.height(16.dp))

      Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        Card(Modifier.weight(1f)) {
          Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Ккал");   Text("1")
          }
        }
        Card(Modifier.weight(1f)) {
          Column(Modifier.padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Text("Б/Ж/У");  Text("90")
          }
        }
      }
    }
  }
}
