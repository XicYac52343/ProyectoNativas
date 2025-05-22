package com.example.proyectonativas.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectonativas.R
import com.example.proyectonativas.adapters.productosAdapter.imagenProductoListener
import com.example.proyectonativas.modelos.Item
import com.example.proyectonativas.modelos.Producto

class carritoAdapter(
    private val items:List<Item>,
    private val listenerCantidad : cantidadListener,
):
    RecyclerView.Adapter<carritoAdapter.ViewHolder>(){

    interface cantidadListener{
        fun aumentarCantidad(item: Item)
        fun disminuirCantidad(item: Item)
    }

        class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
            val tv_nombrePrimerProductoCarrito = view.findViewById<TextView>(R.id.tv_nombrePrimerProductoCarrito)
            val tv_precioUniPrimerProductoCarrito = view.findViewById<TextView>(R.id.tv_precioUniPrimerProductoCarrito)
            val tv_cantidadPrimerProCarrito = view.findViewById<TextView>(R.id.tv_cantidadPrimerProCarrito)
            val tv_totalPrimerProCarrito = view.findViewById<TextView>(R.id.tv_totalPrimerProCarrito)
            val iv_primerProductoCarrito = view.findViewById<ImageView>(R.id.iv_primerProductoCarrito)
            val bt_menosPrimerProCarrito = view.findViewById<TextView>(R.id.bt_menosPrimerProCarrito)
            val bt_masPrimerProCarrito = view.findViewById<TextView>(R.id.bt_masPrimerProCarrito)

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
        var imageResId = holder.itemView.context.resources.getIdentifier(nombreImagen, "drawable", holder.itemView.context.packageName)
        if(item.producto.cantidadDescuento > 0){
            imageResId = holder.itemView.context.resources.getIdentifier(nombreImagen+"_oferta", "drawable", holder.itemView.context.packageName)
            var precioDescuento = (item.producto.precio -((item.producto.precio * item.producto.cantidadDescuento)/100))
            holder.tv_totalPrimerProCarrito.text = "$"+(item.cantidad * item.producto.precio).toString().trim()
            holder.tv_totalPrimerProCarrito.text = "$"+(item.cantidad * precioDescuento).toString().trim()
            holder.tv_precioUniPrimerProductoCarrito.text = "$"+precioDescuento.toString()
        }
        holder.iv_primerProductoCarrito.setImageResource(imageResId)

        holder.bt_menosPrimerProCarrito.setOnClickListener{
            listenerCantidad.disminuirCantidad(item)
        }

        holder.bt_masPrimerProCarrito.setOnClickListener{
            listenerCantidad.aumentarCantidad(item)
        }
    }
    override fun getItemCount() = items.size
}
