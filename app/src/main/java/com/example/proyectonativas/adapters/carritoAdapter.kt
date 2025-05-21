package com.example.proyectonativas.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectonativas.R
import com.example.proyectonativas.modelos.Item

class carritoAdapter(private val items:List<Item>):
    RecyclerView.Adapter<carritoAdapter.ViewHolder>(){

        class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
            val tv_nombrePrimerProductoCarrito = view.findViewById<TextView>(R.id.tv_nombrePrimerProductoCarrito)
            val tv_precioUniPrimerProductoCarrito = view.findViewById<TextView>(R.id.tv_precioUniPrimerProductoCarrito)
            val tv_cantidadPrimerProCarrito = view.findViewById<TextView>(R.id.tv_cantidadPrimerProCarrito)
            val tv_totalPrimerProCarrito = view.findViewById<TextView>(R.id.tv_totalPrimerProCarrito)
            val iv_primerProductoCarrito = view.findViewById<ImageView>(R.id.iv_primerProductoCarrito)
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): carritoAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.producto_item_carrito, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: carritoAdapter.ViewHolder, position: Int) {
        val item = items[position]
        holder.tv_nombrePrimerProductoCarrito.text = item.producto.nombre
        holder.tv_precioUniPrimerProductoCarrito.text = "$"+item.producto.precio.toString()
        holder.tv_cantidadPrimerProCarrito.text = item.cantidad.toString()
        holder.tv_totalPrimerProCarrito.text = "$"+(item.cantidad * item.producto.precio).toString().trim()
        val nombreImagen = item.producto.nombreImagen
        val imageResId = holder.itemView.context.resources.getIdentifier(nombreImagen, "drawable", holder.itemView.context.packageName)
        holder.iv_primerProductoCarrito.setImageResource(imageResId)
    }
    override fun getItemCount() = items.size
}