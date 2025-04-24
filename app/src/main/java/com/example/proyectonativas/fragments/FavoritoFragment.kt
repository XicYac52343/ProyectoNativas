package com.example.proyectonativas.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.proyectonativas.R

class FavoritoFragment : Fragment() {
    private lateinit var mas: Button
    private lateinit var menos: Button
    private lateinit var numero: TextView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favoritos, container, false)

        mas = view.findViewById(R.id.btnaume)
        mas.setOnClickListener {
            actualizarNumeromas()
        }
        menos = view.findViewById(R.id.btndism)
        menos.setOnClickListener {
            actualizarNumeromenos()

        }
        numero = view.findViewById(R.id.cantidadinicial)


        return view
    }

    private fun actualizarNumeromas() {
        val cantidadactualida = numero.text.toString().toInt()
        numero.text = (cantidadactualida + 1).toString()

    }

    private fun actualizarNumeromenos() {
        if (numero.text.toString().toInt() > 0) {
            val cantidadactualida = numero.text.toString().toInt()
            numero.text = (cantidadactualida - 1).toString()
        } else {
            Toast.makeText(requireContext(), "No se puede decrementar más", Toast.LENGTH_SHORT)
                .show()
        }

    }
}