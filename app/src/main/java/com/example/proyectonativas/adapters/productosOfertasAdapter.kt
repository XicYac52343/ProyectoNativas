package com.example.proyectonativas.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectonativas.R
import com.example.proyectonativas.modelos.Producto

class productosOfertasAdapter(private val productos:List<Producto>) :
    RecyclerView.Adapter<productosOfertasAdapter.ViewHolder>() {


        class ViewHolder(private val view: View): RecyclerView.ViewHolder(view){
            val tv_nombrePrimerProductoOfertas = view.findViewById<TextView>(R.id.tv_nombrePrimerProductoOfertas)
            val tv_precioAntesPrimerProductoOfertas = view.findViewById<TextView>(R.id.tv_precioAntesPrimerProductoOfertas)
            val tv_cantidadDescuentoPrimerProductoOfertas = view.findViewById<TextView>(R.id.tv_cantidadDescuentoPrimerProductoOfertas)
            val tv_precioDespuesPrimerProductoOfertas = view.findViewById<TextView>(R.id.tv_precioDespuesPrimerProductoOfertas)
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
        holder.tv_precioDespuesPrimerProductoOfertas.text = "$"+item.precio.toString()
    }

    override fun getItemCount() = productos.size

}