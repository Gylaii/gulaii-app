package org.gulaii.app.ui.screens.authScreen

import AuthMode
import AuthScreenViewModel
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import org.gulaii.app.R
import org.gulaii.app.ui.composables.CustomTextField
import org.gulaii.app.ui.composables.PillButton
import org.gulaii.app.ui.theme.GulaiiTheme

@Composable
fun AuthScreenView(
    modifier: Modifier = Modifier,
    viewModel: AuthScreenViewModel = viewModel(),
    onForgotPasswordClick: () -> Unit = {},
) {
    val uiState by viewModel.uiState.collectAsState()
    var passwordVisible by rememberSaveable { mutableStateOf(false) }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = modifier.padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AnimatedContent(
                targetState = uiState.mode,
                transitionSpec = { fadeIn() togetherWith fadeOut() }
            ) { mode ->
                if (mode == AuthMode.SignIn) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Welcome \nto",
                            style = MaterialTheme.typography.headlineMedium,
                            fontSize = 34.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(top = 50.dp),
                            textAlign = TextAlign.Center,
                            lineHeight = 34.sp
                        )
                        Text(
                            text = "Gulaii",
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 48.sp,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 20.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Create \naccount",
                            style = MaterialTheme.typography.headlineMedium,
                            fontSize = 34.sp,
                            color = MaterialTheme.colorScheme.onBackground,
                            modifier = Modifier.padding(top = 50.dp),
                            textAlign = TextAlign.Center,
                            lineHeight = 34.sp
                        )
                        Text(
                            text = "Gulaii",
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 48.sp,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(top = 20.dp),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            CustomTextField(
                label = "Email",
                value = uiState.email,
                onValueChange = viewModel::onEmailChange,
                modifier = Modifier.padding(top = 50.dp),
                leadingIcon = painterResource(R.drawable.outline_email_24),   // <-- just Painter
                onTrailingIconClick = { },
                trailingIcon = null,
                cornerRadius = 12.dp,
            )

            Spacer(Modifier.height(20.dp))

            CustomTextField(
                label = "Password",
                value = uiState.password,
                onValueChange = viewModel::onPasswordChange,
                modifier = Modifier,
                leadingIcon = painterResource(R.drawable.outline_lock_24),
                onTrailingIconClick = { passwordVisible = !passwordVisible },
                trailingIcon = painterResource(
                    if (passwordVisible)
                        R.drawable.outline_remove_red_eye_24
                    else
                        R.drawable.outline_visibility_off_24
                ),
                visualTransformation = if (passwordVisible) {
                    VisualTransformation.None
                } else
                    PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                cornerRadius = 12.dp
            )

            Spacer(Modifier.height(25.dp))

            Text(
                text = "Forgot your password?",
                style = MaterialTheme.typography.bodySmall,
                textDecoration = TextDecoration.Underline,
                fontSize = 16.sp,
                modifier = Modifier
                    .clickable(role = Role.Button) {
                        onForgotPasswordClick()
                    },
                color = MaterialTheme.colorScheme.secondary
            )

            Spacer(Modifier.height(170.dp))

            PillButton(
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp),
                isEnabled = true,
                buttonColor = ButtonDefaults.buttonColors(MaterialTheme.colorScheme.primaryContainer),
                clickAction = {
                    viewModel.onPrimaryButtonClick()
                }
            ) {
                Text(
                    text = uiState.primaryButtonText,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 24.sp
                )
            }

            Spacer(Modifier.height(10.dp))

            Text(
                text = "or",
                modifier = Modifier,
                style = MaterialTheme.typography.bodySmall,
                fontSize = 20.sp
            )

            Spacer(Modifier.height(10.dp))

            PillButton(
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp),
                isEnabled = true,
                buttonColor = ButtonDefaults.buttonColors(),
                clickAction = { }
            ) {
                Text(
                    text = "Google",
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier,
                    style = MaterialTheme.typography.bodyLarge,
                    fontSize = 24.sp
                )
            }

            Spacer(Modifier.height(50.dp))

            Text(
                text = if (uiState.mode == AuthMode.SignIn)
                    "Need a new account?" else "Already have an account?",
                style = MaterialTheme.typography.bodySmall,
                textDecoration = TextDecoration.Underline,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.secondary,
                modifier = Modifier
                    .clickable { viewModel.toggleMode() }
            )
        }
    }
}

@Preview
@Composable
fun AuthScreenViewPreview() {
    GulaiiTheme {
        AuthScreenView()
    }
}
