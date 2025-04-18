import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.gulaii.app.ui.navigation.Screen
import org.gulaii.app.ui.screens.initialScreen.InitialScreenView
import org.gulaii.app.ui.screens.onboardingScreen.OnboardingView

@Composable
fun AppNavHost(
  navController: NavHostController,
  modifier: Modifier = Modifier,
) {
  NavHost(
    navController = navController,
    startDestination = "initial_graph", // Начинаем с вложенного графа Initial
    modifier = modifier
  ) {
    navigation(
      startDestination = Screen.Initial.route,
      route = "initial_graph"
    ) {
      composable(route = Screen.Initial.route) {
        InitialScreenView(
          onNext = {
            navController.navigate("onboarding_graph")
          }
        )
      }
    }

    // Вложенный граф для онбординга
    navigation(
      startDestination = Screen.Onboarding.route,
      route = "onboarding_graph"
    ) {
      composable(route = Screen.Onboarding.route) {
        OnboardingView(
          onNext = {
            //to do
          }
        )
      }
    }
  }
}
