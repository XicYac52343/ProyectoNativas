package com.example.proyectonativas.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectonativas.R
import com.example.proyectonativas.modelos.Pedido

class pedidosAdminAdapter (
    private val pedidos:List<Pedido>,
    private val listenerID : listenerPedidoID
): RecyclerView.Adapter<pedidosAdminAdapter.ViewHolder>() {

    interface listenerPedidoID {
        fun onIDClick(pedido: Pedido)
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val tv_idPrimerGestionPedi = view.findViewById<TextView>(R.id.tv_idPrimerGestionPedi)
        val tv_estadoPrimerGestionPedi = view.findViewById<TextView>(R.id.tv_estadoPrimerGestionPedi)
        val tv_fechaPrimerGestionPedi = view.findViewById<TextView>(R.id.tv_fechaPrimerGestionPedi)
        val tv_montoPrimerGestionPedi = view.findViewById<TextView>(R.id.tv_montoPrimerGestionPedi)
    }

    override fun getItemCount() = pedidos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.pedidos_admin_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = pedidos[position]
        holder.tv_idPrimerGestionPedi.text = item.id.toString()
        holder.tv_estadoPrimerGestionPedi.text = item.estado
        holder.tv_fechaPrimerGestionPedi.text = item.fecha
        holder.tv_montoPrimerGestionPedi.text = "$" + item.total.toString().trim()

        holder.tv_idPrimerGestionPedi.setOnClickListener {
            listenerID.onIDClick(item)
        }
    }
}