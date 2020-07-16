package com.example.graphviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.graphviewapp.customview.GraphView

class MainActivity : AppCompatActivity() {
    val coordinates = arrayListOf<Pair<Float,Float>>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initCoordinates()

        val graphView = findViewById<GraphView>(R.id.graphView)
        graphView.setCoordinatePoints(coordinates)
    }

    private fun initCoordinates() {
        coordinates.add(Pair(0f,250f))
        coordinates.add(Pair(250f,250f))
        coordinates.add(Pair(350f,365f))
        coordinates.add(Pair(150f,500f))
    }
}