package com.dpad.telematicsclientapp.weiget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;


import com.dpad.telematicsclientapp.netlibrary.R;


/**
 * @创建者 booobdai.
 * @创建时间 2017/8/30  14:32.
 * @描述 ${通用的加载中 Loading Dialog}.
 */
public class LoadingDialog extends Dialog {

    private String mText;
    private Context mContext;

    public LoadingDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public LoadingDialog(Context context, int themeResId, String text) {
        super(context, themeResId);
        this.mContext = context;
        this.mText = text;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
        initView();
    }

    private void initView() {
        TextView tv = (TextView) findViewById(R.id.tv_loading_text);
        if (!TextUtils.isEmpty(mText)) {
            tv.setText(mText);
        } else {
            tv.setVisibility(View.GONE);
        }
    }
}
