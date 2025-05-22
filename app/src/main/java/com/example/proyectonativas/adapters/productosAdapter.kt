package com.example.proyectonativas.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectonativas.R
import com.example.proyectonativas.modelos.Producto

class productosAdapter(
    private val productos:List<Producto>,
    private val listenerImagen : imagenProductoListener,
    private val fragmentProveniente: String
    ): RecyclerView.Adapter<productosAdapter.ViewHolder>() {

        interface imagenProductoListener{
            fun onImagenClick(producto: Producto)
            fun agregarItemCarrito(producto: Producto)
        }

    class ViewHolder(private val view : View): RecyclerView.ViewHolder(view){
        val tv_nombrePrimerProductoCategorias = view.findViewById<TextView>(R.id.tv_nombrePrimerProductoCategorias)
        val tv_precioPrimerProductoCategorias = view.findViewById<TextView>(R.id.tv_precioPrimerProductoCategorias)
        val iv_primerProductoCategorias = view.findViewById<ImageView>(R.id.iv_primerProductoCategorias)
        val bt_agregarPrimerProducto = view.findViewById<Button>(R.id.bt_agregarPrimerProducto)
        val ly_productoGeneral = view.findViewById<View>(R.id.ly_productoGeneral)
    }

    override fun getItemCount() = productos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.producto_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = productos[position]
        holder.tv_nombrePrimerProductoCategorias.text = item.nombre
        holder.tv_precioPrimerProductoCategorias.text = "$"+item.precio.toString()
        //Context nos ayuda a acceder a los recursos del dispositivo
        //item view es el vista raíz del ViewHolder o la vista del elemento de la lista
        //	Contexto asociado a esa vista, que permite acceder a recursos desde ahí
        //Conseguir el ID de la imagen usando el contexto o nombre de la imagen en el archivo de recursos que esta en la actividad
        val nombreImagen = item.nombreImagen
        var imageResId = holder.itemView.context.resources.getIdentifier(nombreImagen, "drawable", holder.itemView.context.packageName)
        if(item.cantidadDescuento > 0){
            imageResId = holder.itemView.context.resources.getIdentifier(nombreImagen+"_oferta", "drawable", holder.itemView.context.packageName)
        }
        holder.iv_primerProductoCategorias.setImageResource(imageResId)

        val layoutParams = holder.ly_productoGeneral.layoutParams


        if(fragmentProveniente.equals("categorias")){
            layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT
            holder.ly_productoGeneral.layoutParams = layoutParams
        }
        if(fragmentProveniente.equals("home")){
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            holder.ly_productoGeneral.layoutParams = layoutParams
        }
        if(fragmentProveniente.equals("vendidos")){
            layoutParams.width = ViewGroup.LayoutParams.WRAP_CONTENT
            holder.ly_productoGeneral.layoutParams = layoutParams
            holder.iv_primerProductoCategorias.background = ContextCompat.getDrawable(holder.itemView.context, R.drawable.degradado_producto_gris)
        }

        holder.iv_primerProductoCategorias.setOnClickListener{
            listenerImagen.onImagenClick(item)
        }

        holder.bt_agregarPrimerProducto.setOnClickListener{
            listenerImagen.agregarItemCarrito(item)
        }
    }

}