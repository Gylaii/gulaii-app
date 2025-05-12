package org.gulaii.app.ui.composables

import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import org.gulaii.app.ui.theme.roseLight

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodayHeader() {
  CenterAlignedTopAppBar(
    title = { Text("Ваши данные за сегодня") },
    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
      containerColor   = roseLight,
      titleContentColor = MaterialTheme.colorScheme.onPrimary
    ),
    scrollBehavior = null
  )
}


