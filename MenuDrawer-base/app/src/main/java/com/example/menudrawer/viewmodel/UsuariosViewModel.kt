package com.example.menudrawer.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.menudrawer.model.Usuario
import com.example.menudrawer.model.UsuarioRequest
import com.example.menudrawer.network.RetrofitClient
import kotlinx.coroutines.launch

class UsuariosViewModel : ViewModel() {

    var usuarioList: List<Usuario> by mutableStateOf(emptyList())
        private set

    var isLoading by mutableStateOf(false)
        private set

    var errorMessage: String? by mutableStateOf(null)
        private set

    var isPosting by mutableStateOf(false)
        private set

    var postSuccess: Boolean? by mutableStateOf(null)
        private set

    var postErrorMessage: String? by mutableStateOf(null)
        private set

    fun fetchUsuarios() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null
            try {
                Log.d("UsuariosViewModel", "Cargando usuarios...")
                val response = RetrofitClient.serviceLibros.getUsuarios()

                if (response.status) {
                    usuarioList = response.result
                    Log.d("UsuariosViewModel", "Usuarios cargados: ${response.result.size}")
                } else {
                    errorMessage = "No se encontraron usuarios"
                }
            } catch (e: Exception) {
                Log.e("UsuariosViewModel", "Error: ${e.message}", e)
                errorMessage = "Error: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun createUsuario(request: UsuarioRequest) {
        viewModelScope.launch {
            isPosting = true
            postSuccess = null
            postErrorMessage = null

            try {
                Log.d("UsuariosViewModel", "Creando usuario: $request")
                RetrofitClient.serviceLibros.createUsuario(request)
                postSuccess = true
                Log.d("UsuariosViewModel", "Usuario creado con éxito")
                fetchUsuarios()
            } catch (e: Exception) {
                postSuccess = false
                postErrorMessage = "Error al guardar: ${e.message}"
                Log.e("UsuariosViewModel", "Error: ${e.message}", e)
            } finally {
                isPosting = false
            }
        }
    }

    fun resetPostState() {
        postSuccess = null
        postErrorMessage = null
    }
}
