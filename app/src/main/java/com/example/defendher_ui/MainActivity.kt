package com.example.defendher_ui // Replace with your actual package name

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.defendher_ui.ui.theme.DefendHerTheme

sealed class Screen(val route: String) {
    object MainScreen : Screen("main_screen")
    object TravelTrackerScreen : Screen("travel_tracker_screen")
    object TrackingOnScreen: Screen("tracking_on_screen")
}

class MainActivity : ComponentActivity() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize FusedLocationProviderClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        setContent {
            DefendHerTheme {
                AppNavigation(fusedLocationClient)
            }
        }
    }
}

@Composable
fun AppNavigation(fusedLocationClient: FusedLocationProviderClient) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = Screen.MainScreen.route) {
        composable(Screen.MainScreen.route) {
            MainScreen(navController)
        }
        composable(Screen.TravelTrackerScreen.route) {
            TravelTrackerScreen(fusedLocationClient = fusedLocationClient,navController)
        }
        composable(Screen.TrackingOnScreen.route) {
            TrackingOnScreen(navController)
        }
    }
}
