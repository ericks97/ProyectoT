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
                navigateToSuperAdminScreen = {
                    // Navegar a SuperAdminScreen y eliminar la pantalla de login de la pila
                    navController.navigate("superadmin") {
                        popUpTo("login") { inclusive = true }
                    }
                },
                navigateToDriverScreen = {
                    // Navegar a DriverScreen y eliminar la pantalla de login de la pila
                    navController.navigate("driver") {
                        popUpTo("login") { inclusive = true }
                    }
                }
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
