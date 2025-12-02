package com.example.menudrawer.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.menudrawer.viewmodel.LibrosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BuscarLibroScreen(
    viewModel: LibrosViewModel = viewModel()
) {
    var codigo by remember { mutableStateOf("") }
    val libro = viewModel.libroEncontrado
    val error = viewModel.busquedaError
    val isBuscando = viewModel.isBuscando

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Text(
            text = "Buscar Libro por Código",
            style = MaterialTheme.typography.headlineMedium
        )

        OutlinedTextField(
            value = codigo,
            onValueChange = { codigo = it },
            label = { Text("Código del libro") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = {
                if (codigo.isNotBlank()) {
                    viewModel.buscarLibroPorCodigo(codigo)
                }
            },
            enabled = !isBuscando && codigo.isNotBlank(),
            modifier = Modifier.fillMaxWidth()
        ) {
            if (isBuscando) {
                CircularProgressIndicator(
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.size(24.dp)
                )
            } else {
                Text("Buscar")
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        when {
            error != null -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.errorContainer
                    )
                ) {
                    Text(
                        text = error,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onErrorContainer
                    )
                }
            }
            libro != null -> {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = libro.nombre,
                            style = MaterialTheme.typography.titleLarge
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = "Autor: ${libro.autor}")
                        Text(text = "Código: ${libro.codlibro}")
                        Text(text = "Precio: $${libro.precio}")
                    }
                }
            }
        }
    }
}
