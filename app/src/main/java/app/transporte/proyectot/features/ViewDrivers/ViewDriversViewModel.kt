import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModel
import app.transporte.proyectot.core.navigation.Driver
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject

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
                        if (name != null && email != null) {
                            driversList.add(Driver(name, email))
                        }
                    }
                    _drivers.value = driversList
                }
            }
            .addOnFailureListener { exception ->
                Log.d("FirestoreError", "Error al cargar choferes: ${exception.message}")
            }
    }
}

