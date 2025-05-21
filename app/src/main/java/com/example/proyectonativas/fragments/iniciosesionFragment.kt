package com.example.proyectonativas.fragments

import android.app.ComponentCaller
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCaller
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment.Companion.findNavController
import com.example.proyectonativas.MainActivity
import com.example.proyectonativas.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task

class iniciosesionFragment : Fragment() {

    private lateinit var linkcontrasena: TextView
    private lateinit var btniniciarsesion: Button
    private lateinit var sharedPreference: SharedPreferences
    private lateinit var getTextCorreo: String
    private lateinit var getTextContrasena: String
    private lateinit var Acorreo: EditText
    private lateinit var Acontrasena: EditText
    private lateinit var Linkregistrarse: TextView
    private lateinit var btngoogle: Button
    private lateinit var mGoogleSingInClient : GoogleSignInClient
    private val RC_SING_IN = 1234
    private val TAG ="INICIO EXITOSO CON GOOGLE"




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_iniciosesion, container, false)

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestProfile()
            .build()


        mGoogleSingInClient = GoogleSignIn.getClient(requireContext(), gso)

        btngoogle = view.findViewById(R.id.btngoogle)
        btngoogle.setOnClickListener {
            SingIn()
        }

        sharedPreference = requireContext().getSharedPreferences("UserData", Context.MODE_PRIVATE)

        getTextCorreo = sharedPreference.getString("correo", "Useremail") ?: "Useremail";

        getTextContrasena =
            sharedPreference.getString("contrasena", "Usercontrasena") ?: "Usercontrasena";

        Acorreo = view.findViewById(R.id.correo)
        Acontrasena = view.findViewById(R.id.passwordInicio)

        btniniciarsesion = view.findViewById(R.id.btniniciarsesion)
        btniniciarsesion.setOnClickListener {
            iniciarSesion()
        }

        linkcontrasena = view.findViewById(R.id.Olvidemicontraseña)
        linkcontrasena.setOnClickListener {
            findNavController(this).navigate(R.id.RecuperarFragment)
        }

        Linkregistrarse = view.findViewById(R.id.texts2registro)
        Linkregistrarse.setOnClickListener {
            findNavController(this).navigate(R.id.registro)
        }

        return view
    }
    private fun SingIn(){
        val singInIntent = mGoogleSingInClient.signInIntent
        startActivityForResult(singInIntent, RC_SING_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?){
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SING_IN){
            val task =GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSingInResult(task)
        }
    }

    private fun handleSingInResult (completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            Log.d(TAG, "Inicio de sesión exitoso: ${account.email}")
            val nombre= account.givenName
            val apellido= account.familyName

            // Guardar datos básicos en SharedPreferences
            val editor = sharedPreference.edit()
            editor.putString("nombres", nombre.toString().trim())
            editor.putString("apellidos", apellido.toString().trim())
            editor.putString("correo", account.email)

            // Verificar si es la primera vez
            val primeraVez = sharedPreference.getBoolean("esPrimeraVezGoogle", true)

            if (primeraVez) {
                editor.putBoolean("esPrimeraVezGoogle", false)
                editor.apply()
                Toast.makeText(requireContext(), "Bienvenido a VentFort, ${account.givenName}", Toast.LENGTH_SHORT).show()
                findNavController(this).navigate(R.id.registro_google)
            } else {
                editor.apply()
                main()
            }

        } catch (e: ApiException) {
            Log.e(TAG, "Error en el inicio de sesión con Google", e)
            Toast.makeText(requireContext(), "Error en el inicio de sesión con Google", Toast.LENGTH_SHORT).show()
        }
    }
    private fun iniciarSesion() {
        val correoIngresado = Acorreo.text.toString().trim()
        val contrasenaIngresado = Acontrasena.text.toString().trim()

        if (correoIngresado == getTextCorreo && contrasenaIngresado == getTextContrasena || correoIngresado == "admin" && contrasenaIngresado == "admin") {
            // Login exitoso

            if(correoIngresado == "admin" && contrasenaIngresado == "admin"){
                val editor = sharedPreference.edit()
                editor.putString("rol", "admin")
                editor.apply()
            }

            Toast.makeText(requireContext(), "Login exitoso", Toast.LENGTH_SHORT).show()
            main()

        } else {
            // Login fallido
            Toast.makeText(requireContext(), "Correo o contraseña incorrectos", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun main() {
        val intent = Intent(requireContext(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }


}