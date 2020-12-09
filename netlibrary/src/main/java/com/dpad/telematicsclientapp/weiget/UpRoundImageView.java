package com.dpad.telematicsclientapp.weiget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.widget.ImageView;


import com.dpad.telematicsclientapp.util.utils.UIUtils;

/**
 * ================================================
 * 作    者：booob
 * 版    本：1.0
 * 创建日期：2018-06-13-0013 17:50
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class UpRoundImageView extends AppCompatImageView {

    private float mRadus = UIUtils.dip2px(5);
//    private float mRadus = 30;

    /*圆角的半径，依次为左上角xy半径，右上角，右下角，左下角*/
    private float[] rids = {mRadus, mRadus, mRadus, mRadus, 0.0f, 0.0f, 0.0f, 0.0f};

    public UpRoundImageView(Context context) {
        super(context);
    }

    public UpRoundImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public UpRoundImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    /**
     * 画图
     *
     * @param canvas
     */
    protected void onDraw(Canvas canvas) {
        Path path = new Path();
        int w = this.getWidth();
        int h = this.getHeight();
        /*向路径中添加圆角矩形。radii数组定义圆角矩形的四个圆角的x,y半径。radii长度必须为8*/
        path.addRoundRect(new RectF(0, 0, w, h), rids, Path.Direction.CW);
        canvas.clipPath(path);
        super.onDraw(canvas);
    }


}
