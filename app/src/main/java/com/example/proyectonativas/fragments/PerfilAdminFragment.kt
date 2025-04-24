package com.example.proyectonativas.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.proyectonativas.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.LineData


class PerfilAdminFragment : Fragment() {
    private lateinit var bt_gestionarProductos: Button
    private lateinit var bt_gestionarPedidos: Button
    private lateinit var bt_gestionarUsuarios: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_perfil_admin, container, false)
        val lineChart = view.findViewById<LineChart>(R.id.graficaPedidos)
        val lineChart1 = view.findViewById<LineChart>(R.id.graficaIngresos)
        val graficaUsuarios = view.findViewById<LineChart>(R.id.graficaUsuarios)
        val graficaProductos = view.findViewById<LineChart>(R.id.graficaProductos)


        val entries = listOf(
            Entry(1f, 10f),
            Entry(2f, 20f),
            Entry(3f, 15f),
            Entry(4f, 30f),
            Entry(5f, 25f)
        )

        val dataSet = LineDataSet(entries, "Ventas")
        dataSet.color = ContextCompat.getColor(requireContext(), R.color.dorado)
        dataSet.setCircleColor(ContextCompat.getColor(requireContext(), R.color.blanco))
        dataSet.lineWidth = 2f;
        dataSet.valueTextSize = 12f
        dataSet.valueTextColor = ContextCompat.getColor(requireContext(), R.color.blanco)

        val legend = lineChart.legend
        legend.textColor = ContextCompat.getColor(requireContext(), R.color.blanco)

        val lineData = LineData(dataSet)
        lineChart.data = lineData
        lineChart1.data = lineData
        graficaUsuarios.data = lineData
        graficaProductos.data = lineData

        val xAxis: XAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor = ContextCompat.getColor(requireContext(), R.color.blanco)

        val leftAxis = lineChart.axisLeft
        leftAxis.textColor = ContextCompat.getColor(requireContext(), R.color.blanco)

        val rightAxis = lineChart.axisRight

        val xAxis1: XAxis = lineChart1.xAxis
        xAxis1.position = XAxis.XAxisPosition.BOTTOM
        xAxis1.textColor = ContextCompat.getColor(requireContext(), R.color.blanco)

        val leftAxis1 = lineChart1.axisLeft
        leftAxis1.textColor = ContextCompat.getColor(requireContext(), R.color.blanco)

        val rightAxis1 = lineChart1.axisRight

        val xAxisUsuarios: XAxis = graficaUsuarios.xAxis
        xAxisUsuarios.position = XAxis.XAxisPosition.BOTTOM
        xAxisUsuarios.textColor = ContextCompat.getColor(requireContext(), R.color.blanco)

        val leftAxisUsuarios = graficaUsuarios.axisLeft
        leftAxisUsuarios.textColor = ContextCompat.getColor(requireContext(), R.color.blanco)

        val rightAxisUsuarios = graficaUsuarios.axisRight

        val xAxisProductos: XAxis = graficaProductos.xAxis
        xAxisProductos.position = XAxis.XAxisPosition.BOTTOM
        xAxisProductos.textColor = ContextCompat.getColor(requireContext(), R.color.blanco)

        val leftAxisProductos = graficaProductos.axisLeft
        leftAxisProductos.textColor = ContextCompat.getColor(requireContext(), R.color.blanco)

        val rightAxisProductos = graficaProductos.axisRight

        lineChart.invalidate()
        lineChart1.invalidate()
        graficaUsuarios.invalidate()
        graficaProductos.invalidate()

        bt_gestionarProductos = view.findViewById(R.id.bt_gestionarProductos)

        bt_gestionarProductos.setOnClickListener {
            findNavController().navigate(R.id.action_perfilAdminAGestionProductos)
        }

        bt_gestionarPedidos = view.findViewById(R.id.bt_gestionarPedidos)

        bt_gestionarPedidos.setOnClickListener {
            findNavController().navigate(R.id.action_perfilAdminAGestionPedidos)
        }

        bt_gestionarUsuarios = view.findViewById(R.id.bt_gestionarUsuarios)

        bt_gestionarUsuarios.setOnClickListener {
            findNavController().navigate(R.id.action_perfilAdminAGestionUsuarios)
        }

        return view
    }
}