package com.example.menudrawer.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.menudrawer.viewmodel.LibrosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibroDetailScreen(
    codLibro: String,
    viewModel: LibrosViewModel,
    onNavigateBack: () -> Unit
) {
    // Cargar el libro al entrar a la pantalla
    LaunchedEffect(codLibro) {
        viewModel.getLibroById(codLibro)
    }

    val libro = viewModel.selectedLibro
    val isLoading = viewModel.isLoadingDetail
    val error = viewModel.detailErrorMessage

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del Libro") },
                navigationIcon = {
                    IconButton(onClick = {
                        viewModel.clearSelection()
                        onNavigateBack()
                    }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Regresar")
                    }
                }
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            when {
                isLoading -> CircularProgressIndicator()
                error != null -> Text(
                    "Error: $error",
                    color = MaterialTheme.colorScheme.error
                )
                libro != null -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // Tarjeta de detalle
                        Card(
                            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(modifier = Modifier.padding(24.dp)) {
                                Text(
                                    text = libro.nombre,
                                    style = MaterialTheme.typography.headlineMedium
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                                Text(
                                    text = "Autor: ${libro.autor}",
                                    style = MaterialTheme.typography.titleMedium
                                )
                                HorizontalDivider(modifier = Modifier.padding(vertical = 16.dp))
                                Text(
                                    text = "Código: ${libro.codlibro}",
                                    style = MaterialTheme.typography.bodyMedium
                                )
                                Text(
                                    text = "Precio: $${libro.precio}",
                                    style = MaterialTheme.typography.headlineSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
