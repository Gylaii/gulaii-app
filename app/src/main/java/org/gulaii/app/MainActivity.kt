package org.gulaii.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import org.gulaii.app.ui.navigation.AppNavHost
import org.gulaii.app.ui.theme.GulaiiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GulaiiTheme {
                val navController = rememberNavController()
                AppNavHost(navController = navController)
            }
        }
    }
}
