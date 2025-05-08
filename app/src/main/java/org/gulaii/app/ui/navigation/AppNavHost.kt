package org.gulaii.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.gulaii.app.ui.screens.authScreen.AuthScreenView
import org.gulaii.app.ui.screens.initialScreen.InitialScreenView
import org.gulaii.app.ui.screens.onboardingScreen.OnboardingView
import org.gulaii.app.ui.screens.otpScreen.OtpView
import org.gulaii.app.ui.screens.profile.ProfileView
import org.gulaii.app.ui.screens.recoveryScreen.RecoveryView

@Composable
fun AppNavHost(
  navController: NavHostController,
  modifier: Modifier = Modifier,
) {
  NavHost(
    navController = navController,
    startDestination = InitialGraph,
    modifier = modifier
  ) {
    //Initial Navigation Graph
    navigation<InitialGraph>(startDestination = Screen.Initial) {
      composable<Screen.Initial> {
        InitialScreenView(
          onNext = { navController.navigate(OnboardingGraph) }
        )
      }
    }

    //Onboarding Navigation Graph
    navigation<OnboardingGraph>(startDestination = Screen.Onboarding) {
      composable<Screen.Onboarding> {
        OnboardingView(
          onFinished = { navController.navigate(Screen.Auth) }
        )
      }
    }

    //Auth Navigation Graph
    navigation<AuthGraph>(startDestination = Screen.Auth) {
      composable<Screen.Auth> {
        AuthScreenView(
          onForgotPasswordClick = { navController.navigate(Screen.Recovery) },
          onAuthSuccess = { navController.navigate(Screen.Profile) { popUpTo(AuthGraph) { inclusive = true } } }
        )
      }
      composable<Screen.Recovery> {
        RecoveryView(
          onReturnClick = { navController.navigate(Screen.Auth) },
          onNextClick = { navController.navigate(Screen.Otp) }
        )
      }
      composable<Screen.Otp> {
        OtpView(
          onVerifyClick = { navController.navigate(Screen.Auth) }
        )
      }
    }

    // Профиль
    composable<Screen.Profile> {
      ProfileView(
        nav = navController
      )
    }
  }
}
