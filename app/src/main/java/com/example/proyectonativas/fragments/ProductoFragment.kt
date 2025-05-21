package com.example.proyectonativas.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import android.view.animation.Animation
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity.MODE_PRIVATE
import androidx.fragment.app.Fragment
import com.example.proyectonativas.Constantes
import com.example.proyectonativas.R
import com.example.proyectonativas.adapters.productosOfertasAdapter
import com.example.proyectonativas.modelos.Item
import com.example.proyectonativas.modelos.Producto
import com.example.proyectonativas.modelos.Usuario
import com.example.proyectonativas.servicios.ItemService
import com.example.proyectonativas.servicios.ProductoService
import com.example.proyectonativas.servicios.UsuarioService
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductoFragment : Fragment() {
    private lateinit var bt_menosProducto: Button
    private lateinit var bt_masProducto: Button
    private lateinit var bt_añadirProductoDetalles: Button
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var tv_cantidadProducto: TextView
    private lateinit var tv_nombreProductoDetalles : TextView
    private lateinit var tv_precioProductoDetalles : TextView
    private lateinit var tv_descripcionProductoDetalles : TextView
    private lateinit var tv_estilo1ProductoDetalles : TextView
    private lateinit var iv_favProductoDetalles : ImageView
    private var cantidadProducto : Int = 0
    private var productoGuardado: Producto? = null
    private var usuarioIniciado : Int = 0
    private var productoEncontrado : Boolean = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_producto, container, false)
        validarFavorito()
        sharedPreferences = requireActivity().getSharedPreferences("UserData", MODE_PRIVATE)
        usuarioIniciado = sharedPreferences.getInt("usuarioIniciado", 0)
        Log.d("usuarioIniciado", "usuarioIniciado: $usuarioIniciado")
        iv_favProductoDetalles = view.findViewById(R.id.iv_favProductoDetalles)

        bt_añadirProductoDetalles = view.findViewById(R.id.bt_añadirProductoDetalles)
        tv_nombreProductoDetalles = view.findViewById(R.id.tv_nombreProductoDetalles)
        tv_precioProductoDetalles = view.findViewById(R.id.tv_precioProductoDetalles)
        tv_descripcionProductoDetalles = view.findViewById(R.id.tv_descripcionProductoDetalles)
        tv_estilo1ProductoDetalles = view.findViewById(R.id.tv_estilo1ProductoDetalles)

        sharedPreferences = requireActivity().getSharedPreferences("carritoProductos", MODE_PRIVATE)

        getProducto()


        bt_masProducto = view.findViewById(R.id.bt_masProducto)
        bt_masProducto.setOnClickListener {
            aumentarCantidad()
        }

        bt_menosProducto = view.findViewById(R.id.bt_menosProducto)
        bt_menosProducto.setOnClickListener {
            disminuirCantidad()
        }


        iv_favProductoDetalles.setOnClickListener {
            modificarFavorito()
        }

        bt_añadirProductoDetalles.setOnClickListener {
            //var cantidadTraida = sharedPreferences.getInt("cantidadPerfume", 0);
            var cantidadDigitada = tv_cantidadProducto.text.toString().trim()

            if (cantidadDigitada.isNotEmpty()) {
                var cantidadDigitada1 = cantidadDigitada.toString().toInt()
                if (cantidadDigitada1 > 0) {
                    if (cantidadDigitada1 <= cantidadProducto) {
                        //agregarProducto(cantidadNueva.toString(), cantidadDigitada.toString())
                        crearItemCarrito(cantidadDigitada1)
                        Toast.makeText(
                            requireContext(),
                            "Agregado Exitosamente",
                            Toast.LENGTH_SHORT).show()
                    }else {
                        Toast.makeText(
                            requireContext(),
                            "No hay Stock para",
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

    /*private fun agregarProducto(cantidadProducto: String, cantidadComprada1: String) {
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
    }*/

    private fun crearItemCarrito(cantidadProducto : Int){
        val retrofit = Retrofit.Builder()
            .baseUrl(Constantes.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val itemService = retrofit.create(ItemService::class.java)

        val nuevoItem = Item()
        nuevoItem.setProducto(productoGuardado)
        nuevoItem.setCantidad(cantidadProducto)

        val call = itemService.crearItem(usuarioIniciado, nuevoItem)

        call.enqueue(object : Callback<Item> {
            //Si la solicitud fue exitosa se ejecuta esta funcion
            //Este metodo recibe dos parametros, la llamada o solicitud que se le hizo
            //Y la respuesta que tuvimosd desde la API
            override fun onResponse(call: Call<Item>, response: Response<Item>) {
                //Si la respuesta viene con un codigo de exito se ejecuta el iff
                if (response.isSuccessful && response.body() != null) {
                    Log.e("Item", "Item Creado Exitosamente")
                } else {
                    Log.e("Error", "Error En la respuesta: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<Item>, t: Throwable) {
                Log.e("Error", "Error en la solicitud: ${t.message}")
            }
        })

    }

    private fun getProducto(){
        val productoID = arguments?.getInt("productoID", -1)?: -1


        Log.d("ProductoFragment", "ProductoID Recibido es: $productoID")

        val retrofit = Retrofit.Builder()
            .baseUrl(Constantes.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val productoService = retrofit.create(ProductoService::class.java)

        val call = productoService.getProductoById(productoID)

        call.enqueue(object : Callback<Producto> {
            //Si la solicitud fue exitosa se ejecuta esta funcion

            //Este metodo recibe dos parametros, la llamada o solicitud que se le hizo
            //Y la respuesta que tuvimosd desde la API
            override fun onResponse(call: Call<Producto>, response: Response<Producto>) {

                //Si la respuesta viene con un codigo de exito se ejecuta el iff
                if (response.isSuccessful && response.body() != null) {
                    val producto = response.body()

                    //El simbolo ? sirve para indicar que el objeto puede ser nulo
                    tv_nombreProductoDetalles.text = producto?.nombre
                    tv_precioProductoDetalles.text = getString(R.string.simboloPesos)+"${producto?.precio.toString()}"
                    tv_descripcionProductoDetalles.text = producto?.descripcion
                    tv_estilo1ProductoDetalles.text = producto?.categoria
                    cantidadProducto = producto?.stock.toString().toInt()
                    productoGuardado = producto

                } else {
                    Log.e("Error", "Código de error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Producto>, t: Throwable) {
                Log.e("Error", "Error en la solicitud: ${t.message}")
            }
        })
    }

    private fun modificarFavorito(){
        val retrofit = Retrofit.Builder()
            .baseUrl(Constantes.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val usuarioService = retrofit.create(UsuarioService::class.java)

        val callUser = usuarioService.getUsuarioById(usuarioIniciado)

        callUser.enqueue(object : Callback<Usuario>{
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful && response.body() != null) {
                    Log.e("Usuario", "FUNCIONOOOOOOO")
                    val usuario = response.body()
                    val productoID = productoGuardado?.id
                    if (usuario != null) {
                        if (productoID != null) {
                            usuarioService.actualizarUsuario(productoID, usuario).enqueue(object : Callback<Usuario>{
                                override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                                    if (response.isSuccessful && response.body() != null) {
                                        validarFavorito()
                                        Log.e("Usuario", "Usuario Actualizado")
                                    }else{
                                        Log.e("Error", "Código de error: ${response.code()}")
                                    }
                                }

                                override fun onFailure(call: Call<Usuario>, t: Throwable) {
                                    Log.e("Error", "Error en la solicitud: ${t.message}")
                                }
                            })
                        }
                    }
                } else {
                    Log.e("Error", "Código de error: ${response.code()}")
                }
            }
            override fun onFailure(call: Call<Usuario>, t: Throwable) {
                Log.e("Error", "Error en la solicitud: ${t.message}")
            }
        })

    }

    private fun cambiarImagenAFavorito(){
        //Cambia la opacidad de un componente, que va desde 1f a 0f
        val fadeOut = AlphaAnimation(1f, 0f)
        fadeOut.duration = 200

        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.duration = 200

        fadeOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
            //Cuando esta por terminar la animacion de cambiar la opacidad de la imagen actual
            //Cambia la imagen y sube la opacidad
            override fun onAnimationEnd(animation: Animation?) {
                iv_favProductoDetalles.setImageResource(R.drawable.fav_dorado)
                iv_favProductoDetalles.startAnimation(fadeIn)
            }

        })
        //Llamo para que desvancezca la imagen la opacidad, entra al metodo anterior
        iv_favProductoDetalles.startAnimation(fadeOut)
    }

    private fun quitarFavoritoImagen(){
        //Cambia la opacidad de un componente, que va desde 1f a 0f
        val fadeOut = AlphaAnimation(1f, 0f)
        fadeOut.duration = 200

        val fadeIn = AlphaAnimation(0f, 1f)
        fadeIn.duration = 200

        fadeOut.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationRepeat(animation: Animation?) {

            }
            //Cuando esta por terminar la animacion de cambiar la opacidad de la imagen actual
            //Cambia la imagen y sube la opacidad
            override fun onAnimationEnd(animation: Animation?) {
                iv_favProductoDetalles.setImageResource(R.drawable.fav_blanco)
                iv_favProductoDetalles.startAnimation(fadeIn)
            }

        })
        //Llamo para que desvancezca la imagen la opacidad, entra al metodo anterior
        iv_favProductoDetalles.startAnimation(fadeOut)
    }

    private fun validarFavorito(){
        val productoID = arguments?.getInt("productoID", -1)?: -1


        Log.d("ProductoFragment", "ProductoID Recibido es: $productoID")

        val retrofit = Retrofit.Builder()
            .baseUrl(Constantes.baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val productoService = retrofit.create(ProductoService::class.java)

        val call = productoService.getProductoById(productoID)

        call.enqueue(object : Callback<Producto> {
            //Si la solicitud fue exitosa se ejecuta esta funcion

            //Este metodo recibe dos parametros, la llamada o solicitud que se le hizo
            //Y la respuesta que tuvimosd desde la API
            override fun onResponse(call: Call<Producto>, response: Response<Producto>) {

                //Si la respuesta viene con un codigo de exito se ejecuta el iff
                if (response.isSuccessful && response.body() != null) {
                    val producto = response.body()
                    val listaUsuarios = producto?.usuariosFavoritos

                    if(!listaUsuarios.isNullOrEmpty()){
                        for(usuarioListaProducto in listaUsuarios!!){
                            if(usuarioListaProducto.id == usuarioIniciado){
                                cambiarImagenAFavorito()
                                return
                            }
                        }
                    }
                    quitarFavoritoImagen()
                    return
                } else {
                    Log.e("Error", "Código de error: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<Producto>, t: Throwable) {
                Log.e("Error", "Error en la solicitud: ${t.message}")
            }
        })
    }
}