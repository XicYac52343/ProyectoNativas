package com.example.proyectonativas.servicios

import com.example.proyectonativas.modelos.Item
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface ItemService {

    @POST("api/items")
    fun crearItem(@Query("usuarioID") usuarioID: Int, @Body item: Item): Call<Item>}