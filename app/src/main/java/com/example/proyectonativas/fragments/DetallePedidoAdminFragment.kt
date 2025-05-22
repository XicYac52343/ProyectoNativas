package com.example.proyectonativas.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.proyectonativas.R

class DetallePedidoAdminFragment : Fragment() {
    private lateinit var sharedPreference: SharedPreferences
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_detalle_pedido, container, false)
        sharedPreference = requireContext().getSharedPreferences("UserData", Context.MODE_PRIVATE)

        return view
    }
}