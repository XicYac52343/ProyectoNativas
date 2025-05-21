package com.example.proyectonativas.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectonativas.Constantes
import com.example.proyectonativas.R
import com.example.proyectonativas.adapters.favoritosAdapter
import com.example.proyectonativas.adapters.productosAdapter
import com.example.proyectonativas.modelos.Producto
import com.example.proyectonativas.servicios.ProductoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FavoritoFragment : Fragment() {
    private lateinit var mas: Button
    private lateinit var menos: Button
    private lateinit var numero: TextView
    private lateinit var rv_productosFavoritos : RecyclerView
    private lateinit var SharedPreferences : SharedPreferences
    private var usuarioIniciado : Int = 0


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_favoritos, container, false)
        SharedPreferences = requireActivity().getSharedPreferences("UserData", MODE_PRIVATE)
        usuarioIniciado = SharedPreferences.getInt("usuarioIniciado", 0)
        rv_productosFavoritos = view.findViewById(R.id.rv_productosFavoritos)


        /*mas = view.findViewById(R.id.btnaume)
        mas.setOnClickListener {
            actualizarNumeromas()
        }
        menos = view.findViewById(R.id.btndism)
        menos.setOnClickListener {
            actualizarNumeromenos()

        }
        numero = view.findViewById(R.id.cantidadinicial)*/

        getFavoritos()
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

    private fun getFavoritos(){
        val retrofit = Retrofit.Builder()
            .baseUrl(Constantes.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //Creamos el servicio de tipo ProductoService con la configuracion anterior de retrofit
        val productoService = retrofit.create(ProductoService::class.java)

        //Creamos la solicitud para obtener los productos de la API
        val call = productoService.getProductosFavoritos(usuarioIniciado)


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

                    if (productos!=null && productos.isNotEmpty()) {
                        rv_productosFavoritos.adapter = favoritosAdapter(productos)

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
}