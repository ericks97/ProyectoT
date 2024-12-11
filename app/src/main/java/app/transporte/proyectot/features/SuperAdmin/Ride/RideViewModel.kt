package app.transporte.proyectot.features.SuperAdmin.Ride

import android.util.Log
import androidx.lifecycle.ViewModel
import app.transporte.proyectot.core.model.Ride
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class RideViewModel : ViewModel() {
    private val db = FirebaseFirestore.getInstance()

    // Estado para los traslados y los errores
    private val _rides = MutableStateFlow<List<Ride>>(emptyList())
    val rides: StateFlow<List<Ride>> = _rides

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadRides()
    }

    // Carga los traslados desde Firebase
    fun loadRides() {
        db.collection("rides")
            .addSnapshotListener { snapshot, exception ->
                if (exception != null) {
                    handleError("Error al cargar los traslados: ${exception.message}")
                    return@addSnapshotListener
                }
                snapshot?.let {
                    _rides.value = it.documents.mapNotNull { document ->
                        document.toObject(Ride::class.java)
                    }
                }
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
                loadRides()  // Recargar la lista de traslados
            }
            .addOnFailureListener { exception ->
                Log.d("FirestoreError", "Error al eliminar traslado: ${exception.message}")
            }
    }
    private fun handleError(message: String) {
        _error.value = message
        println(message)
    }
}
