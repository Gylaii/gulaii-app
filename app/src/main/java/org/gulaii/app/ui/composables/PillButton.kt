package org.gulaii.app.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ElevatedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.gulaii.app.ui.theme.GulaiiTheme


@Composable
fun PillButton(modifier: Modifier = Modifier, isEnabled: Boolean, buttonText: String, clickAction: () -> Unit) {
    ElevatedButton(
        onClick = clickAction,
        modifier = modifier.padding(16.dp),
        enabled = isEnabled,
    ){
        Text(text = buttonText)
    }
}


@Preview
@Composable
fun PillButtonPreview(){
    GulaiiTheme {
        PillButton(
            isEnabled = true,
            buttonText = "vova",
            clickAction = { }
        )
    }
}