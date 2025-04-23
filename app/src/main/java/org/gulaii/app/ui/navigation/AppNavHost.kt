import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import org.gulaii.app.ui.navigation.InitialGraph
import org.gulaii.app.ui.navigation.OnboardingGraph
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
    }
}

