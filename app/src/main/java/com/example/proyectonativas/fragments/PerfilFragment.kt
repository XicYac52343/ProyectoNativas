package com.example.proyectonativas.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.proyectonativas.R

class PerfilFragment: Fragment() {
    private lateinit var btneditar: Button
    private lateinit var getTextNombres: String
    private lateinit var getTextApellido: String
    private lateinit var getTextTelefono: String
    private lateinit var getTextCorreo: String
    private lateinit var TextNombres: TextView
    private lateinit var TextApellido: TextView
    private lateinit var TextCorreo: TextView
    private lateinit var TextTelefono: TextView
    private lateinit var sharedPreference: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_perfil, container, false)

                sharedPreference = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)
                TextNombres = view.findViewById(R.id.nombreuser)
                TextApellido = view.findViewById(R.id.apellidouser)
                TextCorreo = view.findViewById(R.id.correouser)
                TextTelefono = view.findViewById(R.id.telefonouser)

                ObtenerDatos()
                btneditar=view.findViewById(R.id.btneditarinfo)

                btneditar.setOnClickListener{
                    findNavController().navigate(R.id.EditarPerfilFragment)
                }
                return view
            }
    private fun ObtenerDatos() {


        getTextNombres = sharedPreference.getString("nombres", "Username") ?: "username";
        getTextApellido = sharedPreference.getString("apellidos", "Userlastname") ?: "Userlastname";
        getTextCorreo = sharedPreference.getString("correo", "Useremail") ?: "Useremail";
        getTextTelefono = sharedPreference.getString("telefono", "Usertelefono") ?: "Usertelefono";


        TextNombres.text = getTextNombres
        TextApellido.text = getTextApellido
        TextCorreo.text = getTextCorreo
        TextTelefono.text = getTextTelefono

    }




        }



