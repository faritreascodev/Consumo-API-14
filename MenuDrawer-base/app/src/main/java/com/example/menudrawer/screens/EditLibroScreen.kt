package com.example.menudrawer.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.menudrawer.model.Libro
import com.example.menudrawer.model.LibroRequest
import com.example.menudrawer.viewmodel.LibrosViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditLibroScreen(
    libro: Libro,
    viewModel: LibrosViewModel = viewModel(),
    onNavigateBack: () -> Unit
) {
    var nombre by remember { mutableStateOf(libro.nombre) }
    var autor by remember { mutableStateOf(libro.autor) }
    var precio by remember { mutableStateOf(libro.precio.toString()) }

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    val isUpdating = viewModel.isUpdating
    val updateSuccess = viewModel.updateSuccess
    val updateError = viewModel.updateError

    LaunchedEffect(updateSuccess, updateError) {
        if (updateSuccess == true) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = "Libro actualizado con éxito!",
                    duration = SnackbarDuration.Short
                )
                viewModel.resetUpdateState()
                onNavigateBack()
            }
        } else if (updateError != null) {
            scope.launch {
                snackbarHostState.showSnackbar(
                    message = "Error: $updateError",
                    duration = SnackbarDuration.Long
                )
                viewModel.resetUpdateState()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically)
        ) {
            Text(
                text = "Editar Libro",
                style = MaterialTheme.typography.headlineMedium
            )

            Text(
                text = "Código: ${libro.codlibro}",
                style = MaterialTheme.typography.bodyLarge
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = { nombre = it },
                label = { Text("Nombre") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = autor,
                onValueChange = { autor = it },
                label = { Text("Autor") },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = precio,
                onValueChange = { precio = it },
                label = { Text("Precio") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                modifier = Modifier.fillMaxWidth()
            )

            Button(
                onClick = {
                    val precioDouble = precio.toDoubleOrNull() ?: 0.0
                    val request = LibroRequest(
                        codlibro = libro.codlibro,
                        nombre = nombre,
                        autor = autor,
                        precio = precioDouble
                    )
                    viewModel.actualizarLibro(libro.codlibro, request)
                },
                enabled = !isUpdating,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
            ) {
                if (isUpdating) {
                    CircularProgressIndicator(
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(24.dp)
                    )
                } else {
                    Text("Actualizar")
                }
            }
        }
    }
}
