package org.gulaii.app.ui.screens.profileWizard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.ColumnScope

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WizardScaffold(
  title: String,
  content: @Composable ColumnScope.() -> Unit
) {
  Scaffold(
    topBar = { CenterAlignedTopAppBar(title = { Text(title) }) }
  ) { pad ->
    Column(Modifier.padding(pad).padding(24.dp)) {
      content()
    }
  }
}
