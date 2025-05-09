package org.gulaii.app.ui.screens.profileWizard

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import org.gulaii.app.di.ServiceLocator

class WizardVM : ViewModel() {

  private val _height   = MutableStateFlow("")
  private val _weight   = MutableStateFlow("")
  private val _goal     = MutableStateFlow("")
  private val _activity = MutableStateFlow("")
  private val userRepo = ServiceLocator.userRepo()

  val height  : StateFlow<String> = _height
  val weight  : StateFlow<String> = _weight
  val goal    : StateFlow<String> = _goal
  val activity: StateFlow<String> = _activity

  suspend fun commit() {
    userRepo.setMetrics(buildMetrics())
  }

  fun setHeight(v: String)   = _height.update { v }
  fun setWeight(v: String)   = _weight.update { v }
  fun setGoal(v: String)     = _goal.update { v }
  fun setActivity(v: String) = _activity.update { v }

  fun buildMetrics() = org.gulaii.app.data.model.ProfileMetrics(
    height        = height.value.toIntOrNull(),
    weight        = weight.value.toIntOrNull(),
    goal          = goal.value,
    activityLevel = activity.value
  )
}
