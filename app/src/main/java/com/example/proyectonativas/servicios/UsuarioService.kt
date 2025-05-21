package com.example.proyectonativas.servicios

import com.example.proyectonativas.modelos.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface UsuarioService {
    @GET("api/usuario")
    fun getUsuarios(): Call<List<Usuario>>

    @GET("api/usuario/user")
    fun getUsuarioById(@Query("id") usuarioID: Int): Call<Usuario>

    @POST("api/usuario")
    //Retrofit convierte el usuario de kotlin a Json (BODY)
    //La respuesta esperada por la API es el call
    fun crearUsuario(@Body usuario: Usuario): Call<Usuario>

    @PUT("api/usuario/productoID")
    fun actualizarUsuario(@Query("productoID") productoID: Int, @Body usuario: Usuario): Call<Usuario>
}