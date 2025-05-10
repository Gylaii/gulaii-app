package org.gulaii.app.ui.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.gulaii.app.ui.screens.authScreen.AuthScreenView
import org.gulaii.app.ui.screens.initialScreen.InitialScreenView
import org.gulaii.app.ui.screens.onboardingScreen.OnboardingView
import org.gulaii.app.ui.screens.otpScreen.OtpView
import org.gulaii.app.ui.screens.recoveryScreen.RecoveryView

fun NavGraphBuilder.registerInitialGraph(nav: NavHostController) {
  navigation<InitialGraph>(startDestination = Screen.Initial) {
    composable<Screen.Initial> {
      InitialScreenView(onNext = { nav.navigate(OnboardingGraph) })
    }
  }
}

fun NavGraphBuilder.registerOnboardingGraph(nav: NavHostController) {
  navigation<OnboardingGraph>(startDestination = Screen.Onboarding) {
    composable<Screen.Onboarding> {
      OnboardingView(onFinished = { nav.navigate(Screen.Auth) })
    }
  }
}

fun NavGraphBuilder.registerAuthGraph(nav: NavHostController) {
  navigation<AuthGraph>(startDestination = Screen.Auth) {
    composable<Screen.Auth> {
      AuthScreenView(
        onForgotPasswordClick = { /* TODO */ },
        onAuthResult = { needWizard ->
          if (needWizard) {
            nav.navigate(WizardGraph) {
              popUpTo(AuthGraph) { inclusive = true }
            }
          } else {
            nav.navigate(HomeGraph) {
              popUpTo(AuthGraph) { inclusive = true }
            }
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
//
//fun NavGraphBuilder.registerHomeGraph(nav: NavHostController) {
//  navigation<HomeGraph>(startDestination = Screen.Home) {
//    composable<Screen.Home> {
//      FoodView(onNavigateToFood = { nav.navigate(Screen.Food) })
//    }
//    composable<Screen.Food> { FoodView(nav) }
//    composable<Screen.AddFoodEntry> {
//      FoodEntryScreen(
//        nav = nav,
//        onSaved = { nav.popBackStack(Screen.Food, false) }
//      )
//    }
//    composable(
//      route = "edit_food/{id}",
//      arguments = listOf(navArgument("id") { type = NavType.StringType })
//    ) { backStackEntry ->
//      val id = backStackEntry.arguments?.getString("id") ?: return@composable
//      val repo = ServiceLocator.foodRepo()
//      val origin = repo.find(id) ?: return@composable
//      val vm: EditFoodVM = viewModel(factory = EditFoodVM.factory(origin, repo))
//
//      FoodEntryScreen(
//        nav = nav,
//        onSaved = { nav.popBackStack() },
//        vm = vm
//      )
//    }
//  }
//}
