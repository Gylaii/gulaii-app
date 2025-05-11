package org.gulaii.app.ui.navigation
import kotlinx.serialization.Serializable
@Serializable
sealed interface Screen {
  // Initial Flow
  @Serializable data object Initial : Screen
  @Serializable data object Onboarding : Screen

  // Auth Flow
  @Serializable data object Auth : Screen
  @Serializable data object Recovery : Screen
  @Serializable data object Otp : Screen

  // Main Flow
  @Serializable data object Home : Screen
  @Serializable data object Food : Screen
  @Serializable data object Walk : Screen
  @Serializable data object Profile : Screen
  @Serializable data object Loading : Screen

  // Entries
  @Serializable data object AddActivityEntry : Screen
  @Serializable data object AddFoodEntry : Screen
  @Serializable data class EditFoodEntry(val id: String) : Screen

  // Wizard Pages
  @Serializable data object PageHeight : Screen
  @Serializable data object PageWeight : Screen
  @Serializable data object PageGoal : Screen
  @Serializable data object PageActivity : Screen
  @Serializable data object PageCongrats : Screen

}

@Serializable object InitialGraph
@Serializable object OnboardingGraph
@Serializable object AuthGraph
@Serializable object HomeGraph
@Serializable object WizardGraph
