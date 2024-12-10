package app.transporte.proyectot.features.SuperAdmin.Traslados

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import app.transporte.proyectot.core.model.Ride
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RideScreen(
    viewModel: RideViewModel
) {
    val rides = viewModel.rides.value
    val isLoading = viewModel.isLoading.value
    val error = viewModel.error.value
    val scope = rememberCoroutineScope()

    LaunchedEffect(Unit) {
        viewModel.fetchRides()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Gestión de Traslados") },
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when {
                isLoading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
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
                        modifier = Modifier.fillMaxSize().padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        item {
                            Text(
                                text = "Total de traslados: ${rides.size}",
                                modifier = Modifier.padding(16.dp),
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                        items(rides) { ride ->
                            RideItem(
                                ride = ride,
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
        }
    }
}

@Composable
fun RideItem(
    ride: Ride,
    onDelete: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = "Origen: ${ride.origin}",
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Destino: ${ride.destination}",
                fontSize = 16.sp
            )
            Text(
                text = "Chofer: ${ride.driverName}",
                fontSize = 14.sp
            )
            Text(
                text = "Fecha: ${ride.date}",
                fontSize = 14.sp
            )
            Text(
                text = "Estado: ${ride.status}",
                fontSize = 14.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Button(
                onClick = onDelete,
                colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                modifier = Modifier.align(Alignment.End)
            ) {
                Text(
                    text = "Eliminar",
                    color = Color.White
                )
            }
        }
    }
}
