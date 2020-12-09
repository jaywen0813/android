package com.dpad.crmclientapp.android.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.Scroller
import com.dpad.telematicsclientapp.netlibrary.R
import com.dpad.telematicsclientapp.util.utils.UIUtils

import com.socks.library.KLog
import kotlin.math.abs


/**
 * ================================================
 * ��    �ߣ�wenbody
 * ��    ����1.0
 * �������ڣ�2020/1/3 11:31
 * ��    ����
 * �޶���ʷ��
 * ================================================
 */
class SlideButton @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    //״̬�ı����
    interface SlideButtonOnCheckedListener {
        fun onCheckedChangeListener(isChecked: Boolean)
    }

    private var mListener: SlideButtonOnCheckedListener? = null


    //�ؼ��ڱ߾�
    private var PADDING = 20f
    //�ƶ����ж�����
    private var MOVE_DISTANCE = 50f

    //Բ��x��Բ��
    private var circle_x = 0f


    private var mScroller: Scroller? = null
    //��ǰ��ť�Ŀ���״̬,ѡ��Ϊ����ԤԼ״̬
    private var isChecked = false

    private var mWidth = 0f
    private var mHeight = 0f

    private lateinit var mPaint: Paint
    private var circleStartX = 0f
    private var circleEndX = 0f
    private var centerX = 0f
    private var preX = 0f
    private var isMove = false


    private var yycdSelBtm: Bitmap
    private var yycdUnSelBtm: Bitmap
    private var ljcdSelBtm: Bitmap
    private var ljcdUnSelBtm: Bitmap

    private var yycdBgBtm: Bitmap

    private var drawY = 0f

    private var scale = 1f
    private var circlePadding = 15f
    private var needNotify = false
    private var isSetCheck = false


    /**
     * ���õ������
     *
     * @param listener
     */
    fun setOnCheckedListener(listener: SlideButtonOnCheckedListener?) {
        mListener = listener
    }

    /**
     * ���ð�ť״̬
     *
     * @param checked
     */
    fun setChecked(checked: Boolean) {
        isSetCheck = false
        isChecked = checked
        circle_x = if (isChecked) {
            circleEndX
        } else {
            circleStartX
        }
        invalidate()
    }

    init {
        isEnabled = true
        isClickable = true
        mPaint = Paint()
        mScroller = Scroller(context)
        yycdSelBtm = BitmapFactory.decodeResource(resources, R.mipmap.yycd_selected_im)
        yycdUnSelBtm = BitmapFactory.decodeResource(resources, R.mipmap.yycd_unselected_im)
        ljcdSelBtm = BitmapFactory.decodeResource(resources, R.mipmap.ljcd_selected_im)
        ljcdUnSelBtm = BitmapFactory.decodeResource(resources, R.mipmap.ljcd_unseleced_im)
        yycdBgBtm = BitmapFactory.decodeResource(resources, R.mipmap.yycd_bg)

        val dm = resources.displayMetrics
        PADDING = UIUtils.dip2px(60).toFloat()
        mWidth = dm.widthPixels.toFloat()
        mHeight = (mWidth - 2 * PADDING) * 288 / 968//ͼƬ��߱�
        scale = mHeight / yycdBgBtm.height


        yycdSelBtm = Bitmap.createScaledBitmap(yycdSelBtm, (yycdSelBtm.width * scale).toInt(), (yycdSelBtm.height * scale).toInt(), true)
        yycdUnSelBtm = Bitmap.createScaledBitmap(yycdUnSelBtm, (yycdUnSelBtm.width * scale).toInt(), (yycdUnSelBtm.height * scale).toInt(), true)
        ljcdSelBtm = Bitmap.createScaledBitmap(ljcdSelBtm, (ljcdSelBtm.width * scale).toInt(), (ljcdSelBtm.height * scale).toInt(), true)
        ljcdUnSelBtm = Bitmap.createScaledBitmap(ljcdUnSelBtm, (ljcdUnSelBtm.width * scale).toInt(), (ljcdUnSelBtm.height * scale).toInt(), true)
        yycdBgBtm = Bitmap.createScaledBitmap(yycdBgBtm, (yycdBgBtm.width * scale).toInt(), (yycdBgBtm.height * scale).toInt(), true)

        PADDING = (mWidth - yycdBgBtm.width) / 2

        drawY = (mHeight - yycdSelBtm.height) / 2 - 3//3 Ϊ΢��
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(mWidth.toInt(), mHeight.toInt())
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        MOVE_DISTANCE = mWidth / 10

        //�ڲ�Բ��x����ʼ����
        circleStartX = PADDING + circlePadding
        //�ڲ�Բ��x���յ�����
        circleEndX = mWidth - PADDING - yycdSelBtm.width - circlePadding
        circle_x = if (isChecked) {
            circleEndX
        } else {
            circleStartX
        }
        //�ؼ�������
        centerX = mWidth / 2
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawRect(canvas)
        drawCircle(canvas)
    }

    //��Բ�Ǿ���
    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun drawRect(canvas: Canvas) {
        mPaint.reset()
        mPaint.isAntiAlias = true
        mPaint.isDither = true
        canvas.drawBitmap(yycdBgBtm, PADDING, 0f, mPaint)
    }

    //�������Բ
    private fun drawCircle(canvas: Canvas) {
        mPaint.reset()
        mPaint.isAntiAlias = true
        mPaint.isDither = true
        if (isChecked) {
            canvas.drawBitmap(ljcdUnSelBtm, PADDING + circlePadding, drawY, mPaint)
            canvas.drawBitmap(yycdSelBtm, circle_x, drawY, mPaint)
        } else {
            canvas.drawBitmap(yycdUnSelBtm, mWidth - PADDING - yycdUnSelBtm.width - circlePadding, drawY, mPaint)
            canvas.drawBitmap(ljcdSelBtm, circle_x, drawY, mPaint)
        }

    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                needNotify = isChecked
                preX = event.x
//                if (isChecked) {
//                    if (preX < centerX) {
//                        return true
//                    }
//                } else {
//                    if (preX > centerX) {
//                        return true
//                    }
//                }
                isMove = false
//                circle_x = if (!isChecked) {
//                    PADDING
//                } else {
//                    mWidth - 2 * PADDING
//                }
            }
            MotionEvent.ACTION_MOVE -> {
                val move_x = event.x
                if (isChecked) {
                    if (preX < mWidth - PADDING - circlePadding - yycdSelBtm.width || preX > mWidth - PADDING) {
                        return true
                    }
                } else {
                    if (preX > PADDING + circlePadding + yycdSelBtm.width || preX < PADDING) {
                        return true
                    }
                }
                if (abs(move_x - preX) > MOVE_DISTANCE) {
                    isMove = true
                    if (move_x < circleStartX) {
                        circle_x = circleStartX
                        isChecked = false
                    } else if (move_x > circleEndX) {
                        circle_x = circleEndX
                        isChecked = true
                    } else {
                        circle_x = move_x
                    }
                    invalidate()
                }
            }
            MotionEvent.ACTION_UP -> {
                if (!isMove) {
                    return true
                }
                if (needNotify) {
                    if (preX < mWidth - PADDING - circlePadding - yycdSelBtm.width || preX > mWidth - PADDING) {
                        return true
                    }
                } else {
                    if (preX > PADDING + circlePadding + yycdSelBtm.width || preX < PADDING) {
                        return true
                    }
                }
                isChecked = if (isMove) {
                    if (circle_x >= centerX) { //�ر�(ִ�п���)
                        mScroller!!.startScroll(circle_x.toInt(), 0, (circleEndX - circle_x).toInt(), 0)
                        true
                    } else { //������ִ�йرգ�
                        mScroller!!.startScroll(circle_x.toInt(), 0, (circleStartX - circle_x).toInt(), 0)
                        false
                    }
                } else {
                    isChecked
                }
//                else {
//                    if (!isChecked) { //�ر�(ִ�п���)
//                        mScroller!!.startScroll(circle_x.toInt(), 0, (circleEndX - circle_x).toInt(), 0)
//                        true
//                    } else { //������ִ�йرգ�
//                        mScroller!!.startScroll(circle_x.toInt(), 0, (circleStartX - circle_x).toInt(), 0)
//                        false
//                    }
//                }
                if (mListener != null) {
                    if (isChecked != needNotify && !isSetCheck) {
                        mListener!!.onCheckedChangeListener(isChecked)
                    }
                    isSetCheck = false
                }
                invalidate()
            }
        }
        return super.onTouchEvent(event)
    }

    override fun computeScroll() {
        if (mScroller!!.computeScrollOffset()) {
            circle_x = mScroller!!.currX.toFloat()
            invalidate()
        }
    }

    /**
     * dpתpx
     *
     * @param context ������,dp dp
     * @return px
     */
    fun dip2px(context: Context, dp: Int): Int {
        val density = context.resources.displayMetrics.density
        return (dp * density + 0.5).toInt()
    }
}