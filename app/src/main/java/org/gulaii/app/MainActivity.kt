package org.gulaii.app

import org.gulaii.app.ui.navigation.AppNavHost
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import org.gulaii.app.di.ServiceLocator
import org.gulaii.app.ui.theme.GulaiiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ServiceLocator.init(applicationContext)
        setContent {
            GulaiiTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}
