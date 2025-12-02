package com.example.menudrawer

import androidx.compose.foundation.clickable

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.menudrawer.vistas.Pantalla1Activity
import com.example.menudrawer.vistas.Pantalla2Activity
import kotlin.jvm.java

@Composable
fun DrawerMenu(
  context: Context,
  selectedItem: String,              // <-- Para marcar ítem activo
  onCloseDrawer: () -> Unit
) {

  Column(
    modifier = Modifier
      .fillMaxWidth()
      .background(Color(0xFFEFF2FA))
      .padding(16.dp)
  ) {

    // --------------------------------------------------------
    // AVATAR / FOTO DE USUARIO
    // --------------------------------------------------------
    Row(
      verticalAlignment = Alignment.CenterVertically
    ) {
      Image(
        painter = painterResource(id = R.drawable.fotokp),
        contentDescription = "Avatar",
        modifier = Modifier
          .size(70.dp)
          .clip(CircleShape)
      )

      Spacer(modifier = Modifier.width(12.dp))

      Column {
        Text("Bienvenido", style = MaterialTheme.typography.titleMedium)
        Text("Usuario de prueba", style = MaterialTheme.typography.bodySmall)
      }
    }

    Spacer(modifier = Modifier.height(20.dp))
    Divider()

    Spacer(modifier = Modifier.height(10.dp))


    // LISTA DEL MENÚ
    DrawerItem(
      label = "Inicio",
      icon = Icons.Default.Home,
      isSelected = selectedItem == "inicio",
      onClick = {
        context.startActivity(Intent(context, MainActivity::class.java))
        onCloseDrawer()
      }
    )

    DrawerItem(
      label = "Pantalla 1",
      icon = Icons.Default.List,
      isSelected = selectedItem == "pantalla1",
      onClick = {
        context.startActivity(Intent(context, Pantalla1Activity::class.java))
        onCloseDrawer()
      }
    )

    DrawerItem(
      label = "Pantalla 2",
      icon = Icons.Default.Settings,
      isSelected = selectedItem == "pantalla2",
      onClick = {
        context.startActivity(Intent(context, Pantalla2Activity::class.java))
        onCloseDrawer()
      }
    )

    Spacer(modifier = Modifier.height(12.dp))
    Divider()
  }
}



@Composable
fun DrawerItem(
  label: String,
  icon: androidx.compose.ui.graphics.vector.ImageVector,
  isSelected: Boolean,
  onClick: () -> Unit
) {
  val bgColor = if (isSelected) Color(0xFFDBE7FF) else Color.Transparent
  val textColor = if (isSelected) Color(0xFF0A3D91) else Color.Black

  Row(
    modifier = Modifier
      .fillMaxWidth()
      .background(bgColor)
      .padding(12.dp)
      .height(48.dp)
      .padding(start = 8.dp)
      .background(bgColor)
      .clickable { onClick() },
    verticalAlignment = Alignment.CenterVertically
  ) {

    Icon(
      icon,
      contentDescription = label,
      tint = textColor
    )

    Spacer(modifier = Modifier.width(16.dp))

    Text(
      text = label,
      color = textColor,
      style = MaterialTheme.typography.bodyLarge
    )
  }
}
