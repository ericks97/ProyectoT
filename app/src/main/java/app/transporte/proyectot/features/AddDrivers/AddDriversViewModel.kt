package app.transporte.proyectot.features.adddrivers

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import app.transporte.proyectot.core.navigation.Driver
import kotlinx.coroutines.tasks.await

class AddDriversViewModel : ViewModel() {

    private val db = FirebaseFirestore.getInstance()

    suspend fun addDriver(driver: Driver) {
        try {
            val driverData = hashMapOf(
                "name" to driver.name,
                "email" to driver.email,
                "role" to "driver" // El rol siempre será "driver" en este caso
            )

            db.collection("users")
                .add(driverData)
                .await() // Para esperar que Firestore responda
            Log.d("AddDriver", "Chofer agregado exitosamente")
        } catch (e: Exception) {
            Log.d("AddDriver", "Error al agregar chofer: ${e.message}")
        }
    }
}
