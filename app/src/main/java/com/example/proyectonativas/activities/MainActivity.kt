package com.example.proyectonativas.activities

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.proyectonativas.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_agregar_producto)

        /*
        val lineChart = findViewById<LineChart>(R.id.lineChart)
        val lineChart1 = findViewById<LineChart>(R.id.lineChartt)
        val graficaUsuarios = findViewById<LineChart>(R.id.graficaUsuarios)
        val graficaProductos = findViewById<LineChart>(R.id.graficaProductos)


        // Tus datos (X, Y)
        val entries = listOf(
            Entry(1f, 10f),
            Entry(2f, 20f),
            Entry(3f, 15f),
            Entry(4f, 30f),
            Entry(5f, 25f)
        )

        val dataSet = LineDataSet(entries, "Ventas")
        dataSet.color = getColor(R.color.dorado)
        dataSet.setCircleColor(getColor(R.color.blanco))
        dataSet.lineWidth = 2f;
        dataSet.valueTextSize = 12f
        dataSet.valueTextColor = getColor(R.color.blanco)

        val legend = lineChart.legend
        legend.textColor = getColor(R.color.blanco)

        val lineData = LineData(dataSet)
        lineChart.data = lineData
        lineChart1.data = lineData
        graficaUsuarios.data = lineData
        graficaProductos.data = lineData

        // Opcional: configuración del eje X
        val xAxis: XAxis = lineChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor = getColor(R.color.blanco)

        val leftAxis = lineChart.axisLeft
        leftAxis.textColor = getColor(R.color.blanco)

        val rightAxis = lineChart.axisRight

        val xAxis1: XAxis = lineChart1.xAxis
        xAxis1.position = XAxis.XAxisPosition.BOTTOM
        xAxis1.textColor = getColor(R.color.blanco)

        val leftAxis1 = lineChart1.axisLeft
        leftAxis1.textColor = getColor(R.color.blanco)

        val rightAxis1 = lineChart1.axisRight

        val xAxisUsuarios : XAxis = graficaUsuarios.xAxis
        xAxisUsuarios.position = XAxis.XAxisPosition.BOTTOM
        xAxisUsuarios.textColor = getColor(R.color.blanco)

        val leftAxisUsuarios = graficaUsuarios.axisLeft
        leftAxisUsuarios.textColor = getColor(R.color.blanco)

        val rightAxisUsuarios = graficaUsuarios.axisRight

        val xAxisProductos : XAxis = graficaProductos.xAxis
        xAxisProductos.position = XAxis.XAxisPosition.BOTTOM
        xAxisProductos.textColor = getColor(R.color.blanco)

        val leftAxisProductos = graficaProductos.axisLeft
        leftAxisProductos.textColor = getColor(R.color.blanco)

        val rightAxisProductos = graficaProductos.axisRight

        // Refrescar gráfico
        lineChart.invalidate()
        lineChart1.invalidate()
        graficaUsuarios.invalidate()
        graficaProductos.invalidate()*/
    }
}