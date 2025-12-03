package com.example.menudrawer.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.menudrawer.model.Libro
import com.example.menudrawer.viewmodel.LibrosViewModel
import kotlinx.coroutines.launch

@Composable
fun LibroScreen(
    onEditLibro: (cod: String) -> Unit,
    onLibroClick: (cod: String) -> Unit = {}
) {
    val librosViewModel: LibrosViewModel = viewModel()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    var libroAEliminar by remember { mutableStateOf<Libro?>(null) }

    LaunchedEffect(Unit) {
        librosViewModel.getLibroList()
    }

    // Escucha cambios en el resultado del DELETE
    LaunchedEffect(librosViewModel.deleteSuccess, librosViewModel.deleteError) {
        when {
            librosViewModel.deleteSuccess == true -> {
                scope.launch {
                    snackbarHostState.showSnackbar("Libro eliminado con éxito")
                }
                librosViewModel.resetDeleteState()
            }
            librosViewModel.deleteError != null -> {
                scope.launch {
                    snackbarHostState.showSnackbar("Error al eliminar: ${librosViewModel.deleteError}")
                }
                librosViewModel.resetDeleteState()
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
                .padding(16.dp)
        ) {
            Text(
                text = "Lista de Libros",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 16.dp)
            )

            if (librosViewModel.isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            if (librosViewModel.errorMessage.isNotEmpty()) {
                Text(
                    text = librosViewModel.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(8.dp)
                )
            }

            LazyColumn {
                items(librosViewModel.libroList) { libro ->
                    LibroItem(
                        libro = libro,
                        onEdit = { onEditLibro(libro.codlibro) },
                        onDelete = { libroAEliminar = libro },
                        onClick = { onLibroClick(libro.codlibro) }
                    )
                }
            }
        }

        // Diálogo de confirmación de borrado
        if (libroAEliminar != null) {
            AlertDialog(
                onDismissRequest = { libroAEliminar = null },
                title = { Text("Confirmar eliminación") },
                text = { Text("¿Seguro que deseas eliminar \"${libroAEliminar!!.nombre}\"?") },
                confirmButton = {
                    TextButton(onClick = {
                        librosViewModel.eliminarLibro(libroAEliminar!!.codlibro)
                        libroAEliminar = null
                    }) {
                        Text("Eliminar")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { libroAEliminar = null }) {
                        Text("Cancelar")
                    }
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibroItem(
    libro: Libro,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = libro.nombre,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(1f)
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // icono Info
                    IconButton(onClick = onClick) {
                        Icon(
                            Icons.Default.Info,
                            contentDescription = "Ver detalle",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Text(
                        text = "$${String.format("%.2f", libro.precio)}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Autor: ${libro.autor}")
            Text(text = "Código: ${libro.codlibro}")

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                TextButton(onClick = onEdit) {
                    Text("Editar")
                }
                Spacer(modifier = Modifier.width(8.dp))
                TextButton(
                    onClick = onDelete,
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Eliminar")
                }
            }
        }
    }
}
