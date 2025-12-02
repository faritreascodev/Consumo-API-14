package com.example.menudrawer.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.menudrawer.model.LibroRequest
import com.example.menudrawer.viewmodel.LibrosViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateLibroScreen(
    viewModel: LibrosViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    var codlibro by remember { mutableStateOf("") }
    var nombre by remember { mutableStateOf("") }
    var autor by remember { mutableStateOf("") }
    var precio by remember { mutableStateOf("") }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val isPosting = viewModel.isPosting
    val postSuccess = viewModel.postSuccess
    val postErrorMessage = viewModel.postErrorMessage

    // Efecto para mostrar Snackbar cuando la operación finaliza
    LaunchedEffect(postSuccess, postErrorMessage) {
        if (postSuccess == true) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = "Libro guardado con éxito!",
                    withDismissAction = true,
                    duration = SnackbarDuration.Short
                )
                // Limpiar campos
                codlibro = ""
                nombre = ""
                autor = ""
                precio = ""
                viewModel.resetPostState()
                onNavigateBack() // Volver al listado
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
                text = "Crear Nuevo Libro",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier.padding(bottom = 24.dp)
            )

            OutlinedTextField(
                value = codlibro,
                onValueChange = { codlibro = it },
                label = { Text("Código de Libro") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre del Libro") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = autor,
                onValueChange = { autor = it },
                label = { Text("Autor") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true
            )

            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio") },
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                singleLine = true
            )

            Button(
                onClick = {
                    val precioDouble = precio.toDoubleOrNull() ?: 0.0
                    val request = LibroRequest(
                        codlibro = codlibro,
                        nombre = nombre,
                        autor = autor,
                        precio = precioDouble
                    )
                    viewModel.createLibro(request)
                },
                enabled = !isPosting && codlibro.isNotBlank() && nombre.isNotBlank() &&
                        autor.isNotBlank() && precio.isNotBlank(),
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
                    Text("Guardar Libro")
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCreateLibroScreen() {
    MaterialTheme {
        CreateLibroScreen(onNavigateBack = {})
    }
}
