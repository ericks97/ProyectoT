package app.transporte.proyectot.features.Ride

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RideScreen(navController: NavController) {
val auth = FirebaseAuth.getInstance()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Bienvenido ScreenRIde",
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Aquí puedes agregar funcionalidades adicionales específicas para Chofer

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                auth.signOut() // Cerrar sesión
                navController.navigate("login") {
                    // Elimina las pantallas anteriores de la pila para evitar que el usuario regrese a ellas
                    popUpTo("login") { inclusive = true }
                }
            }
        ) {
            Text(text = "Cerrar sesión")
        }
    }

}
