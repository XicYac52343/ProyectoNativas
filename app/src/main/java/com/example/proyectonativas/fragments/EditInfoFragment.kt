package com.example.proyectonativas.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.proyectonativas.R


class EditInfoFragment : Fragment() {

    private lateinit var sharedPreference: SharedPreferences
    private lateinit var Editnombres_perfil:EditText
    private lateinit var Editapellido_perfil:EditText
    private lateinit var Editcorreo_perfil:EditText
    private lateinit var Edittelefono_perfil:EditText
    private lateinit var getnombres_perfil: String
    private lateinit var getapellido_perfil:String
    private lateinit var getcorreo_perfil: String
    private lateinit var gettelefono_perfil: String
    private lateinit var btnGuardarInfo_editperfil : Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_editperfil, container, false)
        sharedPreference = requireActivity().getSharedPreferences("UserData", Context.MODE_PRIVATE)

        getnombres_perfil=sharedPreference.getString("nombres", "Username") ?: "username";
        getapellido_perfil=sharedPreference.getString("apellidos", "Userlastname") ?: "Userlastname";
        getcorreo_perfil = sharedPreference.getString("correo", "Useremail") ?: "Useremail";
        gettelefono_perfil = sharedPreference.getString("telefono", "Usertelefono") ?: "Usertelefono";

        Editnombres_perfil = view.findViewById(R.id.nombreuser_edit);
        Editapellido_perfil= view.findViewById(R.id.apellidouser_edit);
        Editcorreo_perfil=view.findViewById(R.id.correouser_edit);
        Edittelefono_perfil=view.findViewById(R.id.telefonouser_edit);

        Editcorreo_perfil.setText(getcorreo_perfil)
        Editnombres_perfil.setText(getnombres_perfil)
        Editapellido_perfil.setText(getapellido_perfil)
        Edittelefono_perfil.setText(gettelefono_perfil)

        btnGuardarInfo_editperfil = view.findViewById(R.id.btnsave_edit)
        btnGuardarInfo_editperfil.setOnClickListener{
            Editardatos()
            Toast.makeText(requireContext(), "Datos guardados", Toast.LENGTH_SHORT).show()
            findNavController(this).navigate(R.id.MiPerfilFragment)
        }
        return view
    }
    private fun Editardatos() {

        val nombre = Editnombres_perfil.text.toString().trim()
        val apellido = Editapellido_perfil.text.toString().trim()
        val correo = Editcorreo_perfil.text.toString().trim()
        val telefono = Edittelefono_perfil.text.toString().trim()

        val editor = sharedPreference.edit()

        if(nombre != getnombres_perfil){
            getnombres_perfil=nombre;
            editor.putString("nombres",nombre)
        }
        else if (apellido != getapellido_perfil){
            getapellido_perfil=apellido;
            editor.putString("apellidos",apellido)
        }
        else if (correo != getcorreo_perfil){
            getcorreo_perfil=correo;
            editor.putString("correo",correo)
        }
        else if(telefono != gettelefono_perfil){
            gettelefono_perfil=telefono;
            editor.putString("telefono",telefono)
        }

        editor.apply()
    }


    }