package com.example.proyectonativas.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectonativas.Constantes
import com.example.proyectonativas.R
import com.example.proyectonativas.adapters.pedidosAdminAdapter
import com.example.proyectonativas.modelos.Pedido
import com.example.proyectonativas.servicios.PedidoService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class HistorialPedidosFragment : Fragment(), pedidosAdminAdapter.listenerPedidoID{
    private lateinit var call : Call<List<Pedido>>
    private lateinit var SharedPreferences : SharedPreferences
    private var usuarioIniciado : Int = 0
    private lateinit var rv_historialPedidos : RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?

    ): View? {
        val view = inflater.inflate(R.layout.fragment_historial_pedidos, container, false)
        SharedPreferences = requireActivity().getSharedPreferences("UserData", MODE_PRIVATE)
        usuarioIniciado = SharedPreferences.getInt("usuarioIniciado", 0)
        rv_historialPedidos = view.findViewById(R.id.rv_historialPedidos)
        getPedidos()
        return view
    }

    private fun getPedidos(){
        val retrofit = Retrofit.Builder()
            .baseUrl(Constantes.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //Creamos el servicio de tipo ProductoService con la configuracion anterior de retrofit
        val pedidosService = retrofit.create(PedidoService::class.java)

        //Creamos la solicitud para obtener los productos de la API
        call = pedidosService.getPedidosByUsuario(usuarioIniciado)


        //El enqueue devuelve todas las funciones de la respuesta de call
        //Es asíncrona, lo que significa que no bloquea la interfaz gráfica del usuario mientras se ejecuta
        //Con el Object creamos un objeto de tipo Callback el cual posee una lista de tipo producto
        call.enqueue(object : Callback<List<Pedido>> {
            //Si la solicitud fue exitosa se ejecuta esta funcion

            //Este metodo recibe dos parametros, la llamada o solicitud que se le hizo
            //Y la respuesta que tuvimosd desde la API
            override fun onResponse(call: Call<List<Pedido>>, response: Response<List<Pedido>>) {

                //Si la respuesta viene con un codigo de exito se ejecuta el iff
                if (response.isSuccessful && response.body() != null) {
                    val pedidos = response.body()

                    if (!pedidos.isNullOrEmpty()) {
                        rv_historialPedidos.adapter = pedidosAdminAdapter(pedidos, this@HistorialPedidosFragment)
                    } else {
                        Log.e("Error", "La lista de productos está vacía o es null")
                    }
                } else {
                    Log.e("Error", "Código de error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Pedido>>, t: Throwable) {
                Log.e("Error", "Error en la solicitud: ${t.message}")
            }
        })


    }

    override fun onIDClick(pedido: Pedido) {
        Log.d("ID", "Pedido clickeada " + pedido)
        //Cajita para enviar o guardar datos entre fragments
        val bundle = Bundle()
        bundle.putInt("productoID", pedido.id)
        findNavController().navigate(R.id.action_pedidoADetallePedido, bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        call.cancel()
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