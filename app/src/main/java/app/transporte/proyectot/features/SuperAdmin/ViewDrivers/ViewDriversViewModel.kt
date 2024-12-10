import android.util.Log
import androidx.compose.runtime.mutableStateOf
import app.transporte.proyectot.core.model.Driver
import androidx.lifecycle.ViewModel

import com.google.firebase.firestore.FirebaseFirestore

class ViewDriversViewModel : ViewModel() {
    private val _drivers = mutableStateOf<List<Driver>>(emptyList())
    val drivers = _drivers

    // Función para cargar los choferes desde Firestore
    fun viewDrivers() {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .whereEqualTo("role", "driver")  // Consulta todos los documentos con el rol "driver"
            .get()
            .addOnSuccessListener { result ->
                if (result.isEmpty) {
                    Log.d("Firestore", "No se encontraron choferes")
                } else {
                    val driversList = mutableListOf<Driver>()
                    for (document in result) {
                        val name = document.getString("name")
                        val email = document.getString("email")
                        val id = document.id // Obtener el id del documento
                        if (name != null && email != null) {
                            driversList.add(Driver(id, name, email))
                        }
                    }
                    _drivers.value = driversList
                }
            }
            .addOnFailureListener { exception ->
                Log.d("FirestoreError", "Error al cargar choferes: ${exception.message}")
            }
    }

    // Función para eliminar el chofer de Firestore
    fun deleteDriver(driver: Driver) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .document(driver.id)  // Usamos el id para eliminar el documento
            .delete()
            .addOnSuccessListener {
                Log.d("Firestore", "Chofer eliminado correctamente")
                // Después de eliminar, actualizar la lista de choferes
                viewDrivers()  // Recargar la lista de choferes
            }
            .addOnFailureListener { exception ->
                Log.d("FirestoreError", "Error al eliminar chofer: ${exception.message}")
            }
    }
}
