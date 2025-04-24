package com.example.proyectonativas.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.proyectonativas.R

class CategoriasFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_categorias, container, false)

        val iv_primerProductoCategorias = view.findViewById<ImageView>(R.id.iv_primerProductoCategorias)

        iv_primerProductoCategorias.setOnClickListener{
            findNavController().navigate(R.id.action_categoriaAProducto)
        }
        return view
    }
}