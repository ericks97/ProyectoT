package app.transporte.proyectot.features.SuperAdmin.ViewDrivers

import ViewDriversViewModel
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import app.transporte.proyectot.core.model.Driver
import app.transporte.proyectot.R


@Composable
fun ViewDriversScreen(navController: NavController, viewModel: ViewDriversViewModel) {
    val navigateToAddDriver: () -> Unit = {
        navController.navigate("addDriversScreen")
    }

    // Estado para mostrar el diálogo de confirmación
    val showDeleteDialog = remember { mutableStateOf(false) }
    val driverToDelete = remember { mutableStateOf<Driver?>(null) }

    // Estado para el mensaje de "¡Usuario eliminado!"
    val showSuccessMessage = remember { mutableStateOf(false) }

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
                    DriverItem(driver, onDelete = {
                        // Al hacer clic en eliminar, mostramos el popup
                        driverToDelete.value = driver
                        showDeleteDialog.value = true
                    })
                }
            }
        }

        // Diálogo de confirmación de eliminación
        if (showDeleteDialog.value) {
            DeleteConfirmationDialog(
                onConfirm = {
                    // Ejecutar eliminación
                    driverToDelete.value?.let { driver ->
                        viewModel.deleteDriver(driver)
                    }
                    showDeleteDialog.value = false
                    showSuccessMessage.value = true  // Mostrar mensaje de éxito
                },
                onDismiss = {
                    showDeleteDialog.value = false
                }
            )
        }

        // Mostrar mensaje de "¡Usuario eliminado!" con animación
        AnimatedVisibility(
            visible = showSuccessMessage.value,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier.padding(top = 16.dp)
        ) {
            Text(
                text = "¡Usuario eliminado!",
                style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                color = Color.Green
            )
        }

        // Después de 2 segundos, ocultar el mensaje
        LaunchedEffect(showSuccessMessage.value) {
            if (showSuccessMessage.value) {
                kotlinx.coroutines.delay(2000)
                showSuccessMessage.value = false
            }
        }
    }
}
@Composable
fun DriverItem(driver: Driver, onDelete: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp).fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Nombre: ${driver.name}",
                    style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                IconButton(onClick = onDelete) {
                    Icon(
                        painter = painterResource(id = R.drawable.bin),
                        contentDescription = "Eliminar Chofer",
                        modifier = Modifier.size(30.dp),
                        tint = Color.Black
                    )
                }
            }

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
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text(
                    text = "Ver detalles",
                    color = Color.White
                )
            }
        }
    }
}
@Composable
fun DeleteConfirmationDialog(onConfirm: () -> Unit, onDismiss: () -> Unit) {
    androidx.compose.material3.AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(text = "Confirmación")
        },
        text = {
            Text("¿Estás seguro que quieres eliminar a este usuario?")
        },
        confirmButton = {
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black)
            ) {
                Text("Sí", color = Color.White)
            }
        },
        dismissButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text("No", color = Color.White)
            }
        }
    )
}
