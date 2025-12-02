package com.example.menudrawer.model

data class UsuarioRequest(
    val nombre: String,
    val correo: String,
    val password: String,
    val rol: String = "USER"
)
