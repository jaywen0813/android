package com.dpad.crmclientapp.android.widget

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator

import com.dpad.telematicsclientapp.netlibrary.R
import com.dpad.telematicsclientapp.util.utils.UIUtils


/**
 * ================================================
 * 作    者：wenbody
 * 版    本：1.0
 * 创建日期：2020/1/15 17:21
 * 描    述：
 * 修订历史：
 * ================================================
 */
class ChargingProgress @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var mPaintFlagsDrawFilter: PaintFlagsDrawFilter =
            PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG or Paint.FILTER_BITMAP_FLAG)
    private var mOuterGradientPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var diverLinePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var colors = intArrayOf(
            context.resources.getColor(R.color.color_start),
            context.resources.getColor(R.color.color_end)
    )
    private var bgBtm: Bitmap
    private var position: FloatArray? = floatArrayOf(0f, 1.0f)

    /**
     * view的高度
     */
    private var viewWidth = UIUtils.dip2px(110)
    private var viewHeight = viewWidth * 72 / 352
    private var paddingX = 25f
    private var paddingY = 15f

    private var chargingValue = 0f

    private var objectAnimator: ValueAnimator? = null

    init {
//        mOuterGradientPaint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP)
        val mShader: Shader =
                LinearGradient(
                        paddingX,
                        paddingY,
                        viewWidth - paddingX,
                        viewHeight - paddingY,
                        colors,
                        position,
                        Shader.TileMode.CLAMP
                )
        mOuterGradientPaint.shader = mShader
        mOuterGradientPaint.strokeCap = Paint.Cap.ROUND
        mOuterGradientPaint.style = Paint.Style.FILL
        diverLinePaint.style = Paint.Style.FILL
        diverLinePaint.color = resources.getColor(R.color.color_171717)

        val resources = this.resources
        val dm = resources.displayMetrics

        bgBtm = BitmapFactory.decodeResource(resources, R.mipmap.charging_bg)
        bgBtm = Bitmap.createScaledBitmap(bgBtm, viewWidth.toInt(), viewHeight.toInt(), true)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawFilter = mPaintFlagsDrawFilter
        canvas.drawBitmap(bgBtm, 0f, 0f, null)
        val y = viewHeight - paddingY
        val progressLength = (viewWidth - paddingX)
        val rect = RectF(paddingX, paddingY, progressLength, viewHeight - paddingY)
        canvas.drawRoundRect(rect, y, y, mOuterGradientPaint)
        for (item in 1 until progressLength.toInt()) {
            if (item % (viewWidth.toInt() / 6) == 0) {
                canvas.drawRect(
                        item.toFloat(),
                        paddingY,
                        item.toFloat() + 5,
                        viewHeight - paddingY,
                        diverLinePaint
                )
            }
        }

        var currentLength = (progressLength - paddingX) * chargingValue / 100

        val rectBg =
                RectF(paddingX + currentLength, paddingY, progressLength, viewHeight - paddingY)
        canvas.drawRect(rectBg, diverLinePaint)
    }


    fun startAnim(progress: Float) {
        setLayerType(LAYER_TYPE_HARDWARE, null)
        if (objectAnimator == null) {
            objectAnimator = ObjectAnimator.ofFloat(0f, 1000f)
        }
        objectAnimator?.addUpdateListener {
            this.chargingValue = (it.animatedValue as Float) * progress / 1000
            postInvalidate()
        }
        objectAnimator?.repeatCount = ValueAnimator.INFINITE
        objectAnimator?.duration = 1800
//        objectAnimator.interpolator = AccelerateDecelerateInterpolator()
        objectAnimator?.interpolator = LinearInterpolator()
        objectAnimator?.start()
    }

    /**
     * 设置静态的数据
     */
    fun showChargingData(data:Float){
        this.chargingValue = data
        postInvalidate()
    }

    /**
     * 结束动画,防止影响性能
     */
    fun cancleProgress() {
        objectAnimator?.cancel()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)


       val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
       val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
       val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
       val heightSpecSize = MeasureSpec.getMode(heightMeasureSpec)
        if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width,height)
        }else if (widthSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(width,heightSpecSize)
        }else if (heightSpecMode == MeasureSpec.AT_MOST) {
            setMeasuredDimension(widthSpecSize,height)
        }
        /**
         *社会view大小为设置值
         */
        setMeasuredDimension(viewWidth, viewHeight)
    }

}