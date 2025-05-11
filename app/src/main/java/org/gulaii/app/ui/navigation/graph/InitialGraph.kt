package org.gulaii.app.ui.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.gulaii.app.ui.navigation.Screen
import org.gulaii.app.ui.navigation.InitialGraph
import org.gulaii.app.ui.screens.initialScreen.InitialScreenView

fun NavGraphBuilder.registerInitialGraph(nav: NavHostController) {
  navigation<InitialGraph>(startDestination = Screen.Initial) {
    composable<Screen.Initial> {
      InitialScreenView(onNext = { nav.navigate(Screen.Onboarding) })
    }
  }
}
