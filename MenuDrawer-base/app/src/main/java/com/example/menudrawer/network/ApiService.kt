package com.example.menudrawer.network

import com.example.menudrawer.model.User
import com.example.menudrawer.model.LibroRequest
import com.example.menudrawer.model.LibroResponse
import com.example.menudrawer.model.LibroSingleResponse
import com.example.menudrawer.model.UsuarioRequest
import com.example.menudrawer.model.UsuarioResponse
import com.example.menudrawer.model.SimpleResponse
import retrofit2.http.*

interface ApiService {
    @GET("users")
    suspend fun getUsers(): List<User>

    @GET("api/libros/list")
    suspend fun getLibros(): LibroResponse

    @POST("api/libros/create")
    suspend fun createLibro(@Body request: LibroRequest): Unit

    @GET("api/libros/bycode/{codlibro}")
    suspend fun getLibroByCode(@Path("codlibro") cod: String): LibroSingleResponse

    @DELETE("api/libros/delete/{codlibro}")
    suspend fun deleteLibro(@Path("codlibro") cod: String): SimpleResponse

    @PUT("api/libros/update/{codlibro}")
    suspend fun updateLibro(
        @Path("codlibro") cod: String,
        @Body request: LibroRequest
    ): SimpleResponse

    @POST("api/usuarios/create")
    suspend fun createUsuario(@Body request: UsuarioRequest): Unit

    @GET("api/usuarios/list")
    suspend fun getUsuarios(): UsuarioResponse
}
