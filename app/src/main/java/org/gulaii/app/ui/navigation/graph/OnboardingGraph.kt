package org.gulaii.app.ui.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.gulaii.app.ui.navigation.OnboardingGraph
import org.gulaii.app.ui.navigation.Screen
import org.gulaii.app.ui.screens.onboardingScreen.OnboardingView

fun NavGraphBuilder.registerOnboardingGraph(nav: NavHostController) {
  navigation<OnboardingGraph>(startDestination = Screen.Onboarding) {
    composable<Screen.Onboarding> {
      OnboardingView(onFinished = { nav.navigate(Screen.Auth) })
    }
  }
}
