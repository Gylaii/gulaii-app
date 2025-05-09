package org.gulaii.app.ui.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import org.gulaii.app.R
import org.gulaii.app.ui.composables.CustomTextField
import org.gulaii.app.ui.composables.PillButton
import org.gulaii.app.ui.navigation.Screen

private val avatars = listOf(
  R.drawable.avatar1, R.drawable.avatar2,
  R.drawable.avatar3, R.drawable.avatar4
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileView(
  nav: NavHostController = rememberNavController(),
  vm : ProfileViewModel  = viewModel(),
) {
  val ui by vm.ui.collectAsState()
  val avatar = remember { avatars.random() }
  val snackbar = remember { SnackbarHostState() }

  Scaffold(
    snackbarHost = { SnackbarHost(snackbar) }
  ) { pad ->
    Box(Modifier
      .padding(pad)
      .fillMaxSize()) {

      /* ---------- верхние круглые кнопки ---------- */
      Row(
        Modifier
          .fillMaxWidth()
          .padding(16.dp),
        horizontalArrangement = Arrangement.SpaceBetween
      ) {

        OutlinedButton(
          onClick = { vm.toggleEditing() },
          modifier = Modifier.size(48.dp),
          shape = CircleShape,
          border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurfaceVariant),
          contentPadding = PaddingValues(12.dp),
          colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent)
        ) {
          Icon(
            painter = painterResource(R.drawable.ic_edit),
            contentDescription = "Edit",
            modifier = Modifier.fillMaxSize()
          )
        }

        OutlinedButton(
          onClick = {
            vm.logout {
              nav.navigate(Screen.Auth) {
                popUpTo(0)
              }
            }
          },
          modifier = Modifier.size(48.dp),
          shape = CircleShape,
          border = BorderStroke(1.dp, MaterialTheme.colorScheme.onSurfaceVariant),
          contentPadding = PaddingValues(12.dp),
          colors = ButtonDefaults.outlinedButtonColors(containerColor = Color.Transparent)
        ) {
          Icon(
            painter = painterResource(R.drawable.ic_exit),
            contentDescription = "Logout",
            modifier = Modifier.fillMaxSize()
          )
        }
      }

      /* ---------- сам контент ---------- */
      Column(
        Modifier
          .padding(top = 90.dp, start = 24.dp, end = 24.dp)
          .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {

        Image(
          painter = painterResource(avatar),
          contentDescription = "avatar",
          modifier = Modifier
            .size(120.dp)
            .clip(CircleShape)
        )

        Spacer(Modifier.height(32.dp))

        CustomTextField(
          label = "Height (cm)",
          value = ui.height,
          onValueChange = vm::onHeight,
          enabled = ui.isEditing,
          keyboardOptions = KeyboardOptions.Default
            .copy(keyboardType = KeyboardType.Number)
        )

        Spacer(Modifier.height(16.dp))

        CustomTextField(
          label = "Weight (kg)",
          value = ui.weight,
          onValueChange = vm::onWeight,
          enabled = ui.isEditing,
          keyboardOptions = KeyboardOptions.Default
            .copy(keyboardType = KeyboardType.Number)
        )

        Spacer(Modifier.height(16.dp))

        if (ui.isEditing) {
          var expanded by remember { mutableStateOf(false) }
          ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
          ) {
            TextField(
              value = ui.goal,
              onValueChange = {},
              readOnly = true,
              label = { Text("Goal") },
              trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
              },
              modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
              expanded = expanded,
              onDismissRequest = { expanded = false }
            ) {
              listOf("похудение", "поддержание", "набор")
                .forEach { g ->
                  DropdownMenuItem(
                    text = { Text(g) },
                    onClick = {
                      vm.onGoal(g); expanded = false
                    }
                  )
                }
            }
          }
        } else {
          Text(ui.goal.ifBlank { "—" })
        }

        Spacer(Modifier.height(16.dp))

        if (ui.isEditing) {
          var expanded by remember { mutableStateOf(false) }
          ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = { expanded = !expanded }
          ) {
            TextField(
              value = ui.activity,
              onValueChange = {},
              readOnly = true,
              label = { Text("Activity") },
              trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
              },
              modifier = Modifier.menuAnchor()
            )
            ExposedDropdownMenu(
              expanded = expanded,
              onDismissRequest = { expanded = false }
            ) {
              listOf("низкий", "средний", "высокий")
                .forEach { a ->
                  DropdownMenuItem(
                    text = { Text(a) },
                    onClick = {
                      vm.onActivity(a); expanded = false
                    }
                  )
                }
            }
          }
        } else {
          Text(ui.activity.ifBlank { "—" })
        }

        Spacer(Modifier.height(32.dp))

        if (ui.isEditing) {
          PillButton(
            isEnabled  = !ui.isLoading,
            buttonColor = ButtonDefaults.buttonColors(),
            clickAction = { vm.save{} },
            modifier = Modifier.width(200.dp).height(50.dp)
          ) {
            if (ui.isLoading)
              CircularProgressIndicator(strokeWidth = 2.dp)
            else
              Text("Save")
          }
        }
      }

      ui.error?.let { msg ->
        LaunchedEffect(msg) {
          snackbar.showSnackbar(msg)
          vm.onGoal("")
        }
      }
    }
  }
}
