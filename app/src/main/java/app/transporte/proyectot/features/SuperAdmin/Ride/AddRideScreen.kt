package app.transporte.proyectot.features.SuperAdmin.Ride

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun AddRideScreen(viewModel: AddRideViewModel = viewModel()) {
    val ride by viewModel.ride.collectAsState()
    val drivers by viewModel.drivers.collectAsState()
    val isDriverListVisible by viewModel.isDriverListVisible.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text("Agregar Viaje", fontSize = 20.sp, modifier = Modifier.padding(bottom = 8.dp))

        TextField(
            value = ride.date,
            onValueChange = { viewModel.updateRideField("date", it) },
            label = { Text("Fecha (YYYY-MM-DD)") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = ride.startTime,
            onValueChange = { viewModel.updateRideField("startTime", it) },
            label = { Text("Hora de Inicio") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = ride.origin,
            onValueChange = { viewModel.updateRideField("origin", it) },
            label = { Text("Origen") },
            modifier = Modifier.fillMaxWidth()
        )

        TextField(
            value = ride.destination,
            onValueChange = { viewModel.updateRideField("destination", it) },
            label = { Text("Destino") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { viewModel.toggleDriverList() },
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = ride.driverName.ifEmpty { "Seleccionar Conductor" },
                modifier = Modifier.weight(1f).padding(end = 8.dp)
            )
            Icon(Icons.Default.ArrowDropDown, contentDescription = null)
        }

        if (isDriverListVisible) {
            Column(modifier = Modifier.fillMaxWidth()) {
                drivers.forEach { driver ->
                    Text(
                        text = driver.name,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.loadDrivers() }
                            .padding(8.dp)
                    )
                    Divider()
                }
            }
        }

        Button(
            onClick = { viewModel.saveRide() },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Guardar Viaje")
        }
    }
}
