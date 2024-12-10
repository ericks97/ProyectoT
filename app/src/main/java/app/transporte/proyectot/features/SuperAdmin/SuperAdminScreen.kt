package app.transporte.proyectot.features.superadmin

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import androidx.compose.ui.graphics.Color

@Composable
fun SuperAdminScreen(navController: NavController) {
    val auth = FirebaseAuth.getInstance()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Título de la pantalla
        Text(
            text = "Bienvenido, ¿Qué deseas hacer?",
            style = MaterialTheme.typography.headlineLarge,
            modifier = Modifier.padding(bottom = 32.dp)  // Espaciado debajo del título
        )

        // Botón para ver choferes
        Button(
            onClick = { navController.navigate("viewDriversScreen") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),  // Espaciado entre botones
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color.Black)  // Botón negro
        ) {
            Text(
                text = "Ver Choferes",
                color = Color.White
            )
        }

        // Botón para cerrar sesión
        Button(
            onClick = {
                auth.signOut() // Cerrar sesión
                navController.navigate("login") {
                    // Elimina las pantallas anteriores de la pila para evitar que el usuario regrese a ellas
                    popUpTo("login") { inclusive = true }
                }
            },
            modifier = Modifier.fillMaxWidth(),  // Botón de ancho completo
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color.Black)  // Botón negro
        ) {
            Text(
                text = "Cerrar sesión",
                color = Color.White
            )
        }
    }
}
