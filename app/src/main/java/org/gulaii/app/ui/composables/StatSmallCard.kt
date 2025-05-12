package org.gulaii.app.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


@Composable
fun StatSmallCard(
  value: String,
  label: String
) = OutlinedCard(
  modifier = Modifier
    .fillMaxWidth()
    .height(79.dp),
  shape  = RoundedCornerShape(16.dp),
  border = CardDefaults.outlinedCardBorder().copy(width = 1.dp)
) {
  Column(
    Modifier
      .fillMaxSize()
      .padding(16.dp),
    verticalArrangement = Arrangement.Center,
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Text(
      text = value,
      style = MaterialTheme.typography.bodyLarge,
      textAlign = TextAlign.Center,
      fontSize = 20.sp
    )

    Text(
      text = label,
      style = MaterialTheme.typography.bodyLarge,
      textAlign = TextAlign.Center,
      fontSize = 20.sp
    )
  }
}
