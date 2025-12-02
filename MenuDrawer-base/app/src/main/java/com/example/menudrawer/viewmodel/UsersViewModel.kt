package com.example.menudrawer.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.menudrawer.model.User
import com.example.menudrawer.network.RetrofitClient
import kotlinx.coroutines.launch

class UsersViewModel : ViewModel() {

    // Lista de usuarios
    var userList by mutableStateOf<List<User>>(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    //maneja el error si falla el internet
    var errorMessage by mutableStateOf<String?>(null)
        private set

    init {
        //al crear el viewmodel, cargamos los usuarios automáticamente
        fetchUsers()
    }

    fun fetchUsers() {
        //lanzamos una corrutina
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                //llamada a la API
                val users = RetrofitClient.service.getUsers()
                userList = users
            } catch (e: Exception) {
                errorMessage = "Error al cargar los usuarios: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }
}
