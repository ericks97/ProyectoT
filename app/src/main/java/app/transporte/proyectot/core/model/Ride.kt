package app.transporte.proyectot.core.model

// Modelo de datos para un viaje
data class Ride(
    val id: String = "",             // Identificador único del viaje
    val date: String = "",           // Fecha del viaje
    val startTime: String = "",      // Hora de inicio del viaje
    val endTime: String = "",        // Hora de fin del viaje
    val origin: String = "",         // Ubicación de origen del viaje
    val destination: String = "",    // Destino del viaje
    val status: String = "",         // Estado del viaje (ej. Pendiente, En curso, Completado)
    val driverId: String = "",       // Identificador del conductor asignado al viaje
    val passengers: List<Passenger> = emptyList(), // Lista de pasajeros (cada uno representado por un objeto Passenger)
    val notes: String = "",          // Notas adicionales sobre el viaje
    val orderIndex: Int = 0,         // Índice para ordenar los viajes, útil para listas de viajes
    val completed: Boolean = false,  // Indica si el viaje está completado o no
    val createdAt: String = "",      // Fecha y hora en que se creó el viaje
    val updatedAt: String = ""       // Fecha y hora en que se actualizó el viaje
)

// Modelo de datos para un pasajero
data class Passenger(
    val name: String = "",           // Nombre del pasajero
    val phoneNumber: String = ""     // Número de teléfono del pasajero
)
