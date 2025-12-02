package com.example.menudrawer.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {
  var notificationsEnabled by remember { mutableStateOf(true) }
  var darkModeEnabled by remember { mutableStateOf(false) }
  var autoUpdateEnabled by remember { mutableStateOf(true) }

  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFE8F5E9))
      .padding(16.dp)
  ) {
    Text(
      text = "Configuración",
      style = MaterialTheme.typography.headlineMedium,
      fontWeight = FontWeight.Bold,
      modifier = Modifier.padding(vertical = 16.dp)
    )

    // Sección de Preferencias
    SettingsSectionTitle(title = "Preferencias")

    SettingsSwitchItem(
      icon = Icons.Default.Notifications,
      title = "Notificaciones",
      description = "Recibir alertas de nuevos libros",
      checked = notificationsEnabled,
      onCheckedChange = { notificationsEnabled = it }
    )

    SettingsSwitchItem(
      icon = Icons.Default.DarkMode,
      title = "Modo Oscuro",
      description = "Tema oscuro para la aplicación",
      checked = darkModeEnabled,
      onCheckedChange = { darkModeEnabled = it }
    )

    SettingsSwitchItem(
      icon = Icons.Default.Refresh,
      title = "Actualización Automática",
      description = "Sincronizar libros automáticamente",
      checked = autoUpdateEnabled,
      onCheckedChange = { autoUpdateEnabled = it }
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Sección de Información
    SettingsSectionTitle(title = "Información")

    SettingsClickableItem(
      icon = Icons.Default.Info,
      title = "Acerca de",
      description = "Versión 1.0.0"
    )

    SettingsClickableItem(
      icon = Icons.Default.Security,
      title = "Privacidad",
      description = "Política de privacidad"
    )

    SettingsClickableItem(
      icon = Icons.Default.Help,
      title = "Ayuda y Soporte",
      description = "Preguntas frecuentes"
    )

    Spacer(modifier = Modifier.weight(1f))

    // Footer
    Text(
      text = "API REST - Sistema de Gestión de Libros",
      style = MaterialTheme.typography.bodySmall,
      color = Color.Gray,
      modifier = Modifier.align(Alignment.CenterHorizontally)
    )
  }
}

@Composable
fun SettingsSectionTitle(title: String) {
  Text(
    text = title,
    style = MaterialTheme.typography.titleMedium,
    fontWeight = FontWeight.SemiBold,
    color = MaterialTheme.colorScheme.primary,
    modifier = Modifier.padding(vertical = 8.dp)
  )
}

@Composable
fun SettingsSwitchItem(
  icon: ImageVector,
  title: String,
  description: String,
  checked: Boolean,
  onCheckedChange: (Boolean) -> Unit
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        imageVector = icon,
        contentDescription = title,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(28.dp)
      )
      Spacer(modifier = Modifier.width(16.dp))
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = title,
          style = MaterialTheme.typography.bodyLarge,
          fontWeight = FontWeight.Medium
        )
        Text(
          text = description,
          style = MaterialTheme.typography.bodySmall,
          color = Color.Gray
        )
      }
      Switch(
        checked = checked,
        onCheckedChange = onCheckedChange
      )
    }
  }
}

@Composable
fun SettingsClickableItem(
  icon: ImageVector,
  title: String,
  description: String
) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 4.dp),
    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
    colors = CardDefaults.cardColors(containerColor = Color.White)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        imageVector = icon,
        contentDescription = title,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(28.dp)
      )
      Spacer(modifier = Modifier.width(16.dp))
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = title,
          style = MaterialTheme.typography.bodyLarge,
          fontWeight = FontWeight.Medium
        )
        Text(
          text = description,
          style = MaterialTheme.typography.bodySmall,
          color = Color.Gray
        )
      }
      Icon(
        imageVector = Icons.Default.ChevronRight,
        contentDescription = "Ver más",
        tint = Color.Gray
      )
    }
  }
}
