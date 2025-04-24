package com.example.proyectonativas.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.proyectonativas.R

class GestionarProductosFragment : Fragment() {
    private lateinit var bt_agregarProducto : Button
    private lateinit var bt_editarPrimerGestionPro : Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gestionar_productos, container, false)
        bt_agregarProducto = view.findViewById(R.id.bt_agregarProducto)
        bt_editarPrimerGestionPro = view.findViewById(R.id.bt_editarPrimerGestionPro)

        bt_agregarProducto.setOnClickListener {
            findNavController().navigate(R.id.action_gestionarProductosFragment_A_agregarProductoFragment)
        }

        bt_editarPrimerGestionPro.setOnClickListener {
            findNavController().navigate(R.id.action_gestionarProductosFragment_A_editarProductoFragment)
        }

        return view
    }
}