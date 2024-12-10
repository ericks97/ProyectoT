package app.transporte.proyectot.features.SuperAdmin.Traslados

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import app.transporte.proyectot.core.model.Ride
import com.google.firebase.firestore.FirebaseFirestore

class RideViewModel : ViewModel() {
    private val _rides = mutableStateOf<List<Ride>>(emptyList())
    val rides = _rides

    private val _isLoading = mutableStateOf(false)
    val isLoading = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error = _error

    // Función para cargar los traslados desde Firestore
    fun fetchRides() {
        _isLoading.value = true
        _error.value = null

        val db = FirebaseFirestore.getInstance()
        db.collection("rides")  // Suponiendo que la colección se llama "rides"
            .get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    Log.d("Firestore", "No se encontraron traslados")
                    _rides.value = emptyList()
                } else {
                    val ridesList = mutableListOf<Ride>()
                    for (document in result) {
                        val origin = document.getString("origin")
                        val destination = document.getString("destination")
                        val driverName = document.getString("driverName")
                        val startTime = document.getString("startTime")
                        val endTime = document.getString("endTime")
                        val date = document.getString("date")
                        val status = document.getString("status")
                        val passengers = document.get("passengers") as? List<Map<String, Any>> ?: emptyList()

                        // Obtener el ID del documento
                        val id = document.id

                        // Verificar que los campos no sean nulos antes de agregar
                        if (origin != null && destination != null && driverName != null && startTime != null &&
                            endTime != null && date != null && status != null) {
                            ridesList.add(
                                Ride(
                                    id = id,
                                    origin = origin,
                                    destination = destination,
                                    driverName = driverName,
                                    startTime = startTime,
                                    endTime = endTime,
                                    date = date,
                                    status = status,
                                    passengers = passengers,
                                    // Puedes agregar más campos según sea necesario
                                )
                            )
                        }
                    }
                    _rides.value = ridesList
                }
                _isLoading.value = false
            }
            .addOnFailureListener { exception ->
                _error.value = "Error al cargar los traslados: ${exception.message}"
                _isLoading.value = false
                Log.d("FirestoreError", "Error al cargar traslados: ${exception.message}")
            }
    }

    // Función para eliminar un traslado de Firestore
    fun deleteRide(ride: Ride) {
        val db = FirebaseFirestore.getInstance()
        db.collection("rides")
            .document(ride.id)  // Usamos el id para eliminar el documento
            .delete()
            .addOnSuccessListener {
                Log.d("Firestore", "Traslado eliminado correctamente")
                // Después de eliminar, actualizar la lista de traslados
                fetchRides()  // Recargar la lista de traslados
            }
            .addOnFailureListener { exception ->
                Log.d("FirestoreError", "Error al eliminar traslado: ${exception.message}")
            }
    }
}

