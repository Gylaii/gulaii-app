package org.gulaii.app.ui.navigation.graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.lifecycle.viewmodel.compose.viewModel
import org.gulaii.app.ui.navigation.Screen
import org.gulaii.app.ui.navigation.WizardGraph
import org.gulaii.app.ui.screens.profileWizard.*

fun NavGraphBuilder.registerWizardGraph(
  navController: NavHostController,
  onFinished: () -> Unit
) {
  navigation<WizardGraph>(startDestination = Screen.PageHeight) {
    composable<Screen.PageHeight> {
      val vm: WizardVM = viewModel()
      HeightPage(vm = vm) {
        navController.navigate(Screen.PageWeight)
      }
    }

    composable<Screen.PageWeight> {
      val vm: WizardVM = viewModel()
      WeightPage(vm = vm) {
        navController.navigate(Screen.PageGoal)
      }
    }

    composable<Screen.PageGoal> {
      val vm: WizardVM = viewModel()
      GoalPage(vm = vm) {
        navController.navigate(Screen.PageActivity)
      }
    }

    composable<Screen.PageActivity> {
      val vm: WizardVM = viewModel()
      ActivityPage(vm = vm) {
        navController.navigate(Screen.PageCongrats)
      }
    }

    composable<Screen.PageCongrats> {
      val vm: WizardVM = viewModel()
      CongratsPage(vm = vm, onDone = onFinished)
    }
  }
}
