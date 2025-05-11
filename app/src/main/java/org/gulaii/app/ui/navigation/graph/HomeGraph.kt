package org.gulaii.app.ui.navigation.graph

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.gulaii.app.di.ServiceLocator
import org.gulaii.app.ui.navigation.HomeGraph
import org.gulaii.app.ui.navigation.Screen
import org.gulaii.app.ui.screens.food.EditFoodVM
import org.gulaii.app.ui.screens.food.FoodEntryScreen
import org.gulaii.app.ui.screens.food.FoodView
import org.gulaii.app.ui.screens.home.HomeView
import org.gulaii.app.ui.screens.profile.ProfileView
import org.gulaii.app.ui.screens.walk.ActivityEntryScreen
import org.gulaii.app.ui.screens.walk.WalkView

fun NavGraphBuilder.registerHomeGraph(nav: NavHostController) {
    navigation<HomeGraph>(startDestination = Screen.Home) {
        composable<Screen.Home> {
            HomeView(
                onNavigateToFood = { nav.navigate(Screen.Food) },
                onNavigateToWalk = { nav.navigate(Screen.Walk) },
                onNavigateToProfile = { nav.navigate(Screen.Profile) }
            )
        }
        composable<Screen.Food> {
            FoodView(
                onNavigateToAddEntry = { nav.navigate(Screen.AddFoodEntry) },
                onNavigateToEditEntry = { id -> nav.navigate(Screen.EditFoodEntry(id)) },
                onNavigate = { screen -> nav.navigate(screen) }
            )
        }
        composable<Screen.Walk> {
            WalkView(onNavigate = { nav.navigate(it) })
        }
        composable<Screen.Profile> {
            ProfileView(onNavigate = { screen ->
                if (screen == Screen.Auth) {
                    nav.navigate(screen) {
                        popUpTo<HomeGraph> { inclusive = true }
                    }
                } else {
                    nav.navigate(screen)
                }
            })
        }
        composable<Screen.AddFoodEntry> {
            FoodEntryScreen(
                onBack = {
                    nav.popBackStack<Screen.Food>(false)
                    Unit
                },
                onSaved = {
                    nav.popBackStack<Screen.Food>(false)
                    Unit
                }
            )
        }
        composable<Screen.EditFoodEntry> { back ->
            val id = back.arguments?.getString("id") ?: return@composable
            val repo = ServiceLocator.foodRepo()
            val origin = repo.find(id) ?: run {
                nav.popBackStack()
                return@composable
            }
            val vm: EditFoodVM = viewModel(factory = EditFoodVM.factory(origin, repo))
            FoodEntryScreen(
                onBack = {
                    nav.popBackStack<Screen.Food>(false)
                    Unit
                },
                onSaved = {
                    nav.popBackStack<Screen.Food>(false)
                    Unit
                },
                vm = vm
            )
        }
        composable<Screen.AddActivityEntry> {
            ActivityEntryScreen(
                onBack = {
                    nav.popBackStack<Screen.Walk>(false)
                    Unit
                },
                onSaved = {
                    nav.popBackStack<Screen.Walk>(false)
                    Unit
                }
            )
        }
    }
}
