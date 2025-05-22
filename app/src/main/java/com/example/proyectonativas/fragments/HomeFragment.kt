package com.example.proyectonativas.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectonativas.Constantes
import com.example.proyectonativas.R
import com.example.proyectonativas.adapters.productosAdapter
import com.example.proyectonativas.modelos.Item
import com.example.proyectonativas.modelos.Producto
import com.example.proyectonativas.servicios.ItemService
import com.example.proyectonativas.servicios.ProductoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HomeFragment : Fragment(), productosAdapter.imagenProductoListener {

    private lateinit var SharedPreferences : SharedPreferences
    private var usuarioIniciado : Int = 0
    private lateinit var call : Call<List<Producto>>
    private lateinit var rv_productosHome : RecyclerView
    private lateinit var rv_productosHomeMasVendidos: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_home, container, false)
        rv_productosHome = view.findViewById(R.id.rv_productosHome)
        rv_productosHomeMasVendidos = view.findViewById(R.id.rv_productosHomeMasVendidos)
        SharedPreferences = requireActivity().getSharedPreferences("UserData", MODE_PRIVATE)
        usuarioIniciado = SharedPreferences.getInt("usuarioIniciado", 0)

        getProductos()
        return view
    }

    private fun getProductos(){

        //Inicializamos el servicio de retrofit para obtener los productos respecto a la URL
        //Y lo creamos para que se pueda convertir la respuesta JSON a un objeto de tipo Kotlin
        val retrofit = Retrofit.Builder()
            .baseUrl(Constantes.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //Creamos el servicio de tipo ProductoService con la configuracion anterior de retrofit
        val productoService = retrofit.create(ProductoService::class.java)

        //Creamos la solicitud para obtener los productos de la API
        call = productoService.getProductosHome()


        //El enqueue devuelve todas las funciones de la respuesta de call
        //Es asíncrona, lo que significa que no bloquea la interfaz gráfica del usuario mientras se ejecuta
        //Con el Object creamos un objeto de tipo Callback el cual posee una lista de tipo producto
        call.enqueue(object : Callback<List<Producto>> {
            //Si la solicitud fue exitosa se ejecuta esta funcion

            //Este metodo recibe dos parametros, la llamada o solicitud que se le hizo
            //Y la respuesta que tuvimosd desde la API
            override fun onResponse(call: Call<List<Producto>>, response: Response<List<Producto>>) {

                //Si la respuesta viene con un codigo de exito se ejecuta el iff
                if (response.isSuccessful && response.body() != null) {
                    val productos = response.body()

                    if (!productos.isNullOrEmpty()) {
                        val productosNormales = productos.take(5)
                        val productosMasVendidos = productos.takeLast(5)

                        rv_productosHome.adapter = productosAdapter(productosNormales, this@HomeFragment, "home")
                        rv_productosHomeMasVendidos.adapter = productosAdapter(productosMasVendidos, this@HomeFragment, "vendidos")

                    } else {
                        Log.e("Error", "La lista de productos está vacía o es null")
                    }
                } else {
                    Log.e("Error", "Código de error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Producto>>, t: Throwable) {
                Log.e("Error", "Error en la solicitud: ${t.message}")
            }
        })
    }

    override fun agregarItemCarrito(producto: Producto){
        val retrofit = Retrofit.Builder()
            .baseUrl(Constantes.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val itemService = retrofit.create(ItemService::class.java)

        val nuevoItem = Item()
        nuevoItem.setProducto(producto)
        nuevoItem.setCantidad(1)

        val call = itemService.crearItem(usuarioIniciado, nuevoItem)

        call.enqueue(object : Callback<Item> {
            //Si la solicitud fue exitosa se ejecuta esta funcion
            //Este metodo recibe dos parametros, la llamada o solicitud que se le hizo
            //Y la respuesta que tuvimosd desde la API
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                //Si la respuesta viene con un codigo de exito se ejecuta el iff
                if (response.isSuccessful && response.body() != null) {
                    Log.e("Item", "Item Creado Exitosamente")
                    Toast.makeText(
                        requireContext(),
                        "Agregado Exitosamente",
                        Toast.LENGTH_SHORT).show()
                } else {
                    Log.e("Error", "Error En la respuesta: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<Item>, t: Throwable) {
                Log.e("Error", "Error en la solicitud: ${t.message}")
            }
        })
    }

    override fun onImagenClick(producto: Producto) {
        Log.d("Imagen", "Imagen clickeada " + producto.id)
        //Cajita para enviar o guardar datos entre fragments
        val bundle = Bundle()
        bundle.putInt("productoID", producto.id)
        findNavController().navigate(R.id.productoFragment, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onStart() {
        super.onStart()
        Log.d("Profile Activity", "onStart: Va a ser visible el activity de Profile")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Profile Activity", "onResume: Va a estar en primer plano el activity de Profile")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Profile Activity", "onPause: Va a estar en pausa el activity de Profile")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Profile Activity", "onStop: Va a detenerse porque no es visible el activity de Profile")
    }
}