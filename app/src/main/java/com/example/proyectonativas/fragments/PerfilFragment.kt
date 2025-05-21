package com.example.proyectonativas.fragments

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.NavHostFragment.Companion
import androidx.navigation.fragment.findNavController
import com.example.proyectonativas.Constantes
import com.example.proyectonativas.R
import com.example.proyectonativas.modelos.Item
import com.example.proyectonativas.modelos.Usuario
import com.example.proyectonativas.servicios.ItemService
import com.example.proyectonativas.servicios.UsuarioService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class PerfilFragment : Fragment() {
    private lateinit var btneditar: Button
    private lateinit var bt_historialPedidos: Button
    private lateinit var getTextNombres: String
    private lateinit var getTextApellido: String
    private lateinit var getTextTelefono: String
    private lateinit var getTextCorreo: String
    private lateinit var TextNombres: TextView
    private lateinit var TextApellido: TextView
    private lateinit var TextCorreo: TextView
    private lateinit var TextTelefono: TextView
    private lateinit var sharedPreference: SharedPreferences
    private var usuarioIniciado: Int = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)

        sharedPreference = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)
        usuarioIniciado = sharedPreference.getInt("usuarioIniciado", 0)

        TextNombres = view.findViewById(R.id.nombreuser)
        TextApellido = view.findViewById(R.id.apellidouser)
        TextCorreo = view.findViewById(R.id.correouser)
        TextTelefono = view.findViewById(R.id.telefonouser)
        bt_historialPedidos = view.findViewById(R.id.bt_historialPedidos)

        ObtenerDatos()
        btneditar = view.findViewById(R.id.btneditarinfo)

        btneditar.setOnClickListener {
            findNavController().navigate(R.id.EditarPerfilFragment)
        }

        bt_historialPedidos.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.historialPedidosFragment)
        }
        return view
    }

    private fun ObtenerDatos() {
        val retrofit = Retrofit.Builder()
            .baseUrl(Constantes.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val usuarioService = retrofit.create(UsuarioService::class.java)

        val call = usuarioService.getUsuarioById(usuarioIniciado)

        call.enqueue(object : Callback<Usuario> {
            //Si la solicitud fue exitosa se ejecuta esta funcion
            //Este metodo recibe dos parametros, la llamada o solicitud que se le hizo
            //Y la respuesta que tuvimosd desde la API
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                //Si la respuesta viene con un codigo de exito se ejecuta el iff
                if (response.isSuccessful && response.body() != null) {
                    val usuarioObjeto = response.body()
                    if (usuarioObjeto != null) {
                        TextNombres.text = usuarioObjeto.nombre
                        TextApellido.text = usuarioObjeto.apellido
                        TextCorreo.text = usuarioObjeto.correo
                        TextTelefono.text = usuarioObjeto.telefono
                    }
                } else {
                    Log.e("Error", "Error En la respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                Log.e("Error", "Error en la solicitud: ${t.message}")
            }
        })

    }
}



