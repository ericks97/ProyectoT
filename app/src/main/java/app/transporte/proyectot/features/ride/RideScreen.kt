package app.transporte.proyectot.features.ride

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp

@Composable
fun RideScreen() {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally) {
        Spacer(modifier = Modifier.weight(1f))
        Text(text = "Ride Screen", fontSize = 25.sp)
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = { }) {
            Text(text = "Navegar al detalle")
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}