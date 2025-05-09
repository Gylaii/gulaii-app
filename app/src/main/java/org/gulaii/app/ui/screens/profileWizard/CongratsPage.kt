package org.gulaii.app.ui.screens.profileWizard

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import org.gulaii.app.ui.composables.PillButton
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import org.gulaii.app.ui.theme.roseLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CongratsPage(
  vm: WizardVM = viewModel(),
  onDone: () -> Unit
) {
  val userRepo = org.gulaii.app.di.ServiceLocator.userRepo()
  val scope = rememberCoroutineScope()
  var loading by remember { mutableStateOf(false) }

  WizardScaffold("") {
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(roseLight)
        .padding(24.dp),
      contentAlignment = Alignment.Center
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(24.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Text(
          text = "Поздравляем!",
          style = MaterialTheme.typography.headlineMedium,
          color = MaterialTheme.colorScheme.onBackground,
          fontSize = 28.sp,
          textAlign = TextAlign.Center,
          modifier = Modifier.fillMaxWidth()
        )
        Text(
          text = "Ваш профиль успешно создан и готов к использованию.",
          style = MaterialTheme.typography.bodyLarge,
          color = MaterialTheme.colorScheme.onBackground,
          textAlign = TextAlign.Center,
          modifier = Modifier.fillMaxWidth()
        )

        if (loading) {
          CircularProgressIndicator(
            color = MaterialTheme.colorScheme.primary
          )
        } else {
          PillButton(
            modifier = Modifier
              .fillMaxWidth()
              .height(56.dp),
            isEnabled = true,
            buttonColor = ButtonDefaults.buttonColors(
              containerColor = MaterialTheme.colorScheme.primary,
              contentColor = MaterialTheme.colorScheme.onPrimary
            ),
            clickAction = {
              loading = true
              scope.launch {
                vm.commit()
                userRepo.fetchMetrics()
                onDone()
              }
            }
          ) {
            Text(
              text = "Начать работу",
              style = MaterialTheme.typography.titleMedium,
              fontSize = 18.sp
            )
          }
        }
      }
    }
  }
}
