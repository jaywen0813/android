package com.dpad.crmclientapp.android.widget

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.animation.LinearInterpolator

import com.dpad.telematicsclientapp.netlibrary.R
import com.socks.library.KLog
import kotlin.math.*

/**
 * ================================================
 * 作    者：wenbody
 * 版    本：1.0
 * 创建日期：2020/1/1 14:19
 * 描    述：
 * 修订历史：
 * ================================================
 */
class MoonAndSunView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    /**
     * 整个view的宽度和高度
     */
    var mWidth = 0
    var mHeight = 0

    var moonBgIm: Bitmap
    var sunBgIm: Bitmap

    var moonIm: Bitmap
    var sunIm: Bitmap

    /**
     * 默认为白天
     */
    var isNight = false


    var x1 = 0
    var y1 = 0
    var rx = 0
    var ry = 0

    var startCorner = 0f

    var progress = 0f
    var mR = 0f
    val vPaintNight: Paint
    var vPaintDay: Paint

    init {
        val resources = this.resources
//        val dm = resources.displayMetrics
//        mWidth = dm.widthPixels
        mWidth = getScreenWidth()
        mHeight = mWidth * 714 / 1502
        moonBgIm = BitmapFactory.decodeResource(resources, R.mipmap.usercenter_bg)
        sunBgIm = BitmapFactory.decodeResource(resources, R.mipmap.usercenter_day_bg)
        moonBgIm= Bitmap.createScaledBitmap(moonBgIm, mWidth, mHeight, true)
        sunBgIm= Bitmap.createScaledBitmap(sunBgIm, mWidth, mHeight, true)
        moonIm = BitmapFactory.decodeResource(resources, R.mipmap.usercenter_moon)
        sunIm = BitmapFactory.decodeResource(resources, R.mipmap.sun_im)
        rx = mWidth / 2
        y1 = mWidth / 5
        ry = y1 + mWidth / 3
        startCorner = tan(2 * (ry.toFloat() - y1) / mWidth)
        mR = sqrt((mWidth / 2) * (mWidth / 2).toFloat() + (ry - y1) * (ry - y1))

        vPaintNight = Paint()
        vPaintDay = Paint()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        KLog.e(widthSize.toString()+"-----" + mWidth.toString(), "宽度")
        mWidth = if (widthSize >= mWidth) {
            widthSize
        } else mWidth
        setMeasuredDimension(mWidth, mHeight)
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        vPaintNight.style = Paint.Style.STROKE
        vPaintNight.alpha = progress.toInt() * 255 / 100

        vPaintDay.style = Paint.Style.STROKE
        vPaintDay.alpha = (100 - progress).toInt() * 255 / 100

        val mSrcRect = Rect(0, 0, mWidth, mHeight)
        val mDestRect = Rect(0, 0, mWidth, mHeight)
        if (isNight) {
            canvas.drawBitmap(sunBgIm, mSrcRect, mDestRect, vPaintDay)
            canvas.drawBitmap(moonBgIm, mSrcRect, mDestRect, vPaintNight)
        } else {

            canvas.drawBitmap(moonBgIm, mSrcRect, mDestRect, vPaintDay)
            canvas.drawBitmap(sunBgIm, mSrcRect, mDestRect, vPaintNight)
        }

        if (progress <= 20) {
            val y = sin((startCorner + (PI - 2 * startCorner)) + startCorner * progress / 20).toFloat()
            val x = cos((startCorner + (PI - 2 * startCorner)) + startCorner * progress / 20).toFloat()

            canvas.drawBitmap(if (isNight) sunIm else moonIm, (rx - x * mR) - sunIm.width / 2, (ry - y * mR) - sunIm.height / 2, null)
        } else {
            val y = sin(((progress - 20) * (PI - startCorner) / 80)).toFloat()
            val x = cos(((progress - 20) * (PI - startCorner) / 80)).toFloat()
            canvas.drawBitmap(if (isNight) moonIm else sunIm, (rx - x * mR) - sunIm.width / 2, (ry - y * mR) - sunIm.height / 2, null)
        }


    }

    fun setDay(isNight: Boolean) {
        if (isNight != isNight) {
            this.isNight = isNight
            postInvalidate()
        }
    }


    fun startAnimator(isNight: Boolean) {
        this.isNight = isNight
        val objectAnimator: ValueAnimator = ObjectAnimator.ofFloat(0f, 100f)
        objectAnimator.addUpdateListener {
            this.progress = it.animatedValue as Float
            postInvalidate()
        }
        objectAnimator.duration = 1200
        objectAnimator.interpolator = LinearInterpolator()
        objectAnimator.start()
    }

    fun getScreenWidth(): Int {
        val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val displayMetrics = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.widthPixels
    }
}