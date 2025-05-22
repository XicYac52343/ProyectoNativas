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
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.proyectonativas.R
import com.example.proyectonativas.activities.MainActivity

class RegistroGoogleFragment: Fragment() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var editTextTelefono: EditText
    private lateinit var btnguardar: Button


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_registro_google, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)

        editTextTelefono = view.findViewById(R.id.telefonoGoogle)


        btnguardar = view.findViewById(R.id.button_registro_google)
        btnguardar.setOnClickListener {
            guardarTelefono()

            val intent = Intent(requireContext(), com.example.proyectonativas.MainActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        }


        return view
    }

    private fun guardarTelefono() {
        val editor = sharedPreferences.edit()
        editor.putString("telefono", editTextTelefono.text.toString().trim())
        editor.apply()

        Toast.makeText(requireContext(),"registro exitoso", Toast.LENGTH_LONG).show()

    }

}