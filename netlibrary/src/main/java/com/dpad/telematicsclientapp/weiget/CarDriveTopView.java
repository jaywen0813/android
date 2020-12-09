package com.dpad.telematicsclientapp.weiget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.LinearInterpolator;




import com.dpad.telematicsclientapp.mvp.kit.Kits;
import com.dpad.telematicsclientapp.netlibrary.R;
import com.dpad.telematicsclientapp.netlibrary.newapp.entity.BargrapHBean;
import com.dpad.telematicsclientapp.util.utils.UIUtils;

import java.util.List;



/**
 * ================================================
 * 作    者：booob
 * 版    本：1.0
 * 创建日期：2019-01-16-0016 13:30
 * 描    述：环保驾驶顶部展示里程的view
 * 修订历史：
 * ================================================
 */
public class CarDriveTopView extends View {

    float width = 0;
    float height = 0;
    private Paint topLinePaint;//顶部实线
    private Paint textPaint;//文字的画笔
    private Paint leftTextPaint;//文字的画笔
    Path topLinePathCorner;//顶部的圆圈
    private Paint topLineCornerPaint;//顶部圆圈

    Path topLinePath;//顶部的横线

    private String titleText = "周里程(km)";//可能是月里程
    private List<BargrapHBean.Data> integerList;
    private int currentItem;


    private int textSize14 = UIUtils.dip2px(14);
    private int textSize20 = UIUtils.dip2px(20);

    private ValueAnimator animator;
    private float progress = 0;

    private float viewMargin = UIUtils.dip2px(20); //整个view的外边距
    private float topMargin = UIUtils.dip2px(80);

    private float itemWidth = 0;
    private float itemHeight = UIUtils.dip2px(240);

    private float startX;
    private float startY;

    public CarDriveTopView(Context context) {
        super(context);
    }

    public CarDriveTopView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        Resources resources = this.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        float density1 = dm.density;
        width = dm.widthPixels;
        height = 2 * width / 5;//(图片的宽高比 1500/656)
        itemHeight = height + topMargin;
        startY = topMargin;
        init();

    }

    private void init() {
        //        居中文字
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.parseColor("#ffffffff"));
        textPaint.setTextSize(textSize14);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextAlign(Paint.Align.LEFT);

        //实线
        topLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        topLinePaint.setAntiAlias(true);
        topLinePaint.setColor(Color.parseColor("#1758B6"));
        topLinePaint.setStyle(Paint.Style.STROKE);
        topLinePaint.setStrokeWidth(UIUtils.dip2px(1));

        //   里程文字
        leftTextPaint = new Paint();
        leftTextPaint.setAntiAlias(true);
        leftTextPaint.setColor(Color.parseColor("#ffffffff"));
        leftTextPaint.setTextSize(textSize20);
        leftTextPaint.setStyle(Paint.Style.FILL);
        leftTextPaint.setTextAlign(Paint.Align.LEFT);

        //圆圈
        topLineCornerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        topLineCornerPaint.setAntiAlias(true);
        topLineCornerPaint.setColor(Color.parseColor("#1758B6"));
        topLineCornerPaint.setStyle(Paint.Style.FILL);
        topLineCornerPaint.setStrokeWidth(UIUtils.dip2px(3));

        topLinePath = new Path();
        topLinePathCorner = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        @SuppressLint("DrawAllocation")
        Bitmap bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.car_drive_top_bg);
        bmp.setDensity(UIUtils.getResources().getDisplayMetrics().densityDpi);
        @SuppressLint("DrawAllocation") RectF rectF = new RectF(0, startY, width, height + topMargin);
        canvas.drawBitmap(bmp, null, rectF, null);
        canvas.drawPath(topLinePathCorner, topLineCornerPaint);
        float startX1 = width * 7 / (7 + 8);//(位置的左右坐标 7:8)

        float m = 11f / (11f + 5f);
        float startY1 = itemHeight - height * 5 / (11 + 5);

        canvas.drawCircle(startX1, startY1, UIUtils.dip2px(3), topLineCornerPaint);

        if (progress <= 50) {
            topLinePath.moveTo(startX1, startY1);
            topLinePath.lineTo(startX1 + UIUtils.dip2px(30) * progress / 50, startY1 - (height * m + 3 * topMargin / 4) * progress / 50);
        } else {
            topLinePath.moveTo(startX1, startY1);
            topLinePath.lineTo(startX1 + UIUtils.dip2px(30), startY1 - (height * m + 3 * topMargin / 4));
            topLinePath.lineTo(startX1 + UIUtils.dip2px(30) + UIUtils.dip2px(100) * (progress - 50) / 50, startY1 - (height * m + 3 * topMargin / 4));
        }
        if (progress == 100) {
            canvas.drawText(titleText, startX1 + UIUtils.dip2px(30) + UIUtils.dip2px(10), startY1 - (height * m + 3 * topMargin / 4) + UIUtils.dip2px(8) + textSize14, textPaint);

            canvas.drawText(integerList.get(currentItem).getMile() + "", startX1 + UIUtils.dip2px(30) + UIUtils.dip2px(10), startY1 - (height * m + 3 * topMargin / 4) + UIUtils.dip2px(8) + 2*textSize14  + (float) textSize20 / 2, leftTextPaint);
        }
        canvas.drawPath(topLinePath, topLinePaint);

        topLinePath.reset();
    }

//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
//        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
//
//        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
//        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
//
//        if (widthMode == MeasureSpec.EXACTLY) {//精确模式
//            itemWidth = widthSize - (2 * viewMargin); //减去外边距
//            startX = viewMargin; //起始位置定位到默认的
//            startY = topMargin;
//        } else if (widthMode == MeasureSpec.AT_MOST) {//matchparent
//            itemWidth = widthSize - (2 * viewMargin); //减去外边距
//            startX = viewMargin; //起始位置定位到默认的
//            startY = topMargin;
//        } else {//自适应
//            itemWidth = widthSize - (2 * viewMargin); //减去外边距
//            startX = viewMargin; //起始位置定位到默认的
//            startY = topMargin;
//        }
//
//        if (heightMode == MeasureSpec.EXACTLY) {
//            itemHeight = heightSize - (topMargin + viewMargin) - UIUtils.dip2px(25);//100是文字的高度
//            startY = topMargin;
//        } else if (widthMode == MeasureSpec.AT_MOST) {//matchparent
//            itemHeight = heightSize - (topMargin + viewMargin) - UIUtils.dip2px(25);//100是文字的高度
//            startY = topMargin;
//        } else {//自适应
//            itemHeight = heightSize - (topMargin + viewMargin) - UIUtils.dip2px(25);//100是文字的高度
//            startY = topMargin;
//        }
//        setMeasuredDimension(widthSize, heightSize);
//    }

    public void setData(BargrapHBean bargrapHBean, boolean isMouth) {
        this.currentItem = bargrapHBean.getCurrentItem();
        this.titleText = isMouth ? "月里程(km)" : "周里程(km)";
        this.integerList = bargrapHBean.getDataList();
        startAnim(bargrapHBean);
    }

    public void startAnim(BargrapHBean bargrapHBean) {
        if (Kits.Empty.check(bargrapHBean)) {
            return;
        }

        animator = ObjectAnimator.ofFloat(0, 100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                progress = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.setStartDelay(300);   //设置延迟开始
        animator.setDuration(1800);
        animator.setInterpolator(new LinearInterpolator());   //动画匀速
        animator.start();
    }
}
