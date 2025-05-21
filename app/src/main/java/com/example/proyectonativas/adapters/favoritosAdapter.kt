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
import com.example.proyectonativas.modelos.Producto

class favoritosAdapter(
    private val productos:List<Producto>
): RecyclerView.Adapter<favoritosAdapter.ViewHolder>() {


    class ViewHolder(private val view : View): RecyclerView.ViewHolder(view){
        val nombreProductoFavorito = view.findViewById<TextView>(R.id.nombreProductoFavorito)
        val categoriaproductoFavorito = view.findViewById<TextView>(R.id.categoriaproductoFavorito)
        val precioProductoFavorito = view.findViewById<TextView>(R.id.precioProductoFavorito)
        val cantidadStockFavorito = view.findViewById<TextView>(R.id.cantidadStockFavorito)
    }

    override fun getItemCount() = productos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): favoritosAdapter.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.producto_favorito_layout, parent, false)

        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: favoritosAdapter.ViewHolder, position: Int) {
        val item = productos[position]
        holder.nombreProductoFavorito.text = item.nombre
        holder.categoriaproductoFavorito.text = item.categoria
        holder.precioProductoFavorito.text = "$"+item.precio.toString()
        holder.cantidadStockFavorito.text = item.stock.toString().plus(" und")
    }

}