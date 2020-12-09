package com.dpad.crmclientapp.android.widget

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.dpad.telematicsclientapp.netlibrary.R
import com.dpad.telematicsclientapp.util.utils.UIUtils

/**
 * ================================================
 * 作    者：wenbody
 * 版    本：1.0
 * 创建日期：2019/12/17 16:16
 * 描    述：
 * 修订历史：
 * ================================================
 */
class CommonButton @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {


    interface ButtonPress {
        fun click()
    }

    /**
     * 按钮文字
     */
    private lateinit var buttonText: TextView
    private lateinit var commonLeftIm: ImageView
    private lateinit var commonRightIm: ImageView

    private lateinit var buttonPress: ButtonPress

    init {
        initView()
    }

    private fun initView() {
        val view = LayoutInflater.from(context).inflate(R.layout.press_button_layout, null)
        view.setOnClickListener {
            buttonPress.click()
        }
        buttonText = view.findViewById(R.id.commonText)
        commonLeftIm = view.findViewById(R.id.commonLeftIm)
        commonRightIm = view.findViewById(R.id.commonRightIm)
        val lp = LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, UIUtils.dip2px(45))
        lp.setMargins(UIUtils.dip2px(50), 0, UIUtils.dip2px(50), 0)
        lp.gravity = Gravity.CENTER
        addView(view, lp)
    }

    fun setButtonText(text: String) {
        buttonText.text = text
    }

    fun click(buttonPress: ButtonPress) {
        this.buttonPress = buttonPress
    }

    fun setLeftVisiable(leftVisiable: Boolean) {
        commonLeftIm.visibility = if (leftVisiable) View.VISIBLE else View.INVISIBLE
    }

    fun setRightInVisiable(rightVisiable: Boolean) {
        commonRightIm.visibility = if (rightVisiable) View.VISIBLE else View.INVISIBLE
    }

    fun setRightImage(rightIm: Int) {
        commonRightIm.setImageResource(rightIm)
    }

    fun setLeftImage(lefttIm: Int) {
        commonRightIm.setImageResource(lefttIm)
    }
}