package com.example.proyectonativas.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.proyectonativas.R

class GestionarUsuariosFragment : Fragment() {
    private lateinit var tv_idPrimerGestionUsu : TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gestion_usuarios, container, false)

        tv_idPrimerGestionUsu = view.findViewById(R.id.tv_idPrimerGestionUsu)

        tv_idPrimerGestionUsu.setOnClickListener {
            findNavController().navigate(R.id.action_gestionarUsuarioFragment_A_editarUsuarioAdminFragment)
        }
        return view
    }
}