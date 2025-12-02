package com.example.menudrawer.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.School
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen() {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(Color(0xFFF3E5F5))
      .padding(24.dp),
    horizontalAlignment = Alignment.CenterHorizontally
  ) {
    Spacer(modifier = Modifier.height(32.dp))

    // Avatar circular
    Box(
      modifier = Modifier
        .size(120.dp)
        .clip(CircleShape)
        .background(MaterialTheme.colorScheme.primary),
      contentAlignment = Alignment.Center
    ) {
      Icon(
        imageVector = Icons.Default.Person,
        contentDescription = "Avatar Farit",
        modifier = Modifier.size(64.dp),
        tint = Color.White
      )
    }

    Spacer(modifier = Modifier.height(16.dp))

    Text(
      text = "Estudiante de Desarrollo Móvil",
      style = MaterialTheme.typography.headlineSmall,
      fontWeight = FontWeight.Bold
    )

    Text(
      text = "Android Developer",
      style = MaterialTheme.typography.bodyLarge,
      color = MaterialTheme.colorScheme.primary
    )

    Spacer(modifier = Modifier.height(32.dp))

    // Información del perfil
    ProfileInfoCard(
      icon = Icons.Default.School,
      label = "Carrera",
      value = "Desarrollo de Software"
    )

    ProfileInfoCard(
      icon = Icons.Default.Email,
      label = "Correo",
      value = "fareasco@pucese.edu.ec"
    )

    ProfileInfoCard(
      icon = Icons.Default.Phone,
      label = "Teléfono",
      value = "+593 93 914 3233"
    )

    Spacer(modifier = Modifier.weight(1f))

    Text(
      text = "Proyecto: Consumo API REST - Libros",
      style = MaterialTheme.typography.bodySmall,
      color = Color.Gray
    )
  }
}

@Composable
fun ProfileInfoCard(icon: ImageVector, label: String, value: String) {
  Card(
    modifier = Modifier
      .fillMaxWidth()
      .padding(vertical = 8.dp),
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
        contentDescription = label,
        tint = MaterialTheme.colorScheme.primary,
        modifier = Modifier.size(32.dp)
      )
      Spacer(modifier = Modifier.width(16.dp))
      Column {
        Text(
          text = label,
          style = MaterialTheme.typography.bodySmall,
          color = Color.Gray
        )
        Text(
          text = value,
          style = MaterialTheme.typography.bodyLarge,
          fontWeight = FontWeight.Medium
        )
      }
    }
  }
}
