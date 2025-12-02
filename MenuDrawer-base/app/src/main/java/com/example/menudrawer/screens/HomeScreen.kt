package com.example.menudrawer.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.menudrawer.model.User
import com.example.menudrawer.viewmodel.UsersViewModel

@Composable
fun HomeScreen() {
  //La IU reacciona automáticamente a estas variables del ViewModel
  val viewModel: UsersViewModel = viewModel();
  val users = viewModel.userList
  val isLoading = viewModel.isLoading
  val error = viewModel.errorMessage

  Box(modifier = Modifier.fillMaxSize()) {
    when {
      isLoading -> {
        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
      }
      error != null -> {
        Text(
          text = error,
          color = MaterialTheme.colorScheme.error,
          modifier = Modifier.align(Alignment.Center)
        )
        // Botón para reintentar
        Button(
          onClick = { viewModel.fetchUsers() },
          modifier = Modifier.align(Alignment.BottomCenter).padding(16.dp)
        ) {
          Text("Reintentar")
        }
      }
      else -> {
        // LISTA DE USUARIOS (RecyclerView moderno)
        LazyColumn(
          contentPadding = PaddingValues(16.dp),
          verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          items(users) { user ->
            UserCard(user)
          }


        }
      }
    }
  }
}


@Composable
fun UserCard(user: User) {
  Card(
    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
    modifier = Modifier.fillMaxWidth()
  ) {
    ListItem(
      headlineContent = { Text(user.name, style = MaterialTheme.typography.titleMedium) },
      supportingContent = { Text(user.email) },
      leadingContent = {
        Icon(Icons.Default.Person, contentDescription = null)
      }

    )
  }
}
