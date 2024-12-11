package app.transporte.proyectot.features.SuperAdmin.Ride

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.transporte.proyectot.core.model.Ride
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class AddRideViewModel : ViewModel() {
    private val _ride = MutableStateFlow(Ride())
    val ride: StateFlow<Ride> = _ride

    private val _drivers = MutableStateFlow(listOf<Driver>())
    val drivers: StateFlow<List<Driver>> = _drivers

    private val _isDriverListVisible = MutableStateFlow(false)
    val isDriverListVisible: StateFlow<Boolean> = _isDriverListVisible

    init {
        loadDrivers()
    }

    fun loadDrivers() {
        viewModelScope.launch {
            val firestore = FirebaseFirestore.getInstance()
            try {
                val snapshot = firestore.collection("users")
                    .whereEqualTo("role", "driver") // Filtrar solo usuarios con rol de conductor
                    .get()
                    .await()

                val driverList = snapshot.documents.mapNotNull { doc ->
                    val id = doc.id
                    val name = doc.getString("driverName") // Asegúrate de que este campo exista en tu colección
                    if (name != null) Driver(id, name) else null
                }
                _drivers.value = driverList
            } catch (e: Exception) {
                // Manejo de errores (por ejemplo, mostrar un mensaje de error en logs o a los usuarios)
                e.printStackTrace()
            }
        }
    }

    fun toggleDriverList() {
        _isDriverListVisible.value = !_isDriverListVisible.value
    }

    fun selectDriver(driver: Driver) {
        _ride.value = _ride.value.copy(driverId = driver.id, driverName = driver.name)
        toggleDriverList()
    }

    fun updateRideField(field: String, value: String) {
        _ride.value = when (field) {
            "date" -> _ride.value.copy(date = value)
            "startTime" -> _ride.value.copy(startTime = value)
            "endTime" -> _ride.value.copy(endTime = value)
            "origin" -> _ride.value.copy(origin = value)
            "destination" -> _ride.value.copy(destination = value)
            "notes" -> _ride.value.copy(notes = value)
            else -> _ride.value
        }
    }

    fun saveRide() {
        viewModelScope.launch {
            // Simulación de guardado
            println("Ride saved: ${_ride.value}")
        }
    }
}

data class Driver(
    val id: String,
    val name: String
)