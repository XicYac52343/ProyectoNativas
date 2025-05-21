package com.example.proyectonativas.fragments

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import com.example.proyectonativas.Constantes
import com.example.proyectonativas.R
import com.example.proyectonativas.adapters.carritoAdapter
import com.example.proyectonativas.modelos.Carrito
import com.example.proyectonativas.servicios.CarritoService
import retrofit2.Call
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Callback
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectonativas.modelos.Pedido
import com.example.proyectonativas.servicios.PedidoService

class CarritoFragment : Fragment() {
    @SuppressLint("MissingInflatedId")
    private lateinit var bt_menosPrimerProCarrito : Button
    private lateinit var bt_masPrimerProCarrito : Button


    private lateinit var tv_cantidadPrimerProCarrito : TextView
    private lateinit var SharedPreferences : SharedPreferences
    private lateinit var rv_productoCarrito : RecyclerView
    private lateinit var tv_totalPrimerProCarrito : TextView
    private lateinit var tv_totalCarrito : TextView
    private lateinit var tv_vacioCarrito : TextView
    private lateinit var call : Call<Carrito>
    private lateinit var bt_comprarCarrito : Button
    private lateinit var carrito : Carrito

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

        tv_vacioCarrito = view.findViewById(R.id.tv_vacioCarrito)
        tv_totalCarrito = view.findViewById(R.id.tv_totalCarrito)
        bt_comprarCarrito = view.findViewById(R.id.bt_comprarCarrito)

        bt_comprarCarrito.setOnClickListener{
            if(validarStock()){
                realizarPedido()
            }
        }

        rv_productoCarrito = view.findViewById(R.id.rv_productoCarrito)
        getItems()

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

    private fun getItems(){
        val sharedPreferences = requireActivity().getSharedPreferences("UserData", MODE_PRIVATE)
        val usuarioIniciado = sharedPreferences.getInt("usuarioIniciado", 0)

        val retrofit = Retrofit.Builder()
            .baseUrl(Constantes.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val carritoService = retrofit.create(CarritoService::class.java)

        call = carritoService.getCarritoByUsuario(usuarioIniciado)

        call.enqueue(object : Callback<Carrito> {
            override fun onResponse(call: Call<Carrito>, response: Response<Carrito>){
                if(response.isSuccessful && response.body() != null){
                    //Los !! sirve para decir que nunca va a ser nulo ese dato
                    carrito = response.body()!!
                    if(carrito != null){
                        if(!carrito.item.isEmpty()){
                            tv_vacioCarrito.visibility = View.GONE
                        }else{
                            tv_vacioCarrito.visibility = View.VISIBLE
                        }
                        tv_totalCarrito.text = getString(R.string.totalLabel) + getString(R.string.simboloPesos) + carrito.totalCarrito
                        rv_productoCarrito.adapter = carritoAdapter(carrito.item)
                    }
                }else{
                    Log.e("Error", "Codigo de error: ${response.code()}")
                }
            }
            override fun onFailure(call : Call<Carrito>, t: Throwable){
                Log.e("Error", "Error en la obtencion del carrito: ${t.message}")
            }
        })
    }

    private fun validarStock(): Boolean {
        var carritoItems = carrito.item
        for(item in carritoItems){
            if(item.cantidad > item.producto.stock){
                Toast.makeText(requireContext(), "No hay suficiente stock de ${item.producto.nombre}, solo hay ${item.producto.stock}", Toast.LENGTH_SHORT).show()
                return false
            }
        }
        return true
    }

    private fun realizarPedido(){
        SharedPreferences = requireActivity().getSharedPreferences("UserData", MODE_PRIVATE)
        val usuarioIniciado = SharedPreferences.getInt("usuarioIniciado", 0)

        val retrofit = Retrofit.Builder()
            .baseUrl(Constantes.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val pedidoService = retrofit.create(PedidoService::class.java)

        val callPedido = pedidoService.crearProductoByUsuario(usuarioIniciado)

        callPedido.enqueue(object : Callback<Pedido>{
            override fun onResponse(call : Call<Pedido>, response : Response<Pedido>){
                if(response.isSuccessful && response.body() !=null){
                    rv_productoCarrito.adapter = carritoAdapter(emptyList())
                    tv_totalCarrito.text = getString(R.string.totalLabel) + getString(R.string.simboloPesos) + carrito.totalCarrito
                    tv_vacioCarrito.visibility = View.VISIBLE
                }else{
                    Log.e("Error", "Codigo de error: ${response.code()}")
                }
            }
            override fun onFailure(call : Call<Pedido>, t: Throwable){
                Log.e("Error", "Error en la creacion del pedido: ${t.message}")
            }
        })
    }
}