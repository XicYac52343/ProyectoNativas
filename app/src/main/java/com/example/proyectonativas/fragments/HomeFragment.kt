package com.example.proyectonativas.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import com.example.proyectonativas.R

class HomeFragment : Fragment(){
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        sharedPreferences = requireActivity().getSharedPreferences("carritoProductos", MODE_PRIVATE)

        val editor = sharedPreferences.edit()

        editor.putInt("cantidadPerfume", 10)
        editor.putInt("cantidadCompradaPerfume", 0)

        editor.apply()
        return view
    }
}