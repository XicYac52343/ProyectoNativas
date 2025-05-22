package com.example.proyectonativas.servicios

import com.example.proyectonativas.modelos.Pedido
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface PedidoService {
    @POST("api/pedidos")
    fun crearProductoByUsuario(@Query("usuarioID") usuarioID : Int) : Call<Pedido>

    @GET("api/pedidos")
    fun getPedidos() : Call<List<Pedido>>

    @GET("api/pedidos/usuarioID")
    fun getPedidosByUsuario(@Query("usuarioID") usuarioID : Int): Call<List<Pedido>>
}