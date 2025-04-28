package org.gulaii.app.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import org.gulaii.app.ui.theme.GulaiiTheme

@Composable
fun CustomTextField(
  label: String,
  value: String,
  onValueChange: (String) -> Unit,
  modifier: Modifier = Modifier,
  leadingIcon: Painter? = null,
  onTrailingIconClick: () -> Unit = {},
  trailingIcon: Painter? = null,
  cornerRadius: Dp = 12.dp
) {
  Column(modifier = modifier) {
    OutlinedTextField(
      value = value,
      label = { Text(label) },
      placeholder = { Text(value) },
      onValueChange = onValueChange,
      maxLines = 1,
      leadingIcon = leadingIcon?.let {
        @Composable {
          Icon(
            modifier = Modifier.size(20.dp),
            painter = leadingIcon,
            contentDescription = null
          )
        }
      },
      trailingIcon = {
        if (trailingIcon != null)
          IconButton(onClick = onTrailingIconClick) {
            Icon(
              modifier = Modifier.size(20.dp),
              painter = trailingIcon, contentDescription = null
            )
          }
      },
      shape = RoundedCornerShape(cornerRadius),
      modifier = Modifier.fillMaxWidth()
    )
  }
}

@Preview
@Composable
fun CustomTextFieldPreview() {
  var text by remember { mutableStateOf("Test") }

  GulaiiTheme {
    CustomTextField(
      label = "label",
      value = "",
      onValueChange = { text = it },
      modifier = Modifier.padding(20.dp)
    )
  }
}
