package com.example.menudrawer.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.menudrawer.model.Libro
import com.example.menudrawer.model.LibroRequest
import com.example.menudrawer.network.RetrofitClient
import kotlinx.coroutines.launch

class LibrosViewModel : ViewModel() {
    // Estados existentes para GET
    var libroList: List<Libro> by mutableStateOf(listOf())
    var errorMessage: String by mutableStateOf("")
    var isLoading: Boolean by mutableStateOf(false)
    var selectedLibro by mutableStateOf<Libro?>(null)
        private set
    var isLoadingDetail by mutableStateOf(false)
        private set
    var detailErrorMessage by mutableStateOf<String?>(null)
        private set

    // Estados para POST
    var isPosting: Boolean by mutableStateOf(false)
    var postSuccess: Boolean? by mutableStateOf(null)
    var postErrorMessage: String? by mutableStateOf(null)

    //  Estados para búsqueda
    var libroEncontrado: Libro? by mutableStateOf(null)
    var busquedaError: String? by mutableStateOf(null)
    var isBuscando: Boolean by mutableStateOf(false)

    // Estados para DELETE
    var deleteSuccess: Boolean? by mutableStateOf(null)
    var deleteError: String? by mutableStateOf(null)
    var isDeleting: Boolean by mutableStateOf(false)

    // Estados para UPDATE
    var updateSuccess: Boolean? by mutableStateOf(null)
    var updateError: String? by mutableStateOf(null)
    var isUpdating: Boolean by mutableStateOf(false)

    fun getLibroList() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = ""
            try {
                Log.d("LibrosViewModel", "Iniciando llamada a API...")
                val response = RetrofitClient.serviceLibros.getLibros()
                Log.d("LibrosViewModel", "Respuesta: status=${response.status}, libros=${response.result.size}")

                if (response.status) {
                    libroList = response.result
                } else {
                    errorMessage = "No se encontraron libros"
                }
            } catch (e: Exception) {
                Log.e("LibrosViewModel", "Error completo: ${e.message}", e)
                errorMessage = "Error: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

    fun createLibro(request: LibroRequest) {
        viewModelScope.launch {
            isPosting = true
            postSuccess = null
            postErrorMessage = null

            try {
                Log.d("LibrosViewModel", "Creando libro: $request")
                RetrofitClient.serviceLibros.createLibro(request)
                postSuccess = true
                Log.d("LibrosViewModel", "Libro creado exitosamente")
                getLibroList()
            } catch (e: Exception) {
                postSuccess = false
                postErrorMessage = "Error al guardar: ${e.message}"
                Log.e("LibrosViewModel", "Error al crear libro: ${e.message}", e)
            } finally {
                isPosting = false
            }
        }
    }

    fun resetPostState() {
        postSuccess = null
        postErrorMessage = null
    }

    // Buscar libro por código
    fun buscarLibroPorCodigo(codigo: String) {
        viewModelScope.launch {
            isBuscando = true
            libroEncontrado = null
            busquedaError = null

            try {
                val response = RetrofitClient.serviceLibros.getLibroByCode(codigo)
                if (response.status) {
                    libroEncontrado = response.result
                } else {
                    busquedaError = "Libro no encontrado"
                }
            } catch (e: Exception) {
                busquedaError = "Error: ${e.message}"
            } finally {
                isBuscando = false
            }
        }
    }

    // Eliminar libro
    fun eliminarLibro(codigo: String) {
        viewModelScope.launch {
            isDeleting = true
            deleteSuccess = null
            deleteError = null

            try {
                val response = RetrofitClient.serviceLibros.deleteLibro(codigo)
                if (response.status) {
                    deleteSuccess = true
                    getLibroList() // Recargar lista
                } else {
                    deleteError = response.message
                }
            } catch (e: Exception) {
                deleteError = "Error al eliminar: ${e.message}"
            } finally {
                isDeleting = false
            }
        }
    }

    fun resetDeleteState() {
        deleteSuccess = null
        deleteError = null
    }

    // Actualizar libro
    fun actualizarLibro(codigo: String, request: LibroRequest) {
        viewModelScope.launch {
            isUpdating = true
            updateSuccess = null
            updateError = null

            try {
                val response = RetrofitClient.serviceLibros.updateLibro(codigo, request)
                if (response.status) {
                    updateSuccess = true
                    getLibroList() // Recargar lista
                } else {
                    updateError = response.message
                }
            } catch (e: Exception) {
                updateError = "Error al actualizar: ${e.message}"
            } finally {
                isUpdating = false
            }
        }
    }

    fun getLibroById(codLibro: String) {
        viewModelScope.launch {
            isLoadingDetail = true
            detailErrorMessage = null
            selectedLibro = null
            try {
                val libro = RetrofitClient.serviceLibros.getLibroDetalle(codLibro)
                selectedLibro = libro
                Log.d("LibrosViewModel", "Detalle cargado: ${libro.nombre}")
            } catch (e: Exception) {
                detailErrorMessage = "Error al cargar detalle: ${e.message}"
                Log.e("LibrosViewModel", "Error detalle: ${e.message}", e)
            } finally {
                isLoadingDetail = false
            }
        }
    }

    // limpiar el detalle
    fun clearSelection() {
        selectedLibro = null
        detailErrorMessage = null
    }

    fun resetUpdateState() {
        updateSuccess = null
        updateError = null
    }

    fun resetBusqueda() {
        libroEncontrado = null
        busquedaError = null
    }
}
