package com.example.graphviewapp.customview

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.Px
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
    private var extraPadding = 10f

    private lateinit var coordinates : ArrayList<Pair<Float,Float>>
    private var maxXValue : Float = 0f
    private var maxYValue : Float = 0f

    private var eachPixelAllocationX = 0f
    private var eachPixelAllocationY = 0f

    private var actualWidth = 0
    private var actualHeight = 0

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
        getMaxCoordinateValues()
        invalidate()
    }

    private fun getMaxCoordinateValues(){
        for(i in coordinates){
            if (i.first > maxXValue) maxXValue = convertDpToPx(i.first)
            if (i.second > maxYValue) maxYValue = convertDpToPx(i.second)
        }
    }
    private fun convertPxToDp(px: Float) : Float{
        return px/context.resources.displayMetrics.density
    }
    private fun convertDpToPx(dp : Float) :Float{
        return dp*context.resources.displayMetrics.density
    }
    private fun translateToCanvasY( y : Float) : Float{
        Log.d("canvas y : ", (actualHeight.toFloat() - y).toString())
//        return convertDpToPx(actualHeight.toFloat()) - y
        return actualHeight.toFloat() - y
    }
    private fun translateToCanvasX(canvas: Canvas, x : Float) : Float{
        return x
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        eachPixelAllocationX = MeasureSpec.getSize(widthMeasureSpec)/maxXValue
        eachPixelAllocationY = MeasureSpec.getSize(heightMeasureSpec)/maxYValue
        extraPadding = eachPixelAllocationX*2
        setMeasuredDimension(widthMeasureSpec-extraPadding.toInt(),heightMeasureSpec-extraPadding.toInt())
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        actualWidth = w
        actualHeight = h
        super.onSizeChanged(w, h, oldw, oldh)
    }
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawCoordinates(canvas)
    }

    private fun drawCoordinates(canvas: Canvas) {
//        path.moveTo(0f+extraPadding, translateToCanvasY(0f+extraPadding))
        path.moveTo(0f+extraPadding, canvas.height+extraPadding)

        for (i in coordinates) {
            path.lineTo((i.first*eachPixelAllocationX)+extraPadding, translateToCanvasY(i.second*eachPixelAllocationY)+extraPadding)
            path.moveTo((i.first*eachPixelAllocationX)+extraPadding, translateToCanvasY(i.second*eachPixelAllocationY)+extraPadding)
//            path.lineTo((i.first*eachPixelAllocationX)+extraPadding, (i.second*eachPixelAllocationY)+extraPadding)
//            path.moveTo((i.first*eachPixelAllocationX)+extraPadding, (i.second*eachPixelAllocationY)+extraPadding)

            Log.d("x : y = ", i.first.toString() + " : "+i.second.toString())
        }
        path.lineTo(canvas.width.toFloat(),canvas.height.toFloat())
        path.moveTo(canvas.width.toFloat(),canvas.height.toFloat())
        path.close()
        canvas.drawPath(path, paint)
    }
}