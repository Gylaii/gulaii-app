package org.gulaii.app.ui.navigation

import android.annotation.SuppressLint
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable
import org.gulaii.app.ui.screens.profileWizard.*
import androidx.compose.runtime.remember
import androidx.navigation.compose.navigation


@Serializable object WizardGraph
@Serializable object PageHeight
@Serializable object PageWeight
@Serializable object PageGoal
@Serializable object PageActivity
@Serializable object PageCongrats

@SuppressLint("UnrememberedGetBackStackEntry")
fun NavGraphBuilder.profileWizardGraph(
  navController: NavHostController,
  onFinished: () -> Unit
) {
  navigation<WizardGraph>(startDestination = PageHeight) {

    composable<PageHeight> {
      val parentEntry = remember {
        navController.getBackStackEntry(WizardGraph)
      }
      val vm: WizardVM = viewModel(parentEntry)

      HeightPage(vm) { navController.navigate(PageWeight) }
    }

    composable<PageWeight> {
      val parentEntry = remember {
        navController.getBackStackEntry(WizardGraph)
      }
      val vm: WizardVM = viewModel(parentEntry)

      WeightPage(vm) { navController.navigate(PageGoal) }
    }

    composable<PageGoal> {
      val parentEntry = remember {
        navController.getBackStackEntry(WizardGraph)
      }
      val vm: WizardVM = viewModel(parentEntry)

      GoalPage(vm) { navController.navigate(PageActivity) }
    }

    composable<PageActivity> {
      val parentEntry = remember {
        navController.getBackStackEntry(WizardGraph)
      }
      val vm: WizardVM = viewModel(parentEntry)

      ActivityPage(vm) { navController.navigate(PageCongrats) }
    }

    composable<PageCongrats> {
      val parentEntry = remember {
        navController.getBackStackEntry(WizardGraph)
      }
      val vm: WizardVM = viewModel(parentEntry)

      CongratsPage(vm) { onFinished() }
    }
  }
}
