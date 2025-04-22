package com.example.proyectonativas.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.proyectonativas.MainActivity
import com.example.proyectonativas.R

class iniciosesionFragment : Fragment() {

    private lateinit var btniniciarsesion: Button
    private lateinit var sharedPreference: SharedPreferences
    private lateinit var getTextCorreo: String
    private lateinit var getTextContrasena: String
    private lateinit var Acorreo: EditText
    private lateinit var Acontrasena: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
        ): View? {
        val view = inflater.inflate(R.layout.fragment_iniciosesion, container, false)
        sharedPreference = requireContext().getSharedPreferences("MiPref", Context.MODE_PRIVATE)

        datos()
        getTextCorreo = sharedPreference.getString("correo", "Useremail") ?: "Useremail";
        getTextContrasena =
            sharedPreference.getString("contrasena", "Usercontrasena") ?: "Usercontrasena";

        Acorreo = view.findViewById(R.id.correo)
        Acontrasena = view.findViewById(R.id.contrasena)

      btniniciarsesion=view.findViewById(R.id.btniniciarsesion)
        btniniciarsesion.setOnClickListener {
            iniciarSesion()
        }



        return view
    }
    private fun iniciarSesion() {
        val correoIngresado = Acorreo.text.toString().trim()
        val contrasenaIngresado = Acontrasena.text.toString().trim()

        if (correoIngresado == getTextCorreo && contrasenaIngresado == getTextContrasena) {
            // Login exitoso
            Toast.makeText(requireContext(), "Login exitoso", Toast.LENGTH_SHORT).show()
          main()

        } else {
            // Login fallido
            Toast.makeText(requireContext(), "Correo o contraseña incorrectos", Toast.LENGTH_SHORT).show()
        }
    }

    private fun datos(){
            val editor = sharedPreference.edit()
        editor.putString("correo", "nicolaspoveda000@gmail.com")
        editor.putString("contrasena", "1234")
        editor.apply()
    }
    private fun main(){
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }





}