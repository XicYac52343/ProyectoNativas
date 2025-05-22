package com.example.proyectonativas.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectonativas.R
import com.example.proyectonativas.adapters.productosAdapter.imagenProductoListener
import com.example.proyectonativas.modelos.Producto

class productosOfertasAdapter(
    private val productos:List<Producto>,
    private val listenerImagen : imagenProductoListener,) :
    RecyclerView.Adapter<productosOfertasAdapter.ViewHolder>() {

    interface imagenProductoListener{
        fun onImagenClick(producto: Producto)
        fun agregarItemCarrito(producto: Producto)
    }


        class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
            val tv_nombrePrimerProductoOfertas = view.findViewById<TextView>(R.id.tv_nombrePrimerProductoOfertas)
            val tv_precioAntesPrimerProductoOfertas = view.findViewById<TextView>(R.id.tv_precioAntesPrimerProductoOfertas)
            val tv_cantidadDescuentoPrimerProductoOfertas = view.findViewById<TextView>(R.id.tv_cantidadDescuentoPrimerProductoOfertas)
            val tv_precioDespuesPrimerProductoOfertas = view.findViewById<TextView>(R.id.tv_precioDespuesPrimerProductoOfertas)
            val iv_primerProductoOfertas = view.findViewById<ImageView>(R.id.iv_primerProductoOfertas)
            val bt_agregarPrimerProductoOfertas = view.findViewById<Button>(R.id.bt_agregarPrimerProductoOfertas)
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.producto_oferta_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: productosOfertasAdapter.ViewHolder, position: Int) {
        val item = productos[position]
        holder.tv_nombrePrimerProductoOfertas.text = item.nombre
        holder.tv_precioAntesPrimerProductoOfertas.text = "$"+item.precio.toString()
        holder.tv_cantidadDescuentoPrimerProductoOfertas.text = item.cantidadDescuento.toString()+"%"
        val precioDespues = item.precio - (item.precio * (item.cantidadDescuento / 100.0))
        holder.tv_precioDespuesPrimerProductoOfertas.text = "$"+precioDespues
        val nombreImagen = item.nombreImagen
        val imageResId = holder.itemView.context.resources.getIdentifier(nombreImagen+"_oferta", "drawable", holder.itemView.context.packageName)
        holder.iv_primerProductoOfertas.setImageResource(imageResId)

        holder.iv_primerProductoOfertas.setOnClickListener{
            listenerImagen.onImagenClick(item)
        }
        holder.bt_agregarPrimerProductoOfertas.setOnClickListener{
            listenerImagen.agregarItemCarrito(item)
        }
    }

    override fun getItemCount() = productos.size

}