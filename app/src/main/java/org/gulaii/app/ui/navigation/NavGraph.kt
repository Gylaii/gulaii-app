package org.gulaii.app.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import org.gulaii.app.ui.screens.initialScreen.InitialScreenView
import org.gulaii.app.ui.screens.initialScreen.InitialScreenRoute

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController,
        startDestination = InitialScreenRoute.route,
        modifier = modifier
    ) {
        composable(route = InitialScreenRoute.route) {
            InitialScreenView(
                onNext = {
                    //to do nav
                }
            )
        }
    }
}