package com.example.menudrawer.model

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

sealed class MenuItem(val route: String, val title: String, val icon: androidx.compose.ui.graphics.vector.ImageVector) {
  object Home : MenuItem("home", "Home", Icons.Default.Home)
  object Profile : MenuItem("profile", "Profile", Icons.Default.Person)
  object Settings : MenuItem("settings", "Settings", Icons.Default.Settings)
  object Libros : MenuItem("libros", "Libros", Icons.Default.Book)
  object CreateLibro : MenuItem("createLibro", "Nuevo Libro", Icons.Default.Add)
  object Usuarios : MenuItem("usuarios", "Usuarios", Icons.Default.People)
  object CreateUsuario : MenuItem("createUsuario", "Nuevo Usuario", Icons.Default.PersonAdd)
  object BuscarLibro : MenuItem("buscarLibro", "Buscar Libro", Icons.Default.Search)
  object EditLibro : MenuItem("editLibro/{codlibro}", "Editar Libro", Icons.Default.Edit)
}
