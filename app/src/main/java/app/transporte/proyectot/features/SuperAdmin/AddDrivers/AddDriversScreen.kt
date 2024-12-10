package app.transporte.proyectot.features.adddrivers

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.transporte.proyectot.core.navigation.Driver
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AddDriversScreen(navController: NavController, viewModel: AddDriversViewModel = viewModel()) {
    val name = remember { mutableStateOf("") }
    val email = remember { mutableStateOf("") }
    val isLoading = remember { mutableStateOf(false) }

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(
            text = "Agregar Chofer",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        TextField(
            value = name.value,
            onValueChange = { name.value = it },
            label = { Text("Nombre del Chofer") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(12.dp))

        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            label = { Text("Email del Chofer") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Botón para agregar chofer
        Button(
            onClick = {
                if (name.value.isNotEmpty() && email.value.isNotEmpty()) {
                    isLoading.value = true
                } else {
                    Toast.makeText(navController.context, "Por favor, complete todos los campos.", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading.value,
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color.Black)
        ) {
            Text(
                text = if (isLoading.value) "Cargando..." else "Agregar Chofer",
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Botón para volver
        Button(
            onClick = {
                navController.popBackStack()
            },
            modifier = Modifier.fillMaxWidth(),
            colors = androidx.compose.material3.ButtonDefaults.buttonColors(containerColor = Color.Gray)
        ) {
            Text(
                text = "Volver",
                color = Color.White
            )
        }
    }

    // Lógica para agregar chofer de manera suspendida
    LaunchedEffect(isLoading.value) {
        if (isLoading.value) {
            // Aquí llamamos la función suspendida
            viewModel.addDriver(Driver(name.value, email.value))
            Toast.makeText(navController.context, "Chofer agregado correctamente", Toast.LENGTH_SHORT).show()
            isLoading.value = false
        }
    }
}
