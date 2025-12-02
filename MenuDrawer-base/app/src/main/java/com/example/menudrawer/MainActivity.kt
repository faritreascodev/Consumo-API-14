package com.example.menudrawer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.menudrawer.components.AppDrawer
import com.example.menudrawer.model.MenuItem
import com.example.menudrawer.screens.CreateLibroScreen
import com.example.menudrawer.screens.CreateUsuarioScreen
import com.example.menudrawer.screens.EditLibroScreen
import com.example.menudrawer.screens.HomeScreen
import com.example.menudrawer.screens.LibroScreen
import com.example.menudrawer.screens.ProfileScreen
import com.example.menudrawer.screens.SettingsScreen
import com.example.menudrawer.screens.UsuarioScreen
import com.example.menudrawer.screens.BuscarLibroScreen
import com.example.menudrawer.ui.theme.MenuDrawerTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            MenuDrawerTheme {
                MainAppScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val menuItems = listOf(
        MenuItem.Home,
        MenuItem.Profile,
        MenuItem.Settings,
        MenuItem.Libros,
        MenuItem.CreateLibro,
        MenuItem.Usuarios,
        MenuItem.CreateUsuario,
        MenuItem.BuscarLibro
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            AppDrawer(
                navController = navController,
                items = menuItems,
                onCloseDrawer = { scope.launch { drawerState.close() } }
            )
        }
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Menú Lateral Demo") },
                    navigationIcon = {
                        IconButton(onClick = {
                            scope.launch { drawerState.open() }
                        }) {
                            Icon(Icons.Default.Menu, contentDescription = "Abrir menú")
                        }
                    }
                )
            }
        ) { innerPadding ->

            NavHost(
                navController = navController,
                startDestination = MenuItem.Libros.route,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(MenuItem.Home.route) { HomeScreen() }

                composable(MenuItem.Profile.route) { ProfileScreen() }

                composable(MenuItem.Settings.route) { SettingsScreen() }

                composable(MenuItem.Libros.route) {
                    LibroScreen(
                        onEditLibro = { cod ->
                            navController.navigate("editLibro/$cod")
                        }
                    )
                }

                composable(MenuItem.CreateLibro.route) {
                    CreateLibroScreen(
                        onNavigateBack = { navController.popBackStack() }
                    )
                }

                composable(MenuItem.Usuarios.route) { UsuarioScreen() }

                composable(MenuItem.CreateUsuario.route) {
                    CreateUsuarioScreen(
                        onNavigateBack = { navController.popBackStack() }
                    )
                }

                composable(MenuItem.BuscarLibro.route) { BuscarLibroScreen() }

                composable("editLibro/{codlibro}") { backStackEntry ->
                    val cod = backStackEntry.arguments?.getString("codlibro") ?: ""
                    EditLibroScreen(
                        codLibro = cod,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
            }
        }
    }
}

@Composable
fun ScreenContent(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.headlineLarge,
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewMainAppScreen() {
    MenuDrawerTheme {
        MainAppScreen()
    }
}
