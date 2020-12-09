package com.dpad.crmclientapp.android.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator

import com.dpad.crmclientapp.android.util.utils.FLOATBUTTON_IS_ALIGN_LEFT_KEY
import com.dpad.crmclientapp.android.util.utils.FLOATBUTTON_POSITION_KEY

import com.dpad.telematicsclientapp.netlibrary.R
import com.dpad.telematicsclientapp.util.PreferenceUtils
import com.dpad.telematicsclientapp.util.utils.UIUtils
import com.socks.library.KLog
import kotlin.math.sqrt

/**
 * ================================================
 * ��    �ߣ�wenbody
 * ��    ����1.0
 * �������ڣ�2020/1/8 16:01
 * ��    ����
 * �޶���ʷ��
 * ================================================
 */
class FloatingView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private var isFirstSetData: Boolean = true
    private var mLastRawX = 0f
    private var mLastRawY = 0f
    private var isDrug = false
    private var mRootMeasuredWidth = 0
    private var mRootMeasuredHeight = 0
    private var mRootTopY = 0
    private var customIsAttach = false
    private var customIsDrag = false
    private var mWidth: Float


    init {
        initAttrs(context, attrs)
        val dm = resources.displayMetrics

        mWidth = dm.widthPixels.toFloat()
    }

    /**
     * ��ʼ���Զ�������
     */
    private fun initAttrs(context: Context, attrs: AttributeSet) {
        val mTypedAttay = context.obtainStyledAttributes(attrs, R.styleable.AttachButton)
        customIsAttach = mTypedAttay.getBoolean(R.styleable.AttachButton_customIsAttach, true)
        customIsDrag = mTypedAttay.getBoolean(R.styleable.AttachButton_customIsDrag, true)
        mTypedAttay.recycle()
    }

    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        super.dispatchTouchEvent(event)
        return true
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(ev: MotionEvent): Boolean { //�ж��Ƿ���Ҫ����
        if (customIsDrag) { //��ǰ��ָ������
            val mRawX = ev.rawX
            val mRawY = ev.rawY
            when (ev.action) {
                MotionEvent.ACTION_DOWN -> {
                    isDrug = false
                    //��¼���µ�λ��
                    mLastRawX = mRawX
                    mLastRawY = mRawY
                    val mViewGroup = parent as ViewGroup
                    val location = IntArray(2)
                    mViewGroup.getLocationInWindow(location)
                    //��ȡ�����ֵĸ߶�
                    mRootMeasuredHeight = mViewGroup.measuredHeight
                    mRootMeasuredWidth = mViewGroup.measuredWidth
                    //��ȡ�����ֶ��������
                    mRootTopY = location[1]
                }
                MotionEvent.ACTION_MOVE -> if (mRawX > 0 && mRawX <= mRootMeasuredWidth && mRawY >= mRootTopY && mRawY <= mRootMeasuredHeight + mRootTopY) { //��ָX�Ử������
                    val differenceValueX = mRawX - mLastRawX
                    //��ָY�Ử������
                    val differenceValueY = mRawY - mLastRawY
                    //�ж��Ƿ�Ϊ�϶�����
                    if (!isDrug) {
                        isDrug = sqrt(differenceValueX * differenceValueX + differenceValueY * differenceValueY.toDouble()) >= 2
                    }
                    //��ȡ��ָ���µľ�����ؼ�����X��ľ���
                    val ownX = x
                    //��ȡ��ָ���µľ�����ؼ�����Y��ľ���
                    val ownY = y
                    //������X���϶��ľ���
                    var endX = ownX + differenceValueX
                    //������Y���϶��ľ���
                    var endY = ownY + differenceValueY
                    //X������϶���������
                    val maxX = mRootMeasuredWidth - width.toFloat()
                    //Y������϶���������
                    val maxY = mRootMeasuredHeight - height.toFloat()
                    //X��߽�����
                    endX = if (endX < 0) 0f else if (endX > maxX) maxX else endX
                    //Y��߽�����
                    endY = if (endY < 0) 0f else if (endY > maxY) maxY else endY
                    //��ʼ�ƶ�
                    x = endX
                    y = endY
                    //��¼λ��
                    mLastRawX = mRawX
                    mLastRawY = mRawY
                    this.setBackgroundResource(R.mipmap.floating_middle)
                }
                MotionEvent.ACTION_UP ->  //�����Զ��������ж��Ƿ���Ҫ����
//                    if (customIsAttach) { //�ж��Ƿ�Ϊ����¼�
//                        if (isDrug) {
                {
                    val center = mRootMeasuredWidth / 2.toFloat()
                    //�Զ�����
                    var isLeft: String
                    if (mLastRawX <= center) { //��������
                        this.animate()
                                .setInterpolator(BounceInterpolator())
                                .setDuration(100)
                                .x(0f)
                                .start()
                        this.setBackgroundResource(R.mipmap.floating_left)
                        isLeft = "1"
                    } else { //��������
                        this.animate()
                                .setInterpolator(BounceInterpolator())
                                .setDuration(100)
                                .x(mRootMeasuredWidth - width.toFloat())
                                .start()
                        this.setBackgroundResource(R.mipmap.floating_right)
                        isLeft = "0"
                    }
//                    if (customIsAttach) { //�ж��Ƿ�Ϊ����¼�
                    if (isDrug) {
                        PreferenceUtils.getInstance(context).setString(FLOATBUTTON_POSITION_KEY, this.y.toString())
                        PreferenceUtils.getInstance(context).setString(FLOATBUTTON_IS_ALIGN_LEFT_KEY, isLeft)
                        KLog.e("floatButton___View", "y��ֵ:" + this.y.toString() + "------�Ƿ�Ϊ���,1Ϊ���" + isLeft)
                    }
//                }
                }
//                        }
//                    }
            }
        }
        //�Ƿ������¼�
        return if (isDrug) isDrug else super.onTouchEvent(ev)
    }

    fun setLocation(alignLeft: String, yPosition: Float) {

        val view = parent as ViewGroup
        when (alignLeft) {
            "1" -> {
                this.setBackgroundResource(R.mipmap.floating_left)
            }
            "0" -> {
                this.setBackgroundResource(R.mipmap.floating_right)
            }
            else -> {//Ĭ��ֵ
                this.setBackgroundResource(R.mipmap.floating_right)
            }
        }



        if (isFirstSetData) {
            val newX = if (alignLeft == "1") 0f else mWidth - UIUtils.dip2px(32)//ֱ������Ļ���
            val newY = yPosition
            this
                    .animate()
                    .setInterpolator(BounceInterpolator())
                    .setDuration(100)
                    .x(newX)
                    .y(newY)
                    .start()
        } else {
            x = if (alignLeft == "1") 0f else view.measuredWidth.toFloat() - width.toFloat()
            y = yPosition
            invalidate()
        }
        isFirstSetData = false

    }


}