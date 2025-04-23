package org.gulaii.app.ui.navigation
import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
  @Serializable data object Initial   : Screen
  @Serializable data object Onboarding: Screen
  @Serializable data object Auth      : Screen
}

@Serializable data object InitialGraph
@Serializable data object OnboardingGraph
@Serializable data object AuthGraph

