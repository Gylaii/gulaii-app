package org.gulaii.app.ui.screens.profileWizard

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import org.gulaii.app.ui.composables.PillButton

@Composable
fun CongratsPage(
  vm: WizardVM = viewModel(),
  onDone: () -> Unit
) {
  val userRepo = org.gulaii.app.di.ServiceLocator.userRepo()
  val scope = rememberCoroutineScope()
  var loading by remember { mutableStateOf(false) }

  WizardScaffold("Поздравляем!") {
    if (loading) {
      CircularProgressIndicator()
    } else {
      Text("Ваш профиль заполнен 🎉")
      Spacer(Modifier.height(32.dp))
      PillButton(
        isEnabled = true,
        buttonColor = ButtonDefaults.buttonColors(),
        modifier = Modifier.fillMaxWidth(),
        clickAction = {
          loading = true
          scope.launch {
            vm.commit()
            userRepo.fetchMetrics()
            onDone()
          }
        }
      ) { Text("Продолжить") }
    }
  }
}
