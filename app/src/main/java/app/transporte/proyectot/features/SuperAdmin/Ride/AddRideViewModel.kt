package app.transporte.proyectot.features.SuperAdmin.Ride

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AddRideViewModel : ViewModel() {
    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    fun updateDate(newDate: String) {
        _date.value = newDate
    }
}