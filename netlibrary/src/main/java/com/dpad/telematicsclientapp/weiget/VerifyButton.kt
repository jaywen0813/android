package com.dpad.crmclientapp.android.widget

import android.content.Context
import android.os.Handler
import android.util.AttributeSet
import android.widget.TextView

import com.dpad.telematicsclientapp.netlibrary.R

/*
    获取验证码按钮
    带倒计时
 */
class VerifyButton(mContext: Context, attrs: AttributeSet) : TextView(mContext, attrs) {
    private val mHandler: Handler
    private var mCount = 60
    private var mOnVerifyBtnClick: OnVerifyBtnClick? = null

    init {
        this.text = "发送验证码"
        this.setTextColor(resources.getColor(R.color.colorPrimary))
        mHandler = Handler()
    }

    /*
        倒计时，并处理点击事件
     */
    fun requestSendVerifyNumber() {
        mHandler.postDelayed(countDown, 0)
        if (mOnVerifyBtnClick != null) {
            mOnVerifyBtnClick!!.onClick()
        }

    }

    /*
        倒计时
     */
    private val countDown = object : Runnable {
        override fun run() {
            this@VerifyButton.text = "重发("+mCount.toString() + ")s "
            this@VerifyButton.setBackgroundColor(resources.getColor(R.color.transparent))
            this@VerifyButton.setTextColor(resources.getColor(R.color.colorPrimary))
            this@VerifyButton.isEnabled = false

            if (mCount > 0) {
                mHandler.postDelayed(this, 1000)
            } else {
                resetCounter()
            }
            mCount--
        }
    }

    fun removeRunable() {
        mHandler.removeCallbacks(countDown)
    }

    /*
        恢复到初始状态
     */
    fun resetCounter(vararg text: String) {
        this.isEnabled = true
        if (text.isNotEmpty() && "" != text[0]) {
            this.text = text[0]
        } else {
            this.text = "发送验证码"
        }
        this.setBackgroundColor(resources.getColor(R.color.transparent))
        this.setTextColor(resources.getColor(R.color.colorPrimary))
        mCount = 60
    }

    /*
        点击事件接口
     */
    interface OnVerifyBtnClick {
        fun onClick()
    }

    fun setOnVerifyBtnClick(onVerifyBtnClick: OnVerifyBtnClick) {
        this.mOnVerifyBtnClick = onVerifyBtnClick
    }
}
