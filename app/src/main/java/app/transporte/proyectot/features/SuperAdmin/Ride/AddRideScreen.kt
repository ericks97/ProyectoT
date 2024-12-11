@file:Suppress("UNREACHABLE_CODE")

package app.transporte.proyectot.features.SuperAdmin.Ride

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import app.transporte.proyectot.R


@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRideScreen(viewModel: AddRideViewModel) {
    val selectedDate by viewModel.date.observeAsState("")

    var showDatePicker by remember { mutableStateOf(false) }
    val datePickerState = rememberDatePickerState()

    if (showDatePicker) {
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(onClick = {
                    val selectedDateMillis = datePickerState.selectedDateMillis
                    if (selectedDateMillis != null) {
                        val formattedDate = java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")
                            .format(java.time.Instant.ofEpochMilli(selectedDateMillis)
                                .atZone(java.time.ZoneId.systemDefault()).toLocalDate())
                        viewModel.updateDate(formattedDate) // Actualiza el ViewModel
                    }
                    showDatePicker = false
                }) {
                    Text("Aceptar")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) {
                    Text("Cancelar")
                }
            }
        ) {
            DatePicker(state = datePickerState)
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        TextField(
            value = selectedDate, // Observa la fecha seleccionada desde el ViewModel
            onValueChange = {},
            label = { Text("Fecha del viaje") },
            modifier = Modifier.fillMaxWidth(),
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(
                        painter = painterResource(id = R.drawable.calendar),
                        contentDescription = "Abrir calendario"
                    )
                }
            },
            readOnly = true
        )
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Hora de inicio") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Hora de fin") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Origen") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Destino") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Estado del viaje") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("ID del conductor") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Nombre del conductor") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Notas adicionales") },
            modifier = Modifier.fillMaxWidth()
        )
        TextField(
            value = "",
            onValueChange = {},
            label = { Text("Índice de orden") },
            modifier = Modifier.fillMaxWidth()
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(
                onClick = { /* Acción para guardar */ },
                modifier = Modifier.weight(1f)
            ) {
                Text("Guardar")
            }
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = { /* Acción para cancelar */ },
                modifier = Modifier.weight(1f),
                colors = ButtonDefaults.buttonColors(containerColor = Color.Gray)
            ) {
                Text("Cancelar")
            }
        }
    }
}
