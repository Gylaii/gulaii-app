package org.gulaii.app.ui.screens.profileSetupScreen

data class ProfileSetupUiState(
  val height: String = "",
  val weight: String = "",
  val goal: String = "",
  val activity: String = "",
  val isSubmitting: Boolean = false
) {
  val canSubmit: Boolean
    get() = height.isNotBlank() && weight.isNotBlank()
}
