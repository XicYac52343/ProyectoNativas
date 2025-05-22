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
import com.example.proyectonativas.modelos.Item
import com.example.proyectonativas.modelos.Pedido
import com.example.proyectonativas.modelos.Producto
import com.example.proyectonativas.servicios.ItemService
import com.example.proyectonativas.servicios.PedidoService

class CarritoFragment : Fragment(), carritoAdapter.cantidadListener {
    @SuppressLint("MissingInflatedId")
    private lateinit var bt_menosPrimerProCarrito: Button
    private lateinit var bt_masPrimerProCarrito: Button


    private lateinit var tv_cantidadPrimerProCarrito: TextView
    private lateinit var SharedPreferences: SharedPreferences
    private lateinit var rv_productoCarrito: RecyclerView
    private lateinit var tv_totalPrimerProCarrito: TextView
    private lateinit var tv_totalCarrito: TextView
    private lateinit var tv_vacioCarrito: TextView
    private lateinit var call: Call<List<Item>>
    private lateinit var bt_comprarCarrito: Button
    private lateinit var items: List<Item>
    private var totalCarrito = 0.0

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

        bt_comprarCarrito.setOnClickListener {
            if (tv_vacioCarrito.visibility != View.VISIBLE) {
                if (validarStock()) {
                    realizarPedido()
                }
            }else{
                Toast.makeText(requireContext(), "No hay productos", Toast.LENGTH_SHORT)
                    .show()
            }

        }
        rv_productoCarrito = view.findViewById(R.id.rv_productoCarrito)
        getItems()

        return view
    }

    override fun aumentarCantidad(item: Item) {
        val cnatidadActual = item.cantidad.toString().toInt()
        val nuevaCantidad = cnatidadActual + 1
        actualizarItemCarrito(nuevaCantidad, item.producto)
        getItems()
    }

    override fun disminuirCantidad(item: Item) {
        if (item.cantidad.toString().toInt() >= 0) {
            val cnatidadActual = item.cantidad.toString().toInt()
            val nuevaCantidad = cnatidadActual - 1
            actualizarItemCarrito(nuevaCantidad, item.producto)
            getItems()
        } else {
            Toast.makeText(requireContext(), "No se puede decrementar más", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun actualizarItemCarrito(cantidadProducto: Int, producto: Producto) {
        val sharedPreferences = requireActivity().getSharedPreferences("UserData", MODE_PRIVATE)
        val usuarioIniciado = sharedPreferences.getInt("usuarioIniciado", 0)

        val retrofit = Retrofit.Builder()
            .baseUrl(Constantes.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val itemService = retrofit.create(ItemService::class.java)

        val nuevoItem = Item()
        nuevoItem.setProducto(producto)
        nuevoItem.setCantidad(cantidadProducto)

        val call = itemService.crearItem(usuarioIniciado, nuevoItem)

        call.enqueue(object : Callback<Item> {
            //Si la solicitud fue exitosa se ejecuta esta funcion
            //Este metodo recibe dos parametros, la llamada o solicitud que se le hizo
            //Y la respuesta que tuvimosd desde la API
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                //Si la respuesta viene con un codigo de exito se ejecuta el iff
                if (response.isSuccessful && response.body() != null) {
                    Log.e("Item", "Item Actualizado Exitosamente")
                } else {
                    Log.e("Error", "Error En la respuesta: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Item>, t: Throwable) {
                Log.e("Error", "Error en la solicitud: ${t.message}")
            }
        })
    }

    private fun getItems() {
        val sharedPreferences = requireActivity().getSharedPreferences("UserData", MODE_PRIVATE)
        val usuarioIniciado = sharedPreferences.getInt("usuarioIniciado", 0)
        totalCarrito = 0.0

        val retrofit = Retrofit.Builder()
            .baseUrl(Constantes.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val itemService = retrofit.create(ItemService::class.java)

        call = itemService.getItemsByUsuario(usuarioIniciado)

        call.enqueue(object : Callback<List<Item>> {
            override fun onResponse(call: Call<List<Item>>, response: Response<List<Item>>) {
                if (response.isSuccessful && response.body() != null) {
                    //Los !! sirve para decir que nunca va a ser nulo ese dato
                    items = response.body()!!
                    var totalCarrito = 0.0
                    if (items != null) {
                        if (!items.isEmpty()) {
                            tv_vacioCarrito.visibility = View.GONE
                            totalCarrito = items.get(0).carrito.totalCarrito.toDouble()
                        } else {
                            tv_vacioCarrito.visibility = View.VISIBLE
                        }
                        tv_totalCarrito.text =
                            getString(R.string.totalLabel) + getString(R.string.simboloPesos) + totalCarrito
                        rv_productoCarrito.adapter = carritoAdapter(items, this@CarritoFragment)
                    }
                } else {
                    Log.e("Error", "Codigo de error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Item>>, t: Throwable) {
                Log.e("Error", "Error en la obtencion del carrito: ${t.message}")
            }
        })
    }

    private fun validarStock(): Boolean {
        for (item in items) {
            if (item.cantidad > item.producto.stock) {
                Toast.makeText(
                    requireContext(),
                    "No hay suficiente stock de ${item.producto.nombre}, solo hay ${item.producto.stock}",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }
        }
        return true
    }

    private fun realizarPedido() {
        SharedPreferences = requireActivity().getSharedPreferences("UserData", MODE_PRIVATE)
        val usuarioIniciado = SharedPreferences.getInt("usuarioIniciado", 0)

        val retrofit = Retrofit.Builder()
            .baseUrl(Constantes.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val pedidoService = retrofit.create(PedidoService::class.java)

        val callPedido = pedidoService.crearProductoByUsuario(usuarioIniciado)

        callPedido.enqueue(object : Callback<Pedido> {
            override fun onResponse(call: Call<Pedido>, response: Response<Pedido>) {
                if (response.isSuccessful && response.body() != null) {
                    rv_productoCarrito.adapter = carritoAdapter(emptyList(), this@CarritoFragment)
                    tv_totalCarrito.text =
                        getString(R.string.totalLabel) + getString(R.string.simboloPesos) + totalCarrito
                    tv_vacioCarrito.visibility = View.VISIBLE
                    Toast.makeText(requireContext(), "Pedido Realizado Exitosamente", Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Log.e("Error", "Codigo de error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Pedido>, t: Throwable) {
                Log.e("Error", "Error en la creacion del pedido: ${t.message}")
            }
        })
    }


}