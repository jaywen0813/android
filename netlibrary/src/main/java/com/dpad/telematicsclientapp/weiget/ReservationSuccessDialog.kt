package com.dpad.crmclientapp.android.widget

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.dpad.telematicsclientapp.netlibrary.R
import com.flyco.roundview.RoundLinearLayout


/**
 * ================================================
 * 作    者：wenbody
 * 版    本：1.0
 * 创建日期：2019/9/11 14:44
 * 描    述：
 * 修订历史：
 * ================================================
 */
class ReservationSuccessDialog(context: Context, themeResId: Int, onCommitClick: OnCommitClick) : Dialog(context, themeResId) {

    private var commitClick: OnCommitClick = onCommitClick

    private lateinit var dialogLocationTv: TextView
    private lateinit var dialogTimeTv: TextView
    private lateinit var dialogNameTv: TextView
    private lateinit var dialogNumberTv: TextView
    private lateinit var confirmButtonTv: RoundLinearLayout
    private lateinit var dialogTitleTv: TextView
    private lateinit var dialogScaleIm: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.reservation_success_dialog)
        setCancelable(false)
        setCanceledOnTouchOutside(false)
        initView()
    }

    fun initView() {
        dialogLocationTv = findViewById(R.id.dialogLocationTv)
        dialogTimeTv = findViewById(R.id.dialogTimeTv)
        dialogNameTv = findViewById(R.id.dialogNameTv)
        dialogNumberTv = findViewById(R.id.dialogNumberTv)
        confirmButtonTv = findViewById(R.id.nextStepTv)
        dialogTitleTv = findViewById(R.id.dialogTitleTv)
        dialogScaleIm = findViewById(R.id.dialogScaleIm)
        confirmButtonTv.setOnClickListener {
            commitClick.commitClick(this@ReservationSuccessDialog)
        }
    }

    interface OnCommitClick {
        fun commitClick(dialog: ReservationSuccessDialog)
    }

    fun setTime(time: String, isUnScale: Boolean): ReservationSuccessDialog {
        dialogTimeTv.text = time
        dialogScaleIm.visibility = if (isUnScale) View.GONE else View.VISIBLE
        return this
    }

    fun setLocation(location: String): ReservationSuccessDialog {
        dialogLocationTv.text = location
        return this
    }

    fun setName(name: String): ReservationSuccessDialog {
        dialogNameTv.text = name
        return this
    }

    fun setNumber(number: String): ReservationSuccessDialog {
        dialogNumberTv.text = number
        return this
    }

    fun setTitleTv(title: String): ReservationSuccessDialog {
        dialogTitleTv.text = title
        return this
    }

}