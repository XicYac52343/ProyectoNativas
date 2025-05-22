package com.example.proyectonativas.servicios

import com.example.proyectonativas.modelos.Producto
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductoService {
    @GET("api/productos")
    fun getProductos(): Call<List<Producto>>

    @GET("api/productos/productoID")
    fun getProductoById(@Query("id") id: Int): Call<Producto>

    @GET("api/productos/homeAndroid")
    fun getProductosHome(): Call<List<Producto>>

    @GET("api/productos/productosFavoritos")
    fun getProductosFavoritos(@Query("id") id: Int): Call<List<Producto>>

    @POST("api/productos/agregarProducto")
    fun agregarProducto(@Body producto: Producto): Call<Producto>
}