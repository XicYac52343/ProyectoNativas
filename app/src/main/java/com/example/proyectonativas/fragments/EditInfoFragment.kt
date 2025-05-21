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
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.proyectonativas.Constantes
import com.example.proyectonativas.R
import com.example.proyectonativas.modelos.Usuario
import com.example.proyectonativas.servicios.UsuarioService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class EditInfoFragment : Fragment() {

    private lateinit var sharedPreference: SharedPreferences
    private lateinit var Editnombres_perfil: EditText
    private lateinit var Editapellido_perfil: EditText
    private lateinit var Editcorreo_perfil: EditText
    private lateinit var Edittelefono_perfil: EditText
    private lateinit var getnombres_perfil: String
    private lateinit var getapellido_perfil: String
    private lateinit var getcorreo_perfil: String
    private lateinit var gettelefono_perfil: String
    private lateinit var btnGuardarInfo_editperfil: Button
    private lateinit var bt_historialPedidos: Button
    private var usuarioObjeto: Usuario? = null
    private var usuarioIniciado: Int = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_editperfil, container, false)
        sharedPreference = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)
        usuarioIniciado = sharedPreference.getInt("usuarioIniciado", 0)

        Editnombres_perfil = view.findViewById(R.id.et_nombreEditarUsuario);
        Editapellido_perfil = view.findViewById(R.id.et_apellidoEditarUsuario);
        Editcorreo_perfil = view.findViewById(R.id.correouser_edit);
        Edittelefono_perfil = view.findViewById(R.id.telefonouser_edit);
        btnGuardarInfo_editperfil = view.findViewById(R.id.btnsave_edit)

        ObtenerDatos()

        btnGuardarInfo_editperfil.setOnClickListener {
            Editardatos()
            Toast.makeText(requireContext(), "Datos guardados", Toast.LENGTH_SHORT).show()
            findNavController(this).navigate(R.id.MiPerfilFragment)
        }

        return view
    }

    private fun Editardatos() {

        val nombre = Editnombres_perfil.text.toString().trim()
        val apellido = Editapellido_perfil.text.toString().trim()
        val correo = Editcorreo_perfil.text.toString().trim()
        val telefono = Edittelefono_perfil.text.toString().trim()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constantes.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val usuarioService = retrofit.create(UsuarioService::class.java)
        usuarioObjeto!!.nombre  = nombre
        usuarioObjeto!!.apellido = apellido
        usuarioObjeto!!.correo = correo
        usuarioObjeto!!.telefono = telefono

        val call = usuarioService.actualizarUsuarioAndroid(usuarioObjeto!!)

        call.enqueue(object : Callback<Usuario> {
            //Si la solicitud fue exitosa se ejecuta esta funcion
            //Este metodo recibe dos parametros, la llamada o solicitud que se le hizo
            //Y la respuesta que tuvimosd desde la API
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                //Si la respuesta viene con un codigo de exito se ejecuta el iff
                if (response.isSuccessful && response.body() != null) {
                    Log.d("User", "Usuario actualizado correctamente")

                } else {
                    Log.e("Error", "Error En la respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                Log.e("Error", "Error en la solicitud: ${t.message}")
            }
        })

    }

    private fun ObtenerDatos(){
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
                    usuarioObjeto = response.body()
                    if (usuarioObjeto != null) {
                        Editnombres_perfil.setText(usuarioObjeto!!.nombre)
                        Editcorreo_perfil.setText(usuarioObjeto!!.correo)
                        Editapellido_perfil.setText(usuarioObjeto!!.apellido)
                        Edittelefono_perfil.setText(usuarioObjeto!!.telefono)
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