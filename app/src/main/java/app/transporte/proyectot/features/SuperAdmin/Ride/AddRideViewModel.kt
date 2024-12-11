package app.transporte.proyectot.features.SuperAdmin.Ride

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.transporte.proyectot.core.model.Ride
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

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

    private fun loadDrivers() {
        viewModelScope.launch {
            // Simulación de carga desde backend
            _drivers.value = listOf(
                Driver("1", "Juan Pérez"),
                Driver("2", "María López"),
                Driver("3", "Carlos Sánchez")
            )
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