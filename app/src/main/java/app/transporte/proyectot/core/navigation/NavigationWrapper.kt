package app.transporte.proyectot.core.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import app.transporte.proyectot.features.SuperAdmin.Traslados.RideScreen
import app.transporte.proyectot.features.SuperAdmin.ViewDrivers.ViewDriversScreen
import app.transporte.proyectot.features.adddrivers.AddDriversScreen
import app.transporte.proyectot.features.auth.login.LoginScreen
import app.transporte.proyectot.features.driver.DriverScreen
import app.transporte.proyectot.features.superadmin.SuperAdminScreen

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

        composable("superAdmin") {
            SuperAdminScreen(navController = navController)
        }

        composable("driver") {
            DriverScreen(navController = navController)
        }

        composable("viewDriversScreen") {
            ViewDriversScreen(navController = navController, viewModel = viewModel())
        }

        composable("addDriversScreen") {
            AddDriversScreen(navController = navController, viewModel = viewModel())
        }

        composable("rideScreen"){
            RideScreen( viewModel = viewModel())
        }
    }
}
