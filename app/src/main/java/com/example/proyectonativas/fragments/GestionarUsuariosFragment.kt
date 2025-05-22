package com.example.proyectonativas.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectonativas.Constantes
import com.example.proyectonativas.R
import com.example.proyectonativas.adapters.pedidosAdminAdapter
import com.example.proyectonativas.adapters.usuariosAdminAdapter
import com.example.proyectonativas.modelos.Pedido
import com.example.proyectonativas.modelos.Usuario
import com.example.proyectonativas.servicios.PedidoService
import com.example.proyectonativas.servicios.UsuarioService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GestionarUsuariosFragment : Fragment(), usuariosAdminAdapter.listenerUsuarioID {
    private lateinit var tv_idPrimerGestionUsu : TextView
    private lateinit var rv_usuariosAdmin : RecyclerView
    private lateinit var call : Call<List<Usuario>>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_gestion_usuarios, container, false)

        rv_usuariosAdmin = view.findViewById(R.id.rv_usuariosAdmin)
        getUsuarios()
        return view
    }


    private fun getUsuarios(){
        val retrofit = Retrofit.Builder()
            .baseUrl(Constantes.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        //Creamos el servicio de tipo ProductoService con la configuracion anterior de retrofit
        val usuariosService = retrofit.create(UsuarioService::class.java)

        //Creamos la solicitud para obtener los productos de la API
        call = usuariosService.getUsuarios()


        //El enqueue devuelve todas las funciones de la respuesta de call
        //Es asíncrona, lo que significa que no bloquea la interfaz gráfica del usuario mientras se ejecuta
        //Con el Object creamos un objeto de tipo Callback el cual posee una lista de tipo producto
        call.enqueue(object : Callback<List<Usuario>> {
            //Si la solicitud fue exitosa se ejecuta esta funcion

            //Este metodo recibe dos parametros, la llamada o solicitud que se le hizo
            //Y la respuesta que tuvimosd desde la API
            override fun onResponse(call: Call<List<Usuario>>, response: Response<List<Usuario>>) {

                //Si la respuesta viene con un codigo de exito se ejecuta el iff
                if (response.isSuccessful && response.body() != null) {
                    val usuarios = response.body()

                    if (!usuarios.isNullOrEmpty()) {
                        rv_usuariosAdmin.adapter = usuariosAdminAdapter(usuarios, this@GestionarUsuariosFragment)
                    } else {
                        Log.e("Error", "La lista de productos está vacía o es null")
                    }
                } else {
                    Log.e("Error", "Código de error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<List<Usuario>>, t: Throwable) {
                Log.e("Error", "Error en la solicitud: ${t.message}")
            }
        })
    }

    override fun onIDClick(usuario: Usuario) {
        Log.d("ID", "Usuario clickeada " + usuario)
        //Cajita para enviar o guardar datos entre fragments
        val bundle = Bundle()
        bundle.putInt("productoID", usuario.id)
        findNavController().navigate(R.id.action_gestionarUsuarioFragment_A_editarUsuarioAdminFragment)
    }



    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onStart() {
        super.onStart()
        Log.d("Profile Activity", "onStart: Va a ser visible el activity de Gestion Usuarios")
    }

    override fun onResume() {
        super.onResume()
        Log.d("Profile Activity", "onResume: Va a estar en primer plano el activity de Gestion Usuarios")
    }

    override fun onPause() {
        super.onPause()
        Log.d("Profile Activity", "onPause: Va a estar en pausa el activity de Gestion Usuarios")
    }

    override fun onStop() {
        super.onStop()
        Log.d("Profile Activity", "onStop: Va a detenerse porque no es visible el activity de Gestion Usuarios")
    }
}