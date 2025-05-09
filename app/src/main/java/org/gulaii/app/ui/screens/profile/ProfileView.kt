package org.gulaii.app.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
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
  val coroutineScope   = rememberCoroutineScope()

  Scaffold(
    snackbarHost = { SnackbarHost(snackbarHost) },
    bottomBar    = { BottomNavBar(nav, Screen.Profile) }
  ) { pad ->

    /** ---------- «плавающие» квадратные кнопки ---------- **/
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
        FilledIconButton(
          onClick = { vm.toggleEditing() },
          colors  = IconButtonDefaults.filledIconButtonColors(containerColor),
          shape   = RoundedCornerShape(8.dp),
          modifier = Modifier.size(44.dp)
        ) { Icon(Icons.Default.Edit, null) }

        FilledIconButton(
          onClick = {
            vm.logout { nav.navigate(Screen.Auth) { popUpTo(0) } }
          },
          colors  = IconButtonDefaults.filledIconButtonColors(containerColor),
          shape   = RoundedCornerShape(8.dp),
          modifier = Modifier.size(44.dp)
        ) { Icon(Icons.Default.ExitToApp, null) }
      }

      /** ---------- основной контент ---------- **/
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(top = 72.dp, start = 24.dp, end = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        // Аватар
        Image(
          painter = painterResource(avatarRes),
          contentDescription = null,
          modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
            .border(2.dp, Color.White, CircleShape)
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

    /** ---------- ошибки ---------- **/
    ui.error?.let { msg ->
      LaunchedEffect(msg) {
        snackbarHost.showSnackbar(msg)
      }
    }
  }
}

/* -------------------------------------------------------------------------- */
/*                        Доп. поля: Goal / Activity                          */
/* -------------------------------------------------------------------------- */

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

/* Card‑обёртка для чтения */
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
