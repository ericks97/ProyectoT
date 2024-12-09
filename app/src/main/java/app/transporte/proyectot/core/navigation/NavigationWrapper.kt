package app.transporte.proyectot.core.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.transporte.proyectot.features.auth.login.LoginScreen
import app.transporte.proyectot.features.superadmin.SuperAdminScreen
import app.transporte.proyectot.features.driver.DriverScreen

@Composable
fun NavigationWrapper() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "login") {
        composable("login") {
            LoginScreen(
                navigateToSuperAdminScreen = { navController.navigate("superadmin") },
                navigateToDriverScreen = { navController.navigate("driver") }
            )
        }

        composable("superadmin") {
            SuperAdminScreen(navController = navController)
        }

        composable("driver") {
            DriverScreen(navController = navController)
        }
    }
}
