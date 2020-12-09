package com.dpad.telematicsclientapp.weiget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.daimajia.numberprogressbar.NumberProgressBar;
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
public class DownDialog extends Dialog implements View.OnClickListener {

    private NumberProgressBar numberProgressBar;
    private TextView cancelTxt;

    private Context mContext;
    private OnCloseListener listener;
    private String negativeName;

    public interface OnCloseListener {
        void onClose(Dialog dialog, boolean confirm);
    }

    public DownDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public DownDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public DownDialog(Context context, int themeResId, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.listener = listener;
    }

    protected DownDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }


    public DownDialog setNegativeButton(String name) {
        this.negativeName = name;
        return this;
    }

    public NumberProgressBar getNumberProgressBar() {
        return numberProgressBar;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_download_layout);
        setCancelable(false);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        numberProgressBar = (NumberProgressBar) findViewById(R.id.numberprogressbar);
        cancelTxt = (TextView) findViewById(R.id.down_cancel_rtv);
        cancelTxt.setOnClickListener(this);


        if (!TextUtils.isEmpty(negativeName)) {
            cancelTxt.setText(negativeName);
        }

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.down_cancel_rtv) {
            if (listener != null) {
                listener.onClose(this, false);
            }
            dismiss();
        }
    }

}
