package org.gulaii.app.ui.navigation

sealed class Screen(val route: String) {
  object Initial : Screen("initial_screen")
  object Onboarding : Screen("onboarding_screen")
}
