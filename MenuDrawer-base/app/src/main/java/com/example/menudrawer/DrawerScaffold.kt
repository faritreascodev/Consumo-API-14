package com.example.menudrawer

import androidx.compose.foundation.layout.padding


import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DrawerScaffold(
  context: Context,
  selectedItem: String,
  content: @Composable () -> Unit
) {
  val drawerState = rememberDrawerState(DrawerValue.Closed)
  val scope = rememberCoroutineScope()

  ModalNavigationDrawer(
    drawerState = drawerState,
    drawerContent = {
      ModalDrawerSheet {
        DrawerMenu(
          context = context,
          selectedItem = selectedItem,
          onCloseDrawer = { scope.launch { drawerState.close() } }
        )
      }
    }
  ) {
    Scaffold(
      topBar = {
        TopAppBar(
          title = { Text("App con Drawer") },
          navigationIcon = {
            IconButton(onClick = {
              scope.launch { drawerState.open() }
            }) {
              Icon(Icons.Default.Menu, contentDescription = "Menú")
            }
          }
        )
      }
    ) { innerPadding ->
      Surface(modifier = Modifier.padding(innerPadding)) {
        content()
      }
    }
  }
}
