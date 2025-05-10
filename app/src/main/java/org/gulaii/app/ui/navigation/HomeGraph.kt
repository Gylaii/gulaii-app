package org.gulaii.app.ui.navigation

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.gulaii.app.di.ServiceLocator
import org.gulaii.app.ui.screens.food.FoodView
import org.gulaii.app.ui.screens.home.HomeView
import org.gulaii.app.ui.screens.profile.ProfileView
import org.gulaii.app.ui.screens.walk.WalkView
import org.gulaii.app.ui.screens.food.EditFoodVM
import org.gulaii.app.ui.screens.food.FoodEntryScreen
import org.gulaii.app.ui.screens.walk.ActivityEntryScreen

fun NavGraphBuilder.homeGraph(nav: NavHostController) {
  navigation<HomeGraph>(startDestination = Screen.Home) {

    composable<Screen.Home>  { HomeView(nav) }
    composable<Screen.Food>  { FoodView(nav) }
    composable<Screen.Walk>   { WalkView(nav) }
    composable<Screen.Profile> { ProfileView(nav) }
    composable<Screen.AddFoodEntry> {
      FoodEntryScreen(
        nav     = nav,
        onSaved = { nav.popBackStack(Screen.Food, false) }
      )
    }
    composable<Screen.EditFoodEntry> { back ->
      val id      = back.arguments?.getString("id") ?: return@composable
      val repo    = ServiceLocator.foodRepo()
      val origin  = repo.find(id)
      if (origin == null) { nav.popBackStack(); return@composable }

      val vm: EditFoodVM =
        viewModel(factory = EditFoodVM.factory(origin, repo))

      FoodEntryScreen(
        nav     = nav,
        onSaved = { nav.popBackStack() },
        vm      = vm
      )
    }
    composable<Screen.AddActivityEntry> {
      ActivityEntryScreen(nav = nav) { nav.popBackStack(Screen.Walk, false) }
    }
  }
}

