package app.transporte.proyectot.features.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val auth: FirebaseAuth = FirebaseAuth.getInstance()) : ViewModel() {
    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState: StateFlow<LoginUiState> = _uiState

    fun login(email: String, password: String) {
        _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)
        viewModelScope.launch {
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _uiState.value = _uiState.value.copy(isLoading = false, success = true)
                    } else {
                        _uiState.value = _uiState.value.copy(
                            isLoading = false,
                            errorMessage = task.exception?.localizedMessage
                        )
                    }
                }
        }
    }
}

data class LoginUiState(
    val isLoading: Boolean = false,
    val success: Boolean = false,
    val errorMessage: String? = null
)