package org.gulaii.app.ui.composables

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.gulaii.app.ui.theme.GulaiiTheme
import org.gulaii.app.ui.theme.Typography

@Composable
fun PillButton(
    modifier: Modifier = Modifier,
    isEnabled: Boolean,
    buttonColor: ButtonColors,
    clickAction: () -> Unit,
    content: @Composable () -> Unit
) {
    ElevatedButton(
        onClick = clickAction,
        modifier = modifier,
        enabled = isEnabled,
        colors = buttonColor,
    ) {
        content()
    }
}


@Preview(showBackground = true)
@Composable
fun PillButtonPreview() {
    GulaiiTheme {
        PillButton(
            isEnabled = true,
            clickAction = { },
            buttonColor = ButtonDefaults.buttonColors(containerColor = Color.Blue),
            modifier = Modifier
                .padding(16.dp)
                .width(200.dp)
                .height(56.dp),
            content = {
                Text(
                    text = "vova",
                    style = Typography.bodyLarge.copy(color = Color.White)
                )
            }
        )
    }
}
