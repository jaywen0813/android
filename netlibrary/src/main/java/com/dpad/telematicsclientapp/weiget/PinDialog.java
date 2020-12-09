package com.dpad.telematicsclientapp.weiget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;


import com.dpad.telematicsclientapp.netlibrary.R;


/**
 * ================================================
 * 作    者：booob
 * 版    本：1.0
 * 创建日期：2019-05-23-0023 下午 04:25
 * 描    述：下载的进度条
 * 修订历史：
 * ================================================
 */
public class PinDialog extends Dialog implements View.OnClickListener {

    private ImageView pinDialogClose;
    private PinInputView pinInputView;

    private Context mContext;
    private OnCloseListener listener;

    public interface OnCloseListener {
        void onClose(Dialog dialog, boolean confirm);
    }

    public PinDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public PinDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public PinDialog(Context context, int themeResId, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.listener = listener;
    }

    protected PinDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }


    public PinInputView getPinInputView() {
        return pinInputView;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_pin_input);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        pinDialogClose = findViewById(R.id.pin_close_image);
        pinInputView = findViewById(R.id.pin_view);
        pinDialogClose.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.pin_close_image) {
            if (listener != null) {
                listener.onClose(this, false);
            }
            dismiss();
        }
    }

}
