package com.example.proyectonativas.adapters

import android.provider.Settings.System.getString
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyectonativas.R
import com.example.proyectonativas.modelos.Usuario

class usuariosAdminAdapter (
    private val usuarios:List<Usuario>,
    private val listenerID : listenerUsuarioID
): RecyclerView.Adapter<usuariosAdminAdapter.ViewHolder>() {

    interface listenerUsuarioID {
        fun onIDClick(usuario: Usuario)
    }

    class ViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val tv_idPrimerGestionUsu = view.findViewById<TextView>(R.id.tv_idPrimerGestionUsu)
        val tv_nombrePrimerGestionUsu = view.findViewById<TextView>(R.id.tv_nombrePrimerGestionUsu)
        val tv_apellidoPrimerGestionUsu = view.findViewById<TextView>(R.id.tv_apellidoPrimerGestionUsu)
        val tv_estadoPrimerGestionUsu = view.findViewById<TextView>(R.id.tv_estadoPrimerGestionUsu)
    }

    override fun getItemCount() = usuarios.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.usuario_admin_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = usuarios[position]
        holder.tv_idPrimerGestionUsu.text = item.id.toString()
        holder.tv_nombrePrimerGestionUsu.text = item.nombre
        holder.tv_apellidoPrimerGestionUsu.text = item.apellido
        holder.tv_estadoPrimerGestionUsu.text = "Activo"

        holder.tv_idPrimerGestionUsu.setOnClickListener {
            listenerID.onIDClick(item)
        }
    }
}