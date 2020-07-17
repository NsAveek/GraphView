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
        coordinates.add(Pair(1f,2f))
        coordinates.add(Pair(3f,4f))
        coordinates.add(Pair(4f,3f))
        coordinates.add(Pair(5f,5f))
        coordinates.add(Pair(6f,4f))
        coordinates.add(Pair(7f,2f))
    }
    private fun initRedrawButton(){
        button = findViewById(R.id.redraw)
        button.setOnClickListener {
            graphView.invalidate()
        }
    }
}