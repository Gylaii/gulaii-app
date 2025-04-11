package org.gulaii.app.ui.screens.initialScreen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.gulaii.app.R
import org.gulaii.app.ui.composables.PillButton
import org.gulaii.app.ui.theme.GulaiiTheme

object InitialScreenRoute {
    const val route = "initial_screen"
}

@Composable
fun InitialScreenView(
    modifier: Modifier = Modifier,
    viewModel: InitialScreenViewModel = viewModel(),
    onNext: () -> Unit = {}
) {
    val headerText by viewModel.headerText.collectAsState()

    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Column(
                modifier = Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = headerText,
                    fontSize = 24.sp
                )
                Text(text = stringResource(R.string.initial_screen_secondary_header))
            }

            PillButton(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .padding(bottom = 64.dp)
                    .height(60.dp)
                    .width(200.dp),
                isEnabled = true,
                buttonColor = ButtonDefaults.buttonColors(colorResource(R.color.purple_500)),
                clickAction = {
                    viewModel.onButtonClick()
                // onNext()
                }
            ) {
                Text(
                    text = "Welcome!",
                    color = Color.White,
                    style = MaterialTheme.typography.bodyMedium,
                    fontSize = 24.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InitialScreenViewPreview() {
    GulaiiTheme {
        InitialScreenView()
    }
}
