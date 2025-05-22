package com.example.proyectonativas.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.proyectonativas.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class TiendasFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private val ubicacion1 = LatLng(4.588878890281133, -74.20450605331324)
    private val ubicacion2 = LatLng(4.579165, -74.205156)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.tiendas_fragment, container, false)

        // Usamos SupportMapFragment con childFragmentManager para fragmentos internos
        val mapFragment = childFragmentManager.findFragmentById(R.id.mapa) as SupportMapFragment
        mapFragment.getMapAsync(this)

        // Configuramos los botones para mover el mapa
        setupButtons(view)

        return view
    }

    private fun setupButtons(view: View) {
        val button1 = view.findViewById<Button>(R.id.btntienda1)
        val button2 = view.findViewById<Button>(R.id.btntienda2)

        button1.setOnClickListener {
            moveToLocation(ubicacion1, "Tienda 1")
        }

        button2.setOnClickListener {
            moveToLocation(ubicacion2, "Tienda 2")
        }
    }

    private fun moveToLocation(ubicacion: LatLng, title: String) {
        mMap.clear()
        mMap.addMarker(MarkerOptions().position(ubicacion).title(title))
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacion, 12f))
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap.uiSettings.isZoomControlsEnabled = true
        moveToLocation(ubicacion1, "Tienda 1")
    }
}
