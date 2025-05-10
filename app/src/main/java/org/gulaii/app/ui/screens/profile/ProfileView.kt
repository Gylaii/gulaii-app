package org.gulaii.app.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.gulaii.app.R
import org.gulaii.app.ui.composables.PillButton
import org.gulaii.app.ui.navigation.BottomNavBar
import org.gulaii.app.ui.navigation.Screen

private val avatars = listOf(
  R.drawable.avatar1, R.drawable.avatar2,
  R.drawable.avatar3, R.drawable.avatar4
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileView(
  nav: NavHostController = rememberNavController(),
  vm : ProfileViewModel   = viewModel(),
) {
  val ui               by vm.ui.collectAsState()
  val avatarRes        = remember { avatars.random() }
  val snackbarHost     = remember { SnackbarHostState() }
  val containerColor   = MaterialTheme.colorScheme.surfaceContainerLow

  Scaffold(
    snackbarHost = { SnackbarHost(snackbarHost) },
    bottomBar    = { BottomNavBar(nav, Screen.Profile) }
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
            vm.logout { nav.navigate(Screen.Auth) { popUpTo(0) } }
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
            isEnabled   = !ui.isLoading,
            modifier    = Modifier
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

@Composable
private fun ActionCardButton(
  icon: ImageVector,
  contentDescription: String?,
  onClick: () -> Unit
) = ElevatedCard(
  shape  = RoundedCornerShape(12.dp),
  colors = CardDefaults.elevatedCardColors(
    containerColor = MaterialTheme.colorScheme.surfaceContainerLow
  ),
  elevation = CardDefaults.elevatedCardElevation(defaultElevation = 3.dp),
  modifier = Modifier
    .size(48.dp)
    .clickable(onClick = onClick)
) {
  Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
    Icon(
      imageVector = icon,
      contentDescription = contentDescription,
      tint = MaterialTheme.colorScheme.onSurfaceVariant
    )
  }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GoalField(ui: ProfileUiState, vm: ProfileViewModel) {
  if (ui.isEditing) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
      expanded = expanded,
      onExpandedChange = { expanded = !expanded }
    ) {
      OutlinedTextField(
        value = ui.goal,
        onValueChange = {},
        readOnly = true,
        label = { Text("Цель") },
        placeholder = { Text("—") },
        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
        modifier = Modifier
          .fillMaxWidth()
          .menuAnchor()
      )
      ExposedDropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
      ) {
        listOf("Похудение", "Поддержание", "Набор").forEach { g ->
          DropdownMenuItem(
            text = { Text(g) },
            onClick = {
              vm.onGoal(g)
              expanded = false
            }
          )
        }
      }
    }
  } else {
    InfoCard("Цель", ui.goal)
  }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ActivityField(ui: ProfileUiState, vm: ProfileViewModel) {
  if (ui.isEditing) {
    var expanded by remember { mutableStateOf(false) }
    ExposedDropdownMenuBox(
      expanded = expanded,
      onExpandedChange = { expanded = !expanded }
    ) {
      OutlinedTextField(
        value = ui.activity,
        onValueChange = {},
        readOnly = true,
        label = { Text("Активность") },
        placeholder = { Text("—") },
        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded) },
        modifier = Modifier
          .fillMaxWidth()
          .menuAnchor()
      )
      ExposedDropdownMenu(
        expanded = expanded,
        onDismissRequest = { expanded = false }
      ) {
        listOf("Низкий", "Средний", "Высокий").forEach { a ->
          DropdownMenuItem(
            text = { Text(a) },
            onClick = {
              vm.onActivity(a)
              expanded = false
            }
          )
        }
      }
    }
  } else {
    InfoCard("Активность", ui.activity)
  }
}

@Composable
private fun InfoCard(label: String, value: String) = ElevatedCard(
  modifier = Modifier.fillMaxWidth(),
  colors = CardDefaults.elevatedCardColors()
) {
  Column(Modifier.padding(16.dp)) {
    Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    Spacer(Modifier.height(4.dp))
    Text(if (value.isBlank()) "—" else value, style = MaterialTheme.typography.bodyLarge)
  }
}
