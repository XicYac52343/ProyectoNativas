package com.example.proyectonativas.fragments

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import com.example.proyectonativas.R

class CarritoFragment : Fragment() {
    @SuppressLint("MissingInflatedId")
    private lateinit var bt_menosPrimerProCarrito : Button
    private lateinit var bt_masPrimerProCarrito : Button


    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var tv_nombrePrimerProductoCarrito: TextView
    private lateinit var tv_cantidadPrimerProCarrito : TextView
    private lateinit var tv_precioUniPrimerProductoCarrito : TextView
    private lateinit var linearLayoutCarrito : LinearLayout
    private lateinit var tv_totalPrimerProCarrito : TextView
    private lateinit var tv_totalCarrito : TextView
    private lateinit var tv_vacioCarrito : TextView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_carrito, container, false)

        /*bt_masPrimerProCarrito = view.findViewById(R.id.bt_masPrimerProCarrito)
        bt_masPrimerProCarrito.setOnClickListener{
            aumentarCantidad()
        }

        bt_menosPrimerProCarrito = view.findViewById(R.id.bt_menosPrimerProCarrito)
        bt_menosPrimerProCarrito.setOnClickListener{
            disminuirCantidad()
        }

        tv_cantidadPrimerProCarrito = view.findViewById(R.id.tv_cantidadPrimerProCarrito)*/



        linearLayoutCarrito = view.findViewById(R.id.linearLayoutCarrito)
        //linearProductos = view.findViewById(R.id.linearProductos)
        //linearProductos.visibility = View.GONE
        //tv_nombreProducto = view.findViewById(R.id.tv_nombreProducto);
        sharedPreferences = requireActivity().getSharedPreferences("carritoProductos", MODE_PRIVATE)



        val cantidadPerfume = sharedPreferences.getInt("cantidadCompradaPerfume", 0)

        if(cantidadPerfume > 0){
            //linearProductos.visibility = View.VISIBLE
            val inflater = LayoutInflater.from(requireContext())
            val nuevoItem = inflater.inflate(R.layout.producto_item_layout, linearLayoutCarrito, false)

            tv_nombrePrimerProductoCarrito = nuevoItem.findViewById<TextView>(R.id.tv_nombrePrimerProductoCarrito)
            tv_nombrePrimerProductoCarrito.text = getString(R.string.nombreProducto).toString().trim()

            tv_cantidadPrimerProCarrito = nuevoItem.findViewById<TextView>(R.id.tv_cantidadPrimerProCarrito)
            tv_cantidadPrimerProCarrito.text = cantidadPerfume.toString().trim()

            tv_precioUniPrimerProductoCarrito = nuevoItem.findViewById(R.id.tv_precioUniPrimerProductoCarrito)
            tv_precioUniPrimerProductoCarrito.text = getString(R.string.precioLabel).trim() + getString(R.string.precioProducto).toString().trim()

            tv_totalPrimerProCarrito = nuevoItem.findViewById(R.id.tv_totalPrimerProCarrito)
            var subTotal = cantidadPerfume * 120000
            tv_totalPrimerProCarrito.text = getString(R.string.simboloPesos).toString().trim() + subTotal.toString().trim()

            tv_totalCarrito = view.findViewById(R.id.tv_totalCarrito)
            tv_totalCarrito.text = getString(R.string.simboloPesos).toString().trim() + subTotal.toString().trim()

            linearLayoutCarrito.addView(nuevoItem)

            tv_vacioCarrito = view.findViewById(R.id.tv_vacioCarrito)
            tv_vacioCarrito.visibility = View.GONE

            //tv_nombreProducto.text = getString(R.string.celularNombre)
        }else{
            //linearProductos.visibility = View.GONE
        }

        return view
    }

    private fun aumentarCantidad(){
        val cnatidadActual = tv_cantidadPrimerProCarrito.text.toString().toInt()
        val nuevaCantidad = cnatidadActual + 1
        tv_cantidadPrimerProCarrito.text = nuevaCantidad.toString().trim()
    }

    private fun disminuirCantidad(){
        if(tv_cantidadPrimerProCarrito.text.toString().toInt() > 0){
            val cnatidadActual = tv_cantidadPrimerProCarrito.text.toString().toInt()
            val nuevaCantidad = cnatidadActual - 1
            tv_cantidadPrimerProCarrito.text = nuevaCantidad.toString().trim()
        }else{
            Toast.makeText(requireContext(), "No se puede decrementar más", Toast.LENGTH_SHORT)
                .show()
        }

    }
}