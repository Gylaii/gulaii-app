package org.gulaii.app.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.gulaii.app.R
import org.gulaii.app.ui.composables.ActionCardButton
import org.gulaii.app.ui.composables.ActivityField
import org.gulaii.app.ui.composables.BottomNavBar
import org.gulaii.app.ui.composables.GoalField
import org.gulaii.app.ui.composables.PillButton
import org.gulaii.app.ui.navigation.Screen

private val avatars = listOf(
  R.drawable.avatar1, R.drawable.avatar2,
  R.drawable.avatar3, R.drawable.avatar4
)

@Composable
fun ProfileView(
  onNavigate: (Screen) -> Unit,
  vm: ProfileViewModel = viewModel(),
) {
  val ui by vm.ui.collectAsState()
  val avatarRes = remember { avatars.random() }
  val snackbarHost = remember { SnackbarHostState() }
  val containerColor = MaterialTheme.colorScheme.surfaceContainerLow

  Scaffold(
    snackbarHost = { SnackbarHost(snackbarHost) },
    bottomBar = {
      BottomNavBar(
        current = Screen.Profile,
        onNavigate = onNavigate
      )
    }
  ) { pad ->

    Box(
      modifier = Modifier
        .fillMaxSize()
        .padding(pad)
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 16.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {
        ActionCardButton(
          icon = Icons.Default.Edit,
          contentDescription = "Редактировать",
          onClick = { vm.toggleEditing() }
        )

        ActionCardButton(
          icon = Icons.Default.ExitToApp,
          contentDescription = "Выйти",
          onClick = {
            vm.logout {
              onNavigate(Screen.Auth)
            }
          }
        )
      }

      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(top = 72.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Image(
          painter = painterResource(avatarRes),
          contentDescription = null,
          modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .border(2.dp, Color.White, CircleShape)
        )

        Spacer(Modifier.height(12.dp))

        Text(
          text = ui.email.ifBlank { "—" },
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          textAlign = TextAlign.Center,
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(24.dp))

        OutlinedTextField(
          value = ui.height,
          onValueChange = vm::onHeight,
          label = { Text("Рост (см)") },
          placeholder = { Text("—") },
          singleLine = true,
          enabled = ui.isEditing,
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        OutlinedTextField(
          value = ui.weight,
          onValueChange = vm::onWeight,
          label = { Text("Вес (кг)") },
          placeholder = { Text("—") },
          singleLine = true,
          enabled = ui.isEditing,
          keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
          modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(16.dp))

        GoalField(ui, vm)

        Spacer(Modifier.height(16.dp))

        ActivityField(ui, vm)

        Spacer(Modifier.height(32.dp))

        if (ui.isEditing) {
          PillButton(
            isEnabled = !ui.isLoading,
            modifier = Modifier
              .fillMaxWidth()
              .height(52.dp),
            buttonColor = ButtonDefaults.buttonColors(
              containerColor = MaterialTheme.colorScheme.primaryContainer
            ),
            clickAction = { vm.save { } }
          ) {
            if (ui.isLoading)
              CircularProgressIndicator(strokeWidth = 2.dp)
            else
              Text("Сохранить")
          }
        }
      }
    }

    ui.error?.let { msg ->
      LaunchedEffect(msg) {
        snackbarHost.showSnackbar(msg)
      }
    }
  }
}
