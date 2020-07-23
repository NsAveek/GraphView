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
    private var extraPadding = convertDpToPx(10f)

    private lateinit var coordinates: ArrayList<Pair<Float, Float>>
    private var maxXValue: Float = 0f
    private var minXValue: Float = 0f
    private var maxYValue: Float = 0f
    private var minYValue: Float = 0f

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
        ) // TODO : Take the input from User

        path = Path()

        pathPaint = Paint().apply {
            color = ContextCompat.getColor(context, R.color.pathColor)
            isAntiAlias = true
            style = Paint.Style.STROKE
            strokeWidth = convertDpToPx(2f) // TODO : Take the input from the user
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
            textSize = convertDpToPx(10f)
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
        var firstData = true
        for (i in coordinates) {
            if (firstData){
                maxXValue = i.first
                minXValue = i.first
                maxYValue = i.second
                minYValue = i.second
                firstData = false
            }
            if (i.first > maxXValue) maxXValue = i.first
            if (i.first < minXValue) minXValue = i.first
            if (i.second > maxYValue) maxYValue = i.second
            if (i.second < minYValue) minYValue = i.second
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
        val measuredWidth = MeasureSpec.getSize(widthMeasureSpec)
        val measuredHeight = MeasureSpec.getSize(heightMeasureSpec)
        eachPixelAllocationX =  (measuredWidth-extraPadding*2)/ (maxXValue-minXValue)
        eachPixelAllocationY =  (measuredHeight-extraPadding*2)/ (maxYValue)
//        extraPadding = eachPixelAllocationX*2
        setMeasuredDimension(
            measuredWidth,
            measuredHeight
        )
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        actualWidth = w-extraPadding.toInt()
        actualHeight = h
//        actualHeight = h-extraPadding.toInt()
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
        var firstPathDraw : Boolean = true
        var initCoordinateX = 0f
        var initCoordinateY = 0f
        for (i in coordinates) {
            if (firstPathDraw) {
                initCoordinateX = i.first
                initCoordinateY = i.second
                path.moveTo(
                    reCalculateExactCoordinateWithPadding(
                        (initCoordinateX - i.first) * eachPixelAllocationX,
                        true
                    ),
                    reCalculateExactCoordinateWithPadding(
                        translateToCanvasY((initCoordinateY - i.second) * eachPixelAllocationY),
                        false
                    )
                )
                firstPathDraw = false

                path.lineTo(
                    reCalculateExactCoordinateWithPadding((initCoordinateX - i.first) * eachPixelAllocationX, true),
                    reCalculateExactCoordinateWithPadding(
                        translateToCanvasY(i.second * eachPixelAllocationY),
                        false
                    )
                )
            }else{
                path.lineTo(
                    reCalculateExactCoordinateWithPadding( (i.first - initCoordinateX) * eachPixelAllocationX, true),
                    reCalculateExactCoordinateWithPadding(
                        translateToCanvasY(i.second* eachPixelAllocationY),
                        false
                    )
                )
            }
        }
        path.lineTo(reCalculateExactCoordinateWithPadding((maxXValue- initCoordinateX)*eachPixelAllocationX,true), reCalculateExactCoordinateWithPadding(canvas.height.toFloat(),false))
//        path.close()
        canvas.drawPath(path, gradientPaint)
    }

    private fun drawCoordinates(canvas: Canvas) {
        path.reset()
        var firstPathDraw : Boolean = true
        var initCoordinateX = 0f
        for (i in coordinates) {
            if (firstPathDraw){
                initCoordinateX = i.first
                path.moveTo(reCalculateExactCoordinateWithPadding((initCoordinateX-i.first) * eachPixelAllocationX,true), reCalculateExactCoordinateWithPadding(translateToCanvasY((i.second) * eachPixelAllocationY),false))
                firstPathDraw = false

                path.lineTo(
                    reCalculateExactCoordinateWithPadding((initCoordinateX-i.first) * eachPixelAllocationX,true),
                    reCalculateExactCoordinateWithPadding(translateToCanvasY(i.second * eachPixelAllocationY),false)
                )
                drawCircle(
                    canvas,
                    reCalculateExactCoordinateWithPadding((initCoordinateX-i.first) * eachPixelAllocationX,true),
                    reCalculateExactCoordinateWithPadding(translateToCanvasY(i.second * eachPixelAllocationY),false)
                )
            }else{
                path.lineTo(
                    reCalculateExactCoordinateWithPadding((i.first - initCoordinateX) * eachPixelAllocationX,true),
                    reCalculateExactCoordinateWithPadding(translateToCanvasY(i.second * eachPixelAllocationY),false)
                )
                drawCircle(
                    canvas,
                    reCalculateExactCoordinateWithPadding((i.first - initCoordinateX) * eachPixelAllocationX,true),
                    reCalculateExactCoordinateWithPadding(translateToCanvasY(i.second * eachPixelAllocationY),false)
                )
            }


            Log.d("x : y = ", i.first.toString() + " : " + i.second.toString())
        }
        canvas.drawPath(path, pathPaint)
    }
    private fun drawGraduations(canvas: Canvas){
        for (i in 0..maxYValue.toInt()){
            canvas.drawText(i.toString(),0f,reCalculateExactCoordinateWithPadding(translateToCanvasY(i*eachPixelAllocationY),false),graduationPathPaint)
        }
        for (i in 0..maxXValue.toInt()){
            canvas.drawText(i.toString(),reCalculateExactCoordinateWithPadding(i*eachPixelAllocationX, true),canvas.height.toFloat(),graduationPathPaint)
        }
    }

    private fun drawCircle(canvas: Canvas, cx: Float, cy: Float) {
        canvas.drawCircle(cx, cy, convertDpToPx(3f), circlePaint)// TODO : Take the input from the user
    }

    private fun reCalculateExactCoordinateWithPadding(coord : Float, x : Boolean): Float{
        return when(x){
            true -> coord + (extraPadding)
            false -> coord - (extraPadding)
        }
    }
}