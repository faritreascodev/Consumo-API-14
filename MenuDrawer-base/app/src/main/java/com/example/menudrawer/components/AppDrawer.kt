package com.example.menudrawer.components


import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.menudrawer.model.MenuItem


@Composable
fun AppDrawer(
  navController: NavController,
  items: List<MenuItem>,
  onCloseDrawer: () -> Unit
) {
  ModalDrawerSheet {
    Text("Mi App", modifier = Modifier.padding(16.dp), style = MaterialTheme.typography.headlineMedium)
    HorizontalDivider()

    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    items.forEach { item ->
      NavigationDrawerItem(
        label = { Text(item.title) },
        icon = { Icon(item.icon, contentDescription = null) },
        selected = currentRoute == item.route,
        onClick = {
          navController.navigate(item.route) {
            popUpTo(navController.graph.startDestinationId) {
              saveState = true
            }
            launchSingleTop = true
            restoreState = true
          }
          onCloseDrawer()
        },
        modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
      )
    }
  }
}