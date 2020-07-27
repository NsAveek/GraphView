package com.example.graphviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.graphviewapp.customview.GraphView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val coordinates = arrayListOf<Pair<Float,Float>>()
    lateinit var button :Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initCoordinates()

        val graphView = findViewById<GraphView>(R.id.graphView)
        graphView.setCoordinatePoints(coordinates)

        initRedrawButton()
    }

    private fun initCoordinates() {
        coordinates.add(Pair(100f,200f))
        coordinates.add(Pair(300f,400f))
        coordinates.add(Pair(400f,300f))
        coordinates.add(Pair(500f,500f))
        coordinates.add(Pair(600f,400f))
        coordinates.add(Pair(700f,200f))
    }
    private fun initRedrawButton(){
        button = findViewById(R.id.redraw)
        button.setOnClickListener {
            graphView.invalidate()
        }
    }
}