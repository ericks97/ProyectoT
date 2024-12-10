package app.transporte.proyectot.features.auth.login

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun LoginScreen(
    navigateToSuperAdminScreen: () -> Unit,
    navigateToDriverScreen: () -> Unit,
    viewModel: LoginViewModel = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var showWelcomeDialog by remember { mutableStateOf(false) }

    // Esta parte controla la navegación después de iniciar sesión exitosamente
    LaunchedEffect(uiState.success) {
        if (uiState.success) {
            showWelcomeDialog = true // Muestra el popup de bienvenida
        }
    }

    // Dialogo de bienvenida
    // Dialogo de bienvenida
    if (showWelcomeDialog) {
        AlertDialog(
            onDismissRequest = {
                showWelcomeDialog = false
                // Realiza la navegación después de que el usuario cierra el popup
                when (uiState.role) {
                    "superadmin" -> navigateToSuperAdminScreen()
                    "driver" -> navigateToDriverScreen()
                    else -> {} // En caso de que el rol no sea reconocido
                }
            },
            title = {
                Text(
                    text = "Bienvenido, ${uiState.userName?.capitalize() ?: "Invitado"}",
                    style = MaterialTheme.typography.titleLarge
                )
            },
            text = {
                Text(
                    text = "Has iniciado sesión como ${uiState.role}",
                    style = MaterialTheme.typography.bodyMedium
                )
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        showWelcomeDialog = false
                        // Realiza la navegación después de que el usuario cierra el popup
                        when (uiState.role) {
                            "superadmin" -> navigateToSuperAdminScreen()
                            "driver" -> navigateToDriverScreen()
                            else -> {} // En caso de que el rol no sea reconocido
                        }
                    }
                ) {
                    Text("OK")
                }
            }
        )
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Inicio de Sesión",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text(text = "Correo electrónico") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = { /* Focus to password field */ }
            )
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text(text = "Contraseña") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true,
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = { /* Handle login action */ }
            )
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Mostrar el mensaje de error si es necesario
        uiState.errorMessage?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        Button(
            onClick = { viewModel.login(email, password) },
            modifier = Modifier.fillMaxWidth(),
            enabled = !uiState.isLoading,
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black)  // Botón negro
        ) {
            if (uiState.isLoading) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Text(text = "Iniciar Sesión", color = Color.White)
            }
        }

        Spacer(modifier = Modifier.weight(1f))
    }
}
