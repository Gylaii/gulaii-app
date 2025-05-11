// 📁 ui/navigation/AppNavHost.kt
package org.gulaii.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import kotlinx.coroutines.delay
import org.gulaii.app.ui.navigation.graph.*
import org.gulaii.app.ui.screens.common.LoadingScreen

@Composable
fun AppNavHost(
  navController: NavHostController,
  modifier: Modifier = Modifier,
) {
  NavHost(
    navController = navController,
    startDestination = InitialGraph,
    modifier = modifier
  ) {
    registerInitialGraph(navController)
    registerOnboardingGraph(navController)
    registerAuthGraph(navController)

    registerWizardGraph(navController) {
      navController.navigate(Screen.Loading)
    }

    composable<Screen.Loading> {
      LoadingScreen()
      LaunchedEffect(Unit) {
        delay(800)
        navController.navigate(HomeGraph) {
          popUpTo<Screen.Loading> { inclusive = true }
        }
      }
    }

    registerHomeGraph(navController)
  }
}
