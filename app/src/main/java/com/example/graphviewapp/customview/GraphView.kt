package com.example.graphviewapp.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.graphviewapp.R

// TODO
// 1. Get points or co-ordinates from the user/system
// 2. Get colors from the user/system
// 3. Get the graph shape (i.e : Bar, Pie ) from the user/system
// 4. Get the thickness of the lines from the user/system
// 5. Get the interval (i.e: 200,300,400) from the user/system
// 6. Get the initial starting co-ordinates from the user/system ( i.e: topToBottom or bottomToTop)


class GraphView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) : View(context, attrs) {

    private var path : Path
    private var paint : Paint
    private var extraPadding = 10

    private lateinit var coordinates : ArrayList<Pair<Float,Float>>

    init{
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.GraphView)
        path = Path()
        paint = Paint().apply {
            color = Color.BLUE
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = 5f
            isDither = true
        }
        a.recycle()
    }

    fun setCoordinatePoints(coordinates : ArrayList<Pair<Float,Float>>){
        this.coordinates = coordinates
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(widthMeasureSpec,heightMeasureSpec)
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCoordinates(canvas)
    }

    private fun drawCoordinates(canvas: Canvas) {
        path.moveTo(0f+extraPadding, 0f+extraPadding)
        for (i in coordinates) {
            path.lineTo(i.first+extraPadding, i.second+extraPadding)
            path.moveTo(i.first+extraPadding, i.second+extraPadding)
            Log.d("x : y", i.first.toString() + " : "+i.second.toString())
        }
        path.close()
        canvas.drawPath(path, paint)
    }
}