package com.example.proyectonativas.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectonativas.Constantes
import com.example.proyectonativas.R
import com.example.proyectonativas.adapters.productosAdapter
import com.example.proyectonativas.adapters.productosAdminAdapter
import com.example.proyectonativas.modelos.Producto
import com.example.proyectonativas.servicios.ProductoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GestionarProductosFragment : Fragment(), productosAdminAdapter.buttonEditarListener {
    private lateinit var bt_agregarProducto : Button
    private lateinit var bt_editarPrimerGestionPro : Button
    private lateinit var call : Call<List<Producto>>
    private lateinit var rv_productosAdmin : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gestionar_productos, container, false)
        bt_agregarProducto = view.findViewById(R.id.bt_agregarProducto)
        rv_productosAdmin = view.findViewById(R.id.rv_productosAdmin)

        bt_agregarProducto.setOnClickListener {
            findNavController().navigate(R.id.action_gestionarProductosFragment_A_agregarProductoFragment)
        }

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
        call = productoService.getProductos()


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
                        rv_productosAdmin.adapter = productosAdminAdapter(productos, this@GestionarProductosFragment)
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

    //Override es para sobreescribir una funcion de una clase padre
    override fun onEditarClick(producto: Producto) {
        Log.d("Imagen", "Editar clickeada " + producto.id)
        //Cajita para enviar o guardar datos entre fragments
        val bundle = Bundle()
        bundle.putInt("productoID", producto.id)
        findNavController().navigate(R.id.action_gestionarProductosFragment_A_editarProductoFragment)
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