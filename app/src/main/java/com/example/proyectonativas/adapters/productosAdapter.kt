package com.example.proyectonativas.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectonativas.R
import com.example.proyectonativas.modelos.Producto

class productosAdapter(
    private val productos:List<Producto>,
    private val listenerImagen : imagenProductoListener
    ): RecyclerView.Adapter<productosAdapter.ViewHolder>() {

        interface imagenProductoListener{
            fun onImagenClick(producto: Producto)
        }

    class ViewHolder(private val view : View): RecyclerView.ViewHolder(view){
        val tv_nombrePrimerProductoCategorias = view.findViewById<TextView>(R.id.tv_nombrePrimerProductoCategorias)
        val tv_precioPrimerProductoCategorias = view.findViewById<TextView>(R.id.tv_precioPrimerProductoCategorias)
        val iv_primerProductoCategorias = view.findViewById<ImageView>(R.id.iv_primerProductoCategorias)
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

        holder.iv_primerProductoCategorias.setOnClickListener{
            listenerImagen.onImagenClick(item)
        }
    }

}