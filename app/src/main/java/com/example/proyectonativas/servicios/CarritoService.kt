package com.example.proyectonativas.servicios

import com.example.proyectonativas.modelos.Carrito
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CarritoService {
    @GET("api/carrito/usuarioID")
    fun getCarritoByUsuario(@Query("id") id: Int): Call<Carrito>
}