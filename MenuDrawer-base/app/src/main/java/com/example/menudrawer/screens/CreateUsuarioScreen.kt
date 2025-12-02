package com.example.menudrawer.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.menudrawer.model.UsuarioRequest
import com.example.menudrawer.viewmodel.UsuariosViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateUsuarioScreen(
    viewModel: UsuariosViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    var nombre by remember { mutableStateOf("") }
    var correo by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val isPosting = viewModel.isPosting
    val postSuccess = viewModel.postSuccess
    val postErrorMessage = viewModel.postErrorMessage

    LaunchedEffect(postSuccess, postErrorMessage) {
        if (postSuccess == true) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = "Usuario creado con éxito!",
                    withDismissAction = true,
                    duration = SnackbarDuration.Short
                )
                nombre = ""
                correo = ""
                password = ""
                viewModel.resetPostState()
                onNavigateBack()
            }
        } else if (postErrorMessage != null) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = "Error: $postErrorMessage",
                    withDismissAction = true,
                    duration = SnackbarDuration.Long
                )
                viewModel.resetPostState()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            Text(
                text = "Crear Usuario",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = correo,
                onValueChange = { correo = it },
                label = { Text("Correo") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            Button(
                onClick = {
                    val request = UsuarioRequest(
                        nombre = nombre,
                        correo = correo,
                        password = password
                    )
                    viewModel.createUsuario(request)
                },
                enabled = !isPosting && nombre.isNotBlank() && correo.isNotBlank() && password.isNotBlank(),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                if (isPosting) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text("Guardar Usuario")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCreateUsuarioScreen() {
    MaterialTheme {
        CreateUsuarioScreen(onNavigateBack = {})
    }
}
