package com.example.proyectonativas.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import com.example.proyectonativas.R

class ProductoFragment : Fragment() {
    private lateinit var bt_menosProducto: Button
    private lateinit var bt_masProducto: Button
    private lateinit var bt_añadirProductoDetalles: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var tv_cantidadProducto: TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_producto, container, false)

        bt_añadirProductoDetalles = view.findViewById(R.id.bt_añadirProductoDetalles)

        sharedPreferences = requireActivity().getSharedPreferences("carritoProductos", MODE_PRIVATE)

        bt_masProducto = view.findViewById(R.id.bt_masProducto)
        bt_masProducto.setOnClickListener {
            aumentarCantidad()
        }

        bt_menosProducto = view.findViewById(R.id.bt_menosProducto)
        bt_menosProducto.setOnClickListener {
            disminuirCantidad()
        }

        bt_añadirProductoDetalles.setOnClickListener {
            var cantidadTraida = sharedPreferences.getInt("cantidadPerfume", 0);
            var cantidadDigitada = tv_cantidadProducto.text.toString().trim()

            if (cantidadDigitada.isNotEmpty()) {
                var cantidadDigitada1 = cantidadDigitada.toString().toInt()
                if (cantidadDigitada1 > 0) {
                    var cantidadNueva = cantidadTraida - cantidadDigitada1
                    if (cantidadNueva >= 0) {
                        agregarProducto(cantidadNueva.toString(), cantidadDigitada.toString())
                    }else {
                        Toast.makeText(
                            requireContext(),
                            "No hay Stock",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Ingrese una cantidad adecuada",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } else {
                Toast.makeText(
                    requireContext(),
                    "Ingrese una cantidad",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        tv_cantidadProducto = view.findViewById(R.id.tv_cantidadProducto)

        return view
    }

    private fun aumentarCantidad() {
        val cnatidadActual = tv_cantidadProducto.text.toString().toInt()
        val nuevaCantidad = cnatidadActual + 1
        tv_cantidadProducto.text = nuevaCantidad.toString().trim()
    }

    private fun disminuirCantidad() {
        if (tv_cantidadProducto.text.toString().toInt() > 0) {
            val cnatidadActual = tv_cantidadProducto.text.toString().toInt()
            val nuevaCantidad = cnatidadActual - 1
            tv_cantidadProducto.text = nuevaCantidad.toString().trim()
        } else {
            Toast.makeText(requireContext(), "No se puede decrementar más", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun agregarProducto(cantidadProducto: String, cantidadComprada1: String) {
        sharedPreferences = requireActivity().getSharedPreferences("carritoProductos", MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        var cantidadComprada = sharedPreferences.getInt("cantidadCompradaPerfume", 0);
        var cantidadTotalComprada = cantidadComprada + cantidadComprada1.toString().toInt()

        //var productosTraidos = sharedPreferences.getString("listaProductos", "")
        // productosTraidos = productosTraidos + ", " + nombreProducto
        editor.putInt("cantidadPerfume", cantidadProducto.toString().toInt())
        editor.putInt("cantidadCompradaPerfume", cantidadTotalComprada.toString().toInt())
        editor.apply()
        Toast.makeText(requireContext(), "Agregado Exitosamente", Toast.LENGTH_SHORT).show()
    }
}