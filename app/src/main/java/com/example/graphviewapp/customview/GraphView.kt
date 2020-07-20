package com.example.graphviewapp.customview

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.annotation.Px
import androidx.core.content.ContextCompat
import com.example.graphviewapp.R

// TODO
// 1. Get points or co-ordinates from the user/system
// 2. Sort the X co-ordinates
// 2. Get colors from the user/system
// 3. Get the graph shape (i.e : Bar, Pie ) from the user/system
// 4. Get the thickness of the lines from the user/system
// 5. Get the interval (i.e: 200,300,400) from the user/system
// 6. Get the initial starting co-ordinates from the user/system ( i.e: topToBottom or bottomToTop)


class GraphView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null) :
    View(context, attrs) {

    private var path: Path
    private var pathPaint: Paint
    private var gradientPaint: Paint
    private var graduationPathPaint: Paint
    private var colorsArray: IntArray
    private var circlePaint: Paint
    private var extraPadding = 0f

    private lateinit var coordinates: ArrayList<Pair<Float, Float>>
    private var maxXValue: Float = 0f
    private var maxYValue: Float = 0f

    private var eachPixelAllocationX = 0f
    private var eachPixelAllocationY = 0f

    private var actualWidth = 0
    private var actualHeight = 0

    init {
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.GraphView
        )
        colorsArray = intArrayOf(
            ContextCompat.getColor(context, R.color.startColor),
            ContextCompat.getColor(context, R.color.endColor)
        )

        path = Path()

        pathPaint = Paint().apply {
            color = ContextCompat.getColor(context, R.color.pathColor)
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = 5f
            isDither = true
        }
        gradientPaint = Paint().apply {
            isAntiAlias =true
            style = Paint.Style.FILL
        }
        graduationPathPaint = Paint().apply {
            style = Paint.Style.STROKE
            isAntiAlias = true
            color = ContextCompat.getColor(context, R.color.graduationColor)
            textSize = 40F
        }
        circlePaint = Paint().apply {
            color = ContextCompat.getColor(context, R.color.pathColor)
            isAntiAlias = true
        }
        a.recycle()
    }

    fun setCoordinatePoints(coordinates: ArrayList<Pair<Float, Float>>) {
        this.coordinates = coordinates
        getMaxCoordinateValues()
        sortXCoordinates()
        invalidate()
    }

    private fun getMaxCoordinateValues() {
        for (i in coordinates) {
            if (i.first > maxXValue) maxXValue = convertDpToPx(i.first)
            if (i.second > maxYValue) maxYValue = convertDpToPx(i.second)
        }
    }
    private fun sortXCoordinates(){

    }

    private fun convertPxToDp(px: Float): Float {
        return px / context.resources.displayMetrics.density
    }

    private fun convertDpToPx(dp: Float): Float {
        return dp * context.resources.displayMetrics.density
    }

    private fun translateToCanvasY(y: Float): Float {
        return actualHeight.toFloat() - y
    }

    private fun translateToCanvasX(canvas: Canvas, x: Float): Float {
        return x
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        eachPixelAllocationX = MeasureSpec.getSize(widthMeasureSpec) / maxXValue
        eachPixelAllocationY = MeasureSpec.getSize(heightMeasureSpec) / maxYValue
//        extraPadding = eachPixelAllocationX*2
        setMeasuredDimension(
            widthMeasureSpec - extraPadding.toInt(),
            heightMeasureSpec - extraPadding.toInt()
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        actualWidth = w
        actualHeight = h
        val positions = floatArrayOf(0f,1f)
        gradientPaint.shader = LinearGradient(0f, 0f, 0f, actualHeight.toFloat(), colorsArray,positions, Shader.TileMode.CLAMP)
        super.onSizeChanged(w, h, oldw, oldh)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        drawGradients(canvas)
        drawCoordinates(canvas)
        drawGraduations(canvas)
    }

    private fun drawGradients(canvas: Canvas) {
        path.reset()
        path.moveTo(0f + extraPadding, canvas.height + extraPadding)

        for (i in coordinates) {
            path.lineTo(
                (i.first * eachPixelAllocationX) + extraPadding,
                translateToCanvasY(i.second * eachPixelAllocationY) + extraPadding
            )
        }
        path.lineTo(canvas.width.toFloat(), canvas.height.toFloat())
//        path.lineTo(0f + extraPadding, canvas.height + extraPadding) // No need as default the path draws another line back to initial coordinates
        path.close()
        canvas.drawPath(path, gradientPaint)
    }

    private fun drawCoordinates(canvas: Canvas) {
        path.reset()
        path.moveTo(0f + extraPadding, canvas.height + extraPadding)

        for (i in coordinates) {
            path.lineTo(
                (i.first * eachPixelAllocationX) + extraPadding,
                translateToCanvasY(i.second * eachPixelAllocationY) + extraPadding
            )
            drawCircle(
                canvas,
                i.first * eachPixelAllocationX + extraPadding,
                translateToCanvasY(i.second * eachPixelAllocationY) + extraPadding
            )
            Log.d("x : y = ", i.first.toString() + " : " + i.second.toString())
        }
//        path.lineTo(canvas.width.toFloat(), canvas.height.toFloat())
//        path.moveTo(canvas.width.toFloat(), canvas.height.toFloat()) // Important to remove the draw line back to initial coordinate
//        path.close()
        canvas.drawPath(path, pathPaint)
    }
    private fun drawGraduations(canvas: Canvas){
        for (i in coordinates){
            canvas.drawText(i.first.toString(),i.first*eachPixelAllocationX,canvas.height.toFloat(),graduationPathPaint)
        }
    }

    private fun drawCircle(canvas: Canvas, cx: Float, cy: Float) {
        canvas.drawCircle(cx, cy, 10f, circlePaint)
    }
}