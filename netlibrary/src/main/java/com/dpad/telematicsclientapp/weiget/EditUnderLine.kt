package com.dpad.crmclientapp.android.widget

import android.content.Context
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.dpad.telematicsclientapp.netlibrary.R

/**
 * ================================================
 * 作    者：wenbody
 * 版    本：1.0
 * 创建日期：2019/12/24 10:11
 * 描    述：
 * 修订历史：
 * ================================================
 */
class EditUnderLine @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr) {

    private lateinit var errorDesLl: LinearLayout
    private lateinit var errorMsgTv: TextView
    private lateinit var underLineView: View

    init {
        initView()
    }

    private fun initView() {
        val view = LayoutInflater.from(context).inflate(R.layout.view_under_line, null)
        errorDesLl = view.findViewById(R.id.errorDesLl)
        errorMsgTv = view.findViewById(R.id.errorMsgTv)
        underLineView = view.findViewById(R.id.underLineView)
        addView(view)
    }

    /**
     *设置错误信息
     */
    fun setErrorMsg(msg: String) {
        if (!TextUtils.isEmpty(msg)) {
            errorMsgTv.text = msg
        } else {
            errorMsgTv.text = "手机号格式输入不正确"
        }
        errorDesLl.visibility = VISIBLE
    }

    fun setErrorMsgInvisible() {
        errorDesLl.visibility = GONE
    }

    /**
     * 设置背景颜色
     */
    fun setUnderLineColor(color: Int) {
        try {
            underLineView.setBackgroundColor(resources.getColor(color))
        } catch (e: Exception) {
            underLineView.setBackgroundResource(color)
        }
    }


}