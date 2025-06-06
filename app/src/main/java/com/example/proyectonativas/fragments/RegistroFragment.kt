package com.example.proyectonativas.fragments

import android.content.Context.MODE_PRIVATE
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.proyectonativas.Constantes
import com.example.proyectonativas.R
import com.example.proyectonativas.adapters.productosAdapter
import com.example.proyectonativas.modelos.Producto
import com.example.proyectonativas.modelos.Usuario
import retrofit2.Retrofit
import com.example.proyectonativas.servicios.UsuarioService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.converter.gson.GsonConverterFactory

class RegistroFragment : Fragment() {
    private lateinit var sharedPreference: SharedPreferences
    private lateinit var editTextNombres: EditText
    private lateinit var editTextApellido: EditText
    private lateinit var editTextTelefono: EditText
    private lateinit var editTextCorreo: EditText
    private lateinit var editTextContrasena: EditText
    private lateinit var editTextRecontrasena: EditText
    private lateinit var buttonRegistro: Button
    private lateinit var TyC: CheckBox

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_registro, container, false)


                editTextNombres = view.findViewById(R.id.nombre)
                editTextApellido = view.findViewById(R.id.apellido)
                editTextCorreo = view.findViewById(R.id.correo)
                editTextTelefono = view.findViewById(R.id.telefono)
                editTextContrasena = view.findViewById(R.id.password)
                editTextRecontrasena = view.findViewById(R.id.password2)
                buttonRegistro = view.findViewById(R.id.button_registro)
                TyC = view.findViewById(R.id.TyC_regitro)

                //Archivo de almacenamiento local
                sharedPreference = requireContext().getSharedPreferences("UserData", MODE_PRIVATE)

                buttonRegistro.setOnClickListener {
                    if (ValidarCampos()) {
                        GuardarDatos()
                        findNavController(this).navigate(R.id.iniciosesion)
                    }
                }
        
        return view
    }

    private fun ValidarCampos(): Boolean {
        val nombres = editTextNombres.text.toString().trim()
        val apellido = editTextApellido.text.toString().trim()
        val correo = editTextCorreo.text.toString().trim()
        val emailPattern = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}"
        val telefono = editTextTelefono.text.toString().trim()
        val contrasena = editTextContrasena.text.toString().trim()
        val recontrasena = editTextRecontrasena.text.toString().trim()
        val TeryCond = TyC.isChecked

        if (nombres.isEmpty()) {
            Toast.makeText(requireContext(), "el campo nombre es requerido", Toast.LENGTH_LONG).show()
            return false
        } else if (apellido.isEmpty()) {
            Toast.makeText(requireContext(), "el campo  apellido es requerido", Toast.LENGTH_LONG).show()
            return false
        } else if (correo.isEmpty()) {
            Toast.makeText(requireContext(), "el campo correo esta vacio", Toast.LENGTH_LONG).show()
            return false
        } else if (!correo.matches(emailPattern.toRegex())) {
            Toast.makeText(
                requireContext(),
                "debe ser correo electronico debe tener @ y .com o .edu",
                Toast.LENGTH_LONG
            ).show()
            return false
        }
        else if (telefono.isEmpty()) {
            Toast.makeText(requireContext(), "el campo es telefono requerido", Toast.LENGTH_LONG).show()
            return false
        } else if (contrasena.isEmpty()) {
            Toast.makeText(requireContext(), "el campo es contraseña requerido", Toast.LENGTH_LONG).show()
            return false
        }else if (contrasena != recontrasena){
            Toast.makeText(requireContext(), "la contraseña es diferente", Toast.LENGTH_LONG).show()
            return false
        }else if(!TeryCond){
            Toast.makeText(requireContext(), "Debe aceptar los términos y condiciones", Toast.LENGTH_LONG).show()
            return false
        }
        return true
    }

    private fun GuardarDatos(){
        val editor = sharedPreference.edit()

        val retrofit = Retrofit.Builder()
            .baseUrl(Constantes.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val usuarioService = retrofit.create(UsuarioService::class.java)

        val nuevoUsuario = Usuario()
        nuevoUsuario.setNombre(editTextNombres.text.toString().trim())
        nuevoUsuario.setApellido(editTextApellido.text.toString().trim())
        nuevoUsuario.setCorreo(editTextCorreo.text.toString().trim())
        nuevoUsuario.setTelefono(editTextTelefono.text.toString().trim())
        nuevoUsuario.setContrasenia(editTextContrasena.text.toString().trim())


        val call = usuarioService.crearUsuario(nuevoUsuario)

        call.enqueue(object : Callback<Usuario> {
            //Si la solicitud fue exitosa se ejecuta esta funcion
            //Este metodo recibe dos parametros, la llamada o solicitud que se le hizo
            //Y la respuesta que tuvimosd desde la API
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                //Si la respuesta viene con un codigo de exito se ejecuta el iff
                if (response.isSuccessful && response.body() != null) {
                    Log.e("Usuario", "Usuario creado exitosamente")
                } else {
                    Log.e("Error", "Error En la respuesta: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                Log.e("Error", "Error en la solicitud: ${t.message}")
            }
        })

        editor.putString("nombres",editTextNombres.text.toString().trim())
        editor.putString("apellidos",editTextApellido.text.toString().trim())
        editor.putString("correo",editTextCorreo.text.toString().trim())
        editor.putString("telefono",editTextTelefono.text.toString().trim())
        editor.putString("contrasena",editTextContrasena.text.toString().trim())
        editor.apply()

        Toast.makeText(requireContext(),"registro exitoso", Toast.LENGTH_LONG).show()

    }

    override fun onStart() {
        super.onStart()
        Log.d("Activity registrar", "esta en estado on start")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Activity registrar", "esta en estado onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Activity registrar", "esta en estado onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Activity registrar", "esta en estado onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("Activity registrar", "esta en estado onDestroy")

    }
}