package com.dpad.telematicsclientapp.weiget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.dpad.telematicsclientapp.netlibrary.R;


/**
 * @创建者 booobdai.
 * @创建时间 2017/8/30  14:15.
 * @描述 ${基本的 确定/取消 Dialog}.
 */
public class BasicDialog extends Dialog implements View.OnClickListener {
    private TextView contentTxt;
    private TextView titleTxt;
    private TextView submitTxt;
    private TextView cancelTxt;
    private ImageView ivClose;
    private ImageView topImageView;
    private RelativeLayout topRl;

    private boolean topImageVisible = false;//顶部的image是否可见
    private int resourcesId;

    private Context mContext;
    private String mContent;
    private boolean isLeft;
    private OnCloseListener listener;
    private String positiveName;
    private String negativeName;
    private String title;
    private boolean cancelVisible = true;
    private View middleView;


    public interface OnCloseListener {
        void onClose(Dialog dialog, boolean confirm);
    }

    public BasicDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public BasicDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public BasicDialog(Context context, int themeResId, OnCloseListener listener) {
        super(context, themeResId);
        this.mContext = context;
        this.listener = listener;
    }

    protected BasicDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    public BasicDialog setTitle(String title) {
        this.title = title;
        return this;
    }

    public BasicDialog setPositiveButton(String name) {
        this.positiveName = name;
        return this;
    }

    public BasicDialog setNegativeButton(String name) {
        this.negativeName = name;
        return this;
    }

    public BasicDialog setContent(String content) {
        this.mContent = content;
        return this;
    }

    public BasicDialog setGravity(boolean isLeft) {
        this.isLeft = isLeft;
        return this;
    }

    public BasicDialog setTopImageResources(int resourcesId) {
        this.topImageVisible = true;
        this.resourcesId = resourcesId;
        return this;
    }

    public BasicDialog setCancelVisible(boolean visible) {
        this.cancelVisible = visible;
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_basic);
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        contentTxt = (TextView) findViewById(R.id.tv_content_dialog_basic);
        titleTxt = (TextView) findViewById(R.id.tv_title_dialog_basic);
        submitTxt = (TextView) findViewById(R.id.tv_confirm_dialog);
        cancelTxt = (TextView) findViewById(R.id.tv_cancel_dialog);
        ivClose = (ImageView) findViewById(R.id.iv_dialog_basic_close);
        topRl = findViewById(R.id.top_rl);
        topImageView = findViewById(R.id.top_img);
        middleView = findViewById(R.id.base_dialog_bottom_middle_line);
        ivClose.setVisibility(View.INVISIBLE);
        submitTxt.setOnClickListener(this);
        cancelTxt.setOnClickListener(this);
        ivClose.setOnClickListener(this);

        if (!TextUtils.isEmpty(mContent)) {
            contentTxt.setText(mContent);
            if (isLeft) {
                contentTxt.setGravity(Gravity.LEFT | Gravity.CENTER);
            } else {
                contentTxt.setGravity(Gravity.CENTER);
            }
        }
        middleView.setVisibility(cancelVisible ? View.VISIBLE : View.GONE);
        cancelTxt.setVisibility(cancelVisible ? View.VISIBLE : View.GONE);
        if (topImageVisible) {
            topRl.setVisibility(View.VISIBLE);
            topImageView.setImageResource(resourcesId);
        } else {
            topRl.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(positiveName)) {
            submitTxt.setText(positiveName);
        }
        if (!TextUtils.isEmpty(negativeName)) {
            cancelTxt.setText(negativeName);
        }
        if (!TextUtils.isEmpty(title)) {
            titleTxt.setText(title);
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.tv_cancel_dialog) {
            if (listener != null) {
                listener.onClose(this, false);
            }
            dismiss();
        } else if (id == R.id.tv_confirm_dialog) {
            if (listener != null) {
                listener.onClose(this, true);
            }
        } else if (id == R.id.iv_dialog_basic_close) {
            if (listener != null) {
                listener.onClose(this, false);
            }
            dismiss();
        }
    }

}
