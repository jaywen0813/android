package com.dpad.telematicsclientapp.weiget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Checkable;


import com.dpad.telematicsclientapp.netlibrary.R;


/**
 * Created by yongchao.bei on 2017/5/17.
 * 带有选中状态的Imageview,并且可以选择是否消费事件
 */

public class CheckImageview extends android.support.v7.widget.AppCompatImageView implements Checkable {
    private boolean mChecked;
    private boolean mIsconsume;
    private static final int[] CHECKED = {R.attr.checked};

    public CheckImageview(Context context) {
        super(context);
    }

    public CheckImageview(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
    }

    public CheckImageview(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray typeArray = context.obtainStyledAttributes(attrs, R.styleable.CheckImageview);
        mChecked = typeArray.getBoolean(R.styleable.CheckImageview_checked, false);
        mIsconsume = typeArray.getBoolean(R.styleable.CheckImageview_isconsume, false);
    }

    //设置选中状态，并且根据状态刷新背景图
    @Override
    public void setChecked(boolean checked) {
        if (mChecked != checked) {
            mChecked = checked;
            refreshDrawableState();
        }
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
    }

    @Override
    public boolean isChecked() {
        return mChecked;
    }

    @Override
    public void toggle() {
        setChecked(!mChecked);
    }



    //把自定义状态加入view的状态库中,以便selector可以找到
    @Override
    public int[] onCreateDrawableState(int extraSpace) {
        int[] stateSets = super.onCreateDrawableState(extraSpace + 1);
        if (mChecked) {
            mergeDrawableStates(stateSets, CHECKED);
        }
        return stateSets;
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
    }


}
