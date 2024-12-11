package app.transporte.proyectot.features.SuperAdmin.Ride

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import app.transporte.proyectot.R
import app.transporte.proyectot.core.model.Ride
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@SuppressLint("StateFlowValueCalledInComposition")

@Composable
fun RideScreen(
    navController: NavController, viewModel: RideViewModel
) {
    val navigateToAddRide: () -> Unit = {
        navController.navigate("addRideScreen")
    }
    val rides = viewModel.rides.value
    val error = viewModel.error.value
    val scope = rememberCoroutineScope()
    var selectedRide by remember { mutableStateOf<Ride?>(null) } // Estado para el traslado seleccionado
    var showDetailsDialog by remember { mutableStateOf(false) } // Estado para controlar la visualización del diálogo de detalles

    LaunchedEffect(Unit) {
        viewModel.loadRides()
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Barra superior personalizada
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.White)
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Gestión de Traslados",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = navigateToAddRide) {
                Icon(
                    painter = painterResource(id = R.drawable.plusicon),
                    contentDescription = "Agregar Traslado",
                    modifier = Modifier.size(30.dp),
                    tint = Color.Black // Color del ícono
                )
            }
        }

        // Contenido principal
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            when {
                !error.isNullOrEmpty() -> {
                    Text(
                        text = error,
                        color = Color.Red,
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 16.sp
                    )
                }

                rides.isEmpty() -> {
                    Text(
                        text = "No hay traslados disponibles.",
                        modifier = Modifier.align(Alignment.Center),
                        fontSize = 18.sp,
                        color = Color.Gray
                    )
                }

                else -> {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            Text(
                                text = "Total de traslados: ${rides.size}",
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        items(rides) { ride ->
                            RideItem(
                                ride = ride,
                                onClick = {
                                    selectedRide = ride
                                    showDetailsDialog = true
                                },
                                onDelete = {
                                    scope.launch {
                                        viewModel.deleteRide(ride)
                                    }
                                }
                            )
                        }
                    }
                }
            }

            // Diálogo con los detalles del traslado
            selectedRide?.let {
                if (showDetailsDialog) {
                    RideDetailsDialog(ride = it, onDismiss = { showDetailsDialog = false })
                }
            }
        }
    }
}


@Composable
fun RideItem(ride: Ride, onClick: () -> Unit, onDelete: () -> Job) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable(onClick = onClick),
        elevation = CardDefaults.elevatedCardElevation(4.dp),
        shape = MaterialTheme.shapes.small
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Muestra información básica del traslado
            Text("Chofer: ${ride.driverName}", style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black))
            Text("Origen: ${ride.origin}", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(8.dp))
            Text("Destino: ${ride.destination}", style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold))
            Spacer(modifier = Modifier.height(8.dp))
            Text("Fecha: ${ride.date}", style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Light, color = Color.Gray))

            // Línea divisoria dependiendo del estado del traslado
            val lineColor = if (ride.completed) Color.Green else Color.Red
            Spacer(modifier = Modifier.height(4.dp))
            Divider(color = lineColor, thickness = 2.dp)

            Spacer(modifier = Modifier.height(8.dp))

        }
    }
}

@Composable
fun RideDetailsDialog(ride: Ride, onDismiss: () -> Unit) {
    // Muestra un diálogo con los detalles completos del traslado
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text("Detalles del Traslado", style = MaterialTheme.typography.titleLarge.copy(color = Color.Black))
        },
        text = {
            Column {
                Text("Chofer: ${ride.driverName}", style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black))
                Text("Origen: ${ride.origin}", style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black))
                Text("Destino: ${ride.destination}", style = MaterialTheme.typography.bodyLarge.copy(color = Color.Black))
                Text("Fecha: ${ride.date}", style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray))
                Text("Hora de inicio: ${ride.startTime}", style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray))
                Text("Hora de fin: ${ride.endTime}", style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray))
                Text("Notas: ${ride.notes}", style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray))

                Spacer(modifier = Modifier.height(8.dp))

                // Muestra la lista de pasajeros con su nombre y contacto
                Text("Pasajeros:", style = MaterialTheme.typography.bodyMedium.copy(color = Color.Black))
                ride.passengers.forEach { passenger ->
                    Text("  - ${passenger.name}", style = MaterialTheme.typography.bodyMedium.copy(color = Color.Gray))
                    Text("    Contacto: ${passenger.phoneNumber}", style = MaterialTheme.typography.bodySmall.copy(color = Color.Gray))
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Salir")
            }
        },
        dismissButton = null // No hay botón de cierre adicional
    )
}
