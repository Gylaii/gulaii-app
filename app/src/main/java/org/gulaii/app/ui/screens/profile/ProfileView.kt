package org.gulaii.app.ui.screens.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.gulaii.app.R
import org.gulaii.app.ui.composables.CustomTextField
import org.gulaii.app.ui.composables.PillButton

private val avatars = listOf(
  R.drawable.avatar1, R.drawable.avatar2,
  R.drawable.avatar3, R.drawable.avatar4
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileView(
  modifier: Modifier = Modifier,
  vm: ProfileViewModel = viewModel(),
  onSaved: () -> Unit = {}
) {
  val ui by vm.ui.collectAsState()
  val avatar = remember { avatars.random() }

  Surface(modifier.fillMaxSize()) {
    Column(
      modifier.padding(24.dp),
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      // --- аватар --------------------------------------------------
      Image(
        painter = painterResource(avatar),
        contentDescription = "avatar",
        modifier = Modifier
          .size(120.dp)
          .clip(CircleShape)
      )

      Spacer(Modifier.height(32.dp))

      // --- поля ----------------------------------------------------
      CustomTextField(
        label = "Height (cm)",
        value = ui.height,
        onValueChange = vm::onHeight,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
      )
      Spacer(Modifier.height(16.dp))
      CustomTextField(
        label = "Weight (kg)",
        value = ui.weight,
        onValueChange = vm::onWeight,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = androidx.compose.ui.text.input.KeyboardType.Number)
      )
      Spacer(Modifier.height(16.dp))

      // Goal dropdown
      var goalExpanded by remember { mutableStateOf(false) }
      ExposedDropdownMenuBox(expanded = goalExpanded, onExpandedChange = { goalExpanded = !goalExpanded }) {
        TextField(
          value = ui.goal,
          onValueChange = {},
          readOnly = true,
          label = { Text("Goal") },
          trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(goalExpanded) },
          modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(expanded = goalExpanded, onDismissRequest = { goalExpanded = false }) {
          listOf("похудение", "поддержание", "набор").forEach { g ->
            DropdownMenuItem(text = { Text(g) }, onClick = {
              vm.onGoal(g); goalExpanded = false
            })
          }
        }
      }

      Spacer(Modifier.height(16.dp))

      // Activity dropdown
      var actExpanded by remember { mutableStateOf(false) }
      ExposedDropdownMenuBox(expanded = actExpanded, onExpandedChange = { actExpanded = !actExpanded }) {
        TextField(
          value = ui.activity,
          onValueChange = {},
          readOnly = true,
          label = { Text("Activity") },
          trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(actExpanded) },
          modifier = Modifier.menuAnchor()
        )
        ExposedDropdownMenu(expanded = actExpanded, onDismissRequest = { actExpanded = false }) {
          listOf("низкий", "средний", "высокий").forEach { a ->
            DropdownMenuItem(text = { Text(a) }, onClick = {
              vm.onActivity(a); actExpanded = false
            })
          }
        }
      }

      Spacer(Modifier.height(32.dp))

      PillButton(
        isEnabled = !ui.isLoading,
        buttonColor = ButtonDefaults.buttonColors(),
        clickAction = { vm.save(onSaved) },
        modifier = Modifier.width(200.dp).height(50.dp)
      ) {
        if (ui.isLoading) CircularProgressIndicator(strokeWidth = 2.dp)
        else Text("Save")
      }
    }
  }
}
