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
import com.example.proyectonativas.LoginregistroActivity
import com.example.proyectonativas.R

class RecuperarcontraFragment : Fragment() {


    private lateinit var btnRecuperar: Button
    private lateinit var correoGuardado: String
    private lateinit var correoIntruducido: EditText
    private lateinit var sharedPreference: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_recuperacion, container, false)

        sharedPreference = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)
        btnRecuperar = view.findViewById(R.id.btn_recuperar)


        btnRecuperar.setOnClickListener {
            correoGuardado = sharedPreference.getString("correo", "Useremail") ?: "Useremail";
            correoIntruducido = view.findViewById(R.id.recontrasena_correo);
            val asigCorreo = correoIntruducido.text.toString().trim();

            if (asigCorreo == correoGuardado) {
                Toast.makeText(
                    requireContext(), "Se ha enviado un correo de recuperacion de contraseña email $correoGuardado", Toast.LENGTH_SHORT).show()
                rediLogin()
            } else {
                Toast.makeText(requireContext(), "el correo no ha sido encontrado", Toast.LENGTH_SHORT).show()
            }

        }
        return view
        }

    private fun rediLogin() {
        val intent = Intent(requireContext(), LoginregistroActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }
}