package org.gulaii.app.ui.screens.onboardingScreen

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


data class OnboardingPage(
  val header: String,
  val body: String
)

class OnboardingViewModel : ViewModel() {

  private val _headerText = MutableStateFlow("Gulaii")
  val headerText = _headerText.asStateFlow()


  private val pages = listOf(
    OnboardingPage("Добро пожаловать в Gulaii", "Отслеживайте свои цели и прогресс каждый день!"),
    OnboardingPage("Аналитика в реальном времени", "Получайте мгновенную статистику по своим результатам."),
    OnboardingPage("Персонализированные советы", "Индивидуальные рекомендации для быстрого роста."),
  )

  private val _index = MutableStateFlow(0)
  private val _currentPage = MutableStateFlow(pages[0])
  val currentPage: StateFlow<OnboardingPage> = _currentPage.asStateFlow()

  fun onButtonClick(): Boolean {
    return if (_index.value < pages.lastIndex) {
      _index.value += 1
      _currentPage.value = pages[_index.value]
      false
    } else {
      true
    }
  }
}
