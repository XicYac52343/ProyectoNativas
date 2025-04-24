package com.example.proyectonativas.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.proyectonativas.R

class GestionarPedidosFragment : Fragment() {
    private lateinit var tv_idPrimerGestionPedi : TextView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gestion_pedidos, container, false)

        tv_idPrimerGestionPedi = view.findViewById(R.id.tv_idPrimerGestionPedi)

        tv_idPrimerGestionPedi.setOnClickListener{
            findNavController().navigate(R.id.action_pedidoADetallePedido)
        }

        return view
    }
}