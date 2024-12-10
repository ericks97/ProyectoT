package app.transporte.proyectot.features.ViewDrivers

import ViewDriversViewModel
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.transporte.proyectot.core.navigation.Driver
import app.transporte.proyectot.R


@Composable
fun ViewDriversScreen(navController: NavController, viewModel: ViewDriversViewModel) {
    val navigateToAddDriver: () -> Unit = {
        navController.navigate("addDriversScreen")
    }
    // Llamar a viewDrivers() cuando la pantalla se cree
    LaunchedEffect(Unit) {
        viewModel.viewDrivers()  // Cargar los choferes
    }

    val driversList = viewModel.drivers.value

    // Mostrar la lista de choferes
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Lista de Choferes",
                style = MaterialTheme.typography.headlineMedium
            )

            // Aquí colocamos el ícono a la derecha
            IconButton(onClick = navigateToAddDriver) {
                Icon(
                    painter = painterResource(id = R.drawable.plusicon),
                    contentDescription = "Agregar Chofer",
                    modifier = Modifier.size(30.dp),
                    tint = Color.Black // Puedes cambiar el color del ícono
                )
            }
        }

        if (driversList.isEmpty()) {
            Text(
                text = "No se encontraron choferes",
                modifier = Modifier.fillMaxSize(),
                style = MaterialTheme.typography.bodyMedium
            )
        } else {
            LazyColumn {
                items(driversList) { driver ->
                    DriverItem(driver)
                }
            }
        }
    }
}

@Composable
fun DriverItem(driver: Driver) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Text(
                text = "Nombre: ${driver.name}",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Text(
                text = "Email: ${driver.email}",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )

            Button(
                onClick = {
                    // Acción para ver detalles del chofer o realizar alguna acción
                },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)  // Botón negro
            ) {
                Text(
                    text = "Ver detalles",
                    color = Color.White
                )
            }
        }
    }
}
