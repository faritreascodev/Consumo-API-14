package com.example.menudrawer.model

import com.google.gson.annotations.SerializedName

data class LibroResponse(
    val status: Boolean,
    val result: List<Libro>
)

// el model para recibir datos
data class Libro(
    @SerializedName("_id")
    val id: String,
    val codlibro: String,
    val nombre: String,
    val autor: String,
    val precio: Double,
    @SerializedName("__v")
    val version: Int? = null
)

// para el model de enviar datos
data class LibroRequest(
    val codlibro: String,
    val nombre: String,
    val autor: String,
    val precio: Double
)
