package org.gulaii.app.ui.screens.profileWizard

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

  Scaffold(
    containerColor = roseLight,
    contentColor   = MaterialTheme.colorScheme.onBackground
  ) { innerPadding ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding)
        .padding(24.dp),
      verticalArrangement   = Arrangement.Center,
      horizontalAlignment   = Alignment.CenterHorizontally
    ) {
      Text(
        text      = "Поздравляем!",
        style     = MaterialTheme.typography.headlineMedium,
        fontSize  = 28.sp,
        textAlign = TextAlign.Center,
        modifier  = Modifier.fillMaxWidth()
      )
      Spacer(Modifier.height(8.dp))
      Text(
        text      = "Ваш профиль успешно создан и готов к использованию.",
        style     = MaterialTheme.typography.bodyLarge,
        textAlign = TextAlign.Center,
        modifier  = Modifier.fillMaxWidth()
      )
      Spacer(Modifier.height(32.dp))
      if (loading) {
        CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
      } else {
        PillButton(
          modifier    = Modifier
            .fillMaxWidth()
            .height(56.dp),
          isEnabled   = true,
          buttonColor = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor   = MaterialTheme.colorScheme.onPrimary
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
            text      = "Начать работу",
            style     = MaterialTheme.typography.titleMedium,
            fontSize  = 18.sp
          )
        }
      }
    }
  }
}
