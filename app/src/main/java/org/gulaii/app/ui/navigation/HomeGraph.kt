package org.gulaii.app.ui.navigation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import org.gulaii.app.ui.screens.food.FoodView
import org.gulaii.app.ui.screens.home.HomeView
import org.gulaii.app.ui.screens.profile.ProfileView
import org.gulaii.app.ui.screens.walk.WalkView

fun NavGraphBuilder.homeGraph(nav: NavHostController) {
  navigation<HomeGraph>(startDestination = Screen.Home) {

    composable<Screen.Home>  { HomeView(nav) }
    composable<Screen.Food>  { FoodView(nav) }
    composable<Screen.Walk>   { WalkView(nav) }
    composable<Screen.Profile> { ProfileView(nav) }
  }
}

