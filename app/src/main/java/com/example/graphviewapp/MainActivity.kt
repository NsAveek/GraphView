package com.example.graphviewapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.graphviewapp.customview.GraphView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    private val coordinates = arrayListOf<Pair<Float,Float>>()
    lateinit var button :Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initCoordinates()

        val graphView = findViewById<GraphView>(R.id.graphView)
        val graphView2 = findViewById<GraphView>(R.id.graphView2)
        graphView.setCoordinatePoints(coordinates)
        graphView2.setCoordinatePoints(coordinates)

        initRedrawButton()
    }

    private fun initCoordinates() {
        coordinates.add(Pair(0f,0f))
        coordinates.add(Pair(1f,2f))
        coordinates.add(Pair(3f,4f))
        coordinates.add(Pair(4f,3f))
        coordinates.add(Pair(5f,5f))
        coordinates.add(Pair(6f,4f))
        coordinates.add(Pair(7f,2f))

        coordinates.add(Pair(9f,2f))
        coordinates.add(Pair(10f,4f))
        coordinates.add(Pair(11f,3f))
        coordinates.add(Pair(12f,5f))
        coordinates.add(Pair(13f,4f))
        coordinates.add(Pair(15f,2f))

        coordinates.add(Pair(16f,7f))
        coordinates.add(Pair(20f,9f))
        coordinates.add(Pair(21f,3f))
        coordinates.add(Pair(22f,9f))
        coordinates.add(Pair(23f,7f))
        coordinates.add(Pair(25f,11f))

        coordinates.add(Pair(28f,2f))
        coordinates.add(Pair(30f,5f))
        coordinates.add(Pair(31f,3f))
        coordinates.add(Pair(32f,11f))
        coordinates.add(Pair(33f,9f))
        coordinates.add(Pair(35f,3f))
//
        coordinates.add(Pair(37f,7f))
        coordinates.add(Pair(39f,6f))
        coordinates.add(Pair(41f,4f))
        coordinates.add(Pair(42f,1f))
        coordinates.add(Pair(43f,1f))
        coordinates.add(Pair(45f,8f))
    }

    private fun initRedrawButton(){
        button = findViewById(R.id.redraw)
        button.setOnClickListener {
            graphView.invalidate()
        }
    }
}