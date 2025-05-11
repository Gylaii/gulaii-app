package org.gulaii.app.ui.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.gulaii.app.ui.navigation.AuthGraph
import org.gulaii.app.ui.navigation.HomeGraph
import org.gulaii.app.ui.navigation.Screen
import org.gulaii.app.ui.navigation.WizardGraph
import org.gulaii.app.ui.screens.authScreen.AuthScreenView
import org.gulaii.app.ui.screens.recoveryScreen.RecoveryView
import org.gulaii.app.ui.screens.otpScreen.OtpView

fun NavGraphBuilder.registerAuthGraph(nav: NavHostController) {
  navigation<AuthGraph>(startDestination = Screen.Auth) {
    composable<Screen.Auth> {
      AuthScreenView(
        onForgotPasswordClick = { nav.navigate(Screen.Recovery) },
        onAuthResult = { needWizard ->
          if (needWizard) nav.navigate(WizardGraph) {
            popUpTo(AuthGraph) { inclusive = true }
          } else nav.navigate(HomeGraph) {
            popUpTo(AuthGraph) { inclusive = true }
          }
        }
      )
    }
    composable<Screen.Recovery> {
      RecoveryView(
        onReturnClick = { nav.navigate(Screen.Auth) },
        onNextClick = { nav.navigate(Screen.Otp) }
      )
    }
    composable<Screen.Otp> {
      OtpView(
        onVerifyClick = {
          nav.navigate(Screen.Auth) {
            popUpTo(AuthGraph) { inclusive = true }
          }
        }
      )
    }
  }
}
