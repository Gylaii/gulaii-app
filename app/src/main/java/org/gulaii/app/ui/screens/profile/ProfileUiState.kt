package org.gulaii.app.ui.screens.profile

data class ProfileUiState(
  val email: String = "",
  val height: String = "",
  val weight: String = "",
  val goal: String = "",
  val activity: String = "",
  val isLoading: Boolean = false,
  val saved: Boolean = false,
  val error: String? = null,
  val isEditing: Boolean = false
)
