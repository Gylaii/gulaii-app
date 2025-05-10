package org.gulaii.app.ui.navigation
import kotlinx.serialization.Serializable

@Serializable
sealed interface Screen {
  @Serializable data object Initial   : Screen
  @Serializable data object Onboarding: Screen
  @Serializable data object Auth      : Screen
  @Serializable data object Recovery : Screen
  @Serializable data object Otp: Screen

  @Serializable data object Home      : Screen
  @Serializable data object Food      : Screen
  @Serializable data object Walk       : Screen
  @Serializable data object Loading       : Screen
  @Serializable data object Profile : Screen

  @Serializable data class EditFoodEntry(val id: String) : Screen


  @Serializable
  data object AddWalkEntry : Screen
  @Serializable
  data object AddFoodEntry : Screen
}

@Serializable data object InitialGraph
@Serializable data object OnboardingGraph
@Serializable data object AuthGraph
@Serializable object HomeGraph
