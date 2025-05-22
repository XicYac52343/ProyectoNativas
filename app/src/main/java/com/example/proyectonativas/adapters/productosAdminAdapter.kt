package com.example.proyectonativas.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectonativas.R
import com.example.proyectonativas.modelos.Producto
import org.w3c.dom.Text

class productosAdminAdapter (
    private val productos:List<Producto>,
    private val listenerButton : buttonEditarListener
): RecyclerView.Adapter<productosAdminAdapter.ViewHolder>() {

    interface buttonEditarListener{
        fun onEditarClick(producto: Producto)
    }

    class ViewHolder(private val view : View): RecyclerView.ViewHolder(view){
        val tv_nombrePrimerProductoGestionPro = view.findViewById<TextView>(R.id.tv_nombrePrimerProductoGestionPro)
        val tv_precioPrimerProductoGestionPro = view.findViewById<TextView>(R.id.tv_precioPrimerProductoGestionPro)
        val tv_categoriaPrimerProductoGestionPro = view.findViewById<TextView>(R.id.tv_categoriaPrimerProductoGestionPro)
        val tv_cantidadPrimerProductoGestionPro = view.findViewById<TextView>(R.id.tv_cantidadPrimerProductoGestionPro)
        val bt_editarPrimerGestionPro = view.findViewById<Button>(R.id.bt_editarPrimerGestionPro)
        val iv_primerProductoGestionPro = view.findViewById<ImageView>(R.id.iv_primerProductoGestionPro)
    }

    override fun getItemCount() = productos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.productos_admin_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = productos[position]
        holder.tv_nombrePrimerProductoGestionPro.text = item.nombre
        holder.tv_precioPrimerProductoGestionPro.text = "$"+item.precio.toString()
        holder.tv_categoriaPrimerProductoGestionPro.text = item.categoria
        holder.tv_cantidadPrimerProductoGestionPro.text = "Stock: "+item.stock.toString()

        val nombreImagen = item.nombreImagen
        var imageResId = holder.itemView.context.resources.getIdentifier(nombreImagen, "drawable", holder.itemView.context.packageName)
        if(item.cantidadDescuento > 0){
            imageResId = holder.itemView.context.resources.getIdentifier(nombreImagen+"_oferta", "drawable", holder.itemView.context.packageName)
        }
        holder.iv_primerProductoGestionPro.setImageResource(imageResId)

        holder.bt_editarPrimerGestionPro.setOnClickListener{
            listenerButton.onEditarClick(item)
        }
    }

}