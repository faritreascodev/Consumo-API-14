package com.example.menudrawer.model

data class Usuario(
    val _id: String? = null,
    val nombre: String,
    val correo: String,
    val password: String,
    val rol: String = "USER"
)
