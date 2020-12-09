package com.dpad.telematicsclientapp.weiget;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;


import com.dpad.telematicsclientapp.netlibrary.R;

/**
 * ================================================
 * 作    者：booob
 * 版    本：1.0
 * 创建日期：2019-03-14-0014 09:43
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ScaleImageView extends AppCompatImageView {
    private Context context;

    public ScaleImageView(Context context) {
        super(context);
        this.context = context;
    }

    public ScaleImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public ScaleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        switch (event.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                beginScale(R.anim.zoom_in);
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                beginScale(R.anim.zoom_out);
                break;
            case MotionEvent.ACTION_CANCEL:
                beginScale(R.anim.zoom_out);
                break;
        }
        return true;
    }

    private synchronized void beginScale(int animation) {
        Animation an = AnimationUtils.loadAnimation(context, animation);
        an.setDuration(80);
        an.setFillAfter(true);//此方法会导致setVisibility 方法失效,调用隐藏的方法之前需要清除动画
        this.startAnimation(an);
    }

}
