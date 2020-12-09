package com.dpad.telematicsclientapp.weiget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.dpad.crmclientapp.android.R;
import com.dpad.crmclientapp.android.modules.internet_of_vehicles.bean.AverageOilBean;
import com.dpad.crmclientapp.android.util.utils.T;
import com.dpad.crmclientapp.android.util.utils.UIUtils;

import java.security.PublicKey;

import cn.droidlover.xdroidmvp.kit.Kits;


/**
 * ================================================
 * 作    者：booob
 * 版    本：1.0
 * 创建日期：2018-10-30-0030 09:40
 * 描    述：环保驾驶用户占比图例
 * 修订历史：
 * ================================================
 */
public class ProgressView extends View {

    Paint paint;
    Paint circlePaint;
    Paint maxPaint;


    private float imageWidth;
    private float imageHeight;
    private ValueAnimator animator;
    private float progress = 0;
    private float progress2 = 0;

    private float startProgress;//进度
    private boolean isZero = false;
    private boolean is360 = false;
    private String percentage;//百分比
    private String oilNum;//油耗
    private int space1 = UIUtils.dip2px(5);
    private int space2 = UIUtils.dip2px(14);
    private int space3 = UIUtils.dip2px(20);
    private int space4 = UIUtils.dip2px(50);
    private int topLineLen = UIUtils.dip2px(100);


    private int width1 = UIUtils.dip2px(1);
    private int width2 = UIUtils.dip2px(3);
    private int corner1 = 5;
    private int corner2 = 15;
    private int corner3 = 50;//描述的角度

    private float startX;
    private float startY;

    private int textSize14 = UIUtils.dip2px(13);
    private int textSize11 = UIUtils.dip2px(11);
    private int textSize18 = UIUtils.dip2px(15);
    private int textSize24 = UIUtils.dip2px(20);

    private float viewMargin = UIUtils.dip2px(40); //整个view的外边距
    private float topMargin = UIUtils.dip2px(10);

    private Path maxPath;//内部小圆 写反了
    private Path littlePath;//外部大圆

    private Path cornerPath;//说明的圆圈
    private Path desPath;//说明的折线

    private Paint textPaint;//普通文字
    private Paint textLittlePaint;//小文字
    private Paint textBluePaint;//蓝色文字
    private Paint textMaxPaint;//大文字

    Bitmap bmp;

    private int viewHeight = UIUtils.dip2px(190);

    public ProgressView(Context context) {
        super(context);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        bmp = BitmapFactory.decodeResource(getResources(), R.mipmap.oil_avg);
        float f = (float) bmp.getHeight() / (viewHeight - 2 * topMargin);
        Matrix matrix = new Matrix();
        matrix.postScale(f, f);
        //获取新的bitmap
        bmp = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(), bmp.getHeight(), matrix, true);

        imageHeight = bmp.getHeight();
        imageWidth = bmp.getWidth();

        paint = new Paint();
        paint.setColor(Color.parseColor("#1758B6"));
        paint.setStyle(Paint.Style.STROKE);
        paint.setAntiAlias(true);
        paint.setStrokeWidth(width1);

        circlePaint = new Paint();
        circlePaint.setColor(Color.parseColor("#1758B6"));
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setAntiAlias(true);
        circlePaint.setStrokeWidth(width1);

        maxPaint = new Paint();
        maxPaint.setColor(Color.parseColor("#1758B6"));
        maxPaint.setStyle(Paint.Style.STROKE);
        maxPaint.setAntiAlias(true);
        maxPaint.setStrokeWidth(width2);


        maxPath = new Path();
        littlePath = new Path();
        cornerPath = new Path();
        desPath = new Path();


        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.parseColor("#ffffffff"));
        textPaint.setTextSize(textSize14);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextAlign(Paint.Align.LEFT);

        textLittlePaint = new Paint();
        textLittlePaint.setAntiAlias(true);
        textLittlePaint.setColor(Color.parseColor("#ffffffff"));
        textLittlePaint.setTextSize(textSize11);
        textLittlePaint.setStyle(Paint.Style.FILL);
        textLittlePaint.setTextAlign(Paint.Align.LEFT);

        textMaxPaint = new Paint();
        textMaxPaint.setAntiAlias(true);
        textMaxPaint.setColor(Color.parseColor("#ffffffff"));
        textMaxPaint.setTextSize(textSize18);
        textMaxPaint.setStyle(Paint.Style.FILL);
        textMaxPaint.setTextAlign(Paint.Align.LEFT);

        textBluePaint = new Paint();
        textBluePaint.setAntiAlias(true);
        textBluePaint.setColor(Color.parseColor("#177BDE"));
        textBluePaint.setTextSize(textSize24);
        textBluePaint.setStyle(Paint.Style.FILL);
        textBluePaint.setTextAlign(Paint.Align.LEFT);
    }

    public ProgressView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        requestLayout();
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {//精确模式
            startX = viewMargin; //起始位置定位到默认的
        } else if (widthMode == MeasureSpec.AT_MOST) {//matchparent
            startX = viewMargin; //起始位置定位到默认的
        } else {//自适应
            startX = viewMargin; //起始位置定位到默认的
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            startY = topMargin +UIUtils.dip2px(20);
//            startY = topMargin + (heightSize - imageHeight) / 2 - UIUtils.dip2px(10);
        } else if (widthMode == MeasureSpec.AT_MOST) {//matchparent
            startY = topMargin +UIUtils.dip2px(20);
        } else {//自适应
            startY = topMargin +UIUtils.dip2px(20);
        }
        setMeasuredDimension(widthSize, viewHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();

//        canvas.scale(1.1f, 1.1f);

        float R = imageWidth / 2;
        float r = 56 * R / 137;
        float x0 = (float) (R + startX + Math.sin(corner1 * Math.PI / 180) * r);
        float y0 = (float) (R - r + startY + (1 - Math.cos(corner1 * Math.PI / 180)) * r);

        float x_corner = (float) (R + Math.sin(corner3 * Math.PI / 180) * (R + space3)) + startX;
        float y_corner = (float) (R - Math.cos(corner3 * Math.PI / 180) * (R + space3)) + startY;

        float x_corner1 = (float) (R + Math.sin(corner3 * Math.PI / 180) * (R + space3 + space4)) + startX;
        float y_corner1 = (float) (R - Math.cos(corner3 * Math.PI / 180) * (R + space3 + space4)) + startY;
        cornerPath.addCircle(x_corner, y_corner, UIUtils.dip2px(3), Path.Direction.CCW);
        desPath.moveTo(x_corner, y_corner);
        if (!isZero) {
            maxPath.moveTo(x0, y0);
        }


        float x1;
        float y1;

        float x2 = (float) (R + Math.sin(corner2 * Math.PI / 180) * r) + startX;
        float y2 = (float) (R - Math.cos(corner2 * Math.PI / 180) * r) + startY;

        float x3;
        float y3;

        float m1;
        float n1;
        float m2;
        float n3;
        littlePath.moveTo(x2, y2);
        float f = R + (space1 + width2 / 2) - r;
        float f1 = R + (space2 + width2 / 2) - r;
//        if (!isZero) {
        if (progress <= startProgress / 4) {
            if (!isZero) {
                x1 = (float) (R + Math.sin(corner1 * Math.PI / 180) * (r + (R + space1 + width1 / 2 - r) * 4 * progress / startProgress) + startX);
                y1 = (float) (R - Math.cos(corner1 * Math.PI / 180) * (r + (R + space1 + width1 / 2 - r) * 4 * progress / startProgress) + startY);

                maxPath.lineTo(x1, y1);
            }
            if (!is360) {
                x3 = (float) (R + Math.sin(corner2 * Math.PI / 180) * (r + (R + space2 + width2 / 2 - r) * 4 * progress / startProgress) + startX);
                y3 = (float) (R - Math.cos(corner2 * Math.PI / 180) * (r + (R + space2 + width2 / 2 - r) * 4 * progress / startProgress) + startY);
                littlePath.lineTo(x3, y3);
            }

            m1 = (float) (x_corner + Math.sin(corner3 * Math.PI / 180) * ((space4) * 4 * progress / startProgress));
            n1 = (float) (y_corner - Math.cos(corner3 * Math.PI / 180) * ((space4) * 4 * progress / startProgress));
            desPath.lineTo(m1, n1);
        } else {
            desPath.lineTo(x_corner1, y_corner1);
            m2 = x_corner1 + topLineLen * 4 * progress / (4 * startProgress);
            desPath.lineTo(m2, y_corner1);

            if (progress <= 3 * startProgress / 4) {
                if (!isZero) {
                    x1 = (float) (R + Math.sin(corner1 * Math.PI / 180) * (R + (space1 + width2 / 2))) + startX;
                    y1 = (float) (R - Math.cos(corner1 * Math.PI / 180) * (R + (space1 + width2 / 2))) + startY;
                    maxPath.lineTo(x1, y1);
                }//
                if (!is360) {
                    x3 = (float) (R + Math.sin(corner2 * Math.PI / 180) * (R + (space2 + width2 / 2))) + startX;
                    y3 = (float) (R - Math.cos(corner2 * Math.PI / 180) * (R + (space2 + width2 / 2))) + startY;
                    littlePath.lineTo(x3, y3);
                }
            } else {
                if (!isZero) {
                    x1 = (float) (R + Math.sin(corner1 * Math.PI / 180) * (R + (space1 + width2 / 2))) + startX;
                    y1 = (float) (R - Math.cos(corner1 * Math.PI / 180) * (R + (space1 + width2 / 2))) + startY;


                    float x4 = (float) (R + Math.sin((corner1 + startProgress) * Math.PI / 180) * (R + (space1 + width2 / 2))) + startX;
                    float y4 = (float) (R - Math.cos((corner1 + startProgress) * Math.PI / 180) * (R + (space1 + width2 / 2))) + startY;
                    float x5 = (float) (R + Math.sin((corner1 + startProgress) * Math.PI / 180) * ((R + (space1 + width2 / 2)) - 4 * f * (progress - 3 * startProgress / 4) / startProgress)) + startX;
                    float y5 = (float) (R - Math.cos((corner1 + startProgress) * Math.PI / 180) * ((R + (space1 + width2 / 2)) - 4 * f * (progress - 3 * startProgress / 4) / startProgress)) + startY;

                    maxPath.lineTo(x1, y1);
                    maxPath.moveTo(x4, y4);
                    maxPath.lineTo(x5, y5);
                }
                if (!is360) {
                    x3 = (float) (R + Math.sin(corner2 * Math.PI / 180) * (R + (space2 + width2 / 2))) + startX;
                    y3 = (float) (R - Math.cos(corner2 * Math.PI / 180) * (R + (space2 + width2 / 2))) + startY;
                    littlePath.lineTo(x3, y3);


                    float x6 = (float) (R + Math.sin((corner2 + 360 - startProgress) * Math.PI / 180) * (R + (space2 + width2 / 2))) + startX;
                    float y6 = (float) (R - Math.cos((corner2 + 360 - startProgress) * Math.PI / 180) * (R + (space2 + width2 / 2))) + startY;

                    float x7 = (float) (R + Math.sin((corner2 + 360 - startProgress) * Math.PI / 180) * ((R + (space2 + width2 / 2)) - 4 * f1 * (progress - 3 * startProgress / 4) / startProgress)) + startX;
                    float y7 = (float) (R - Math.cos((corner2 + 360 - startProgress) * Math.PI / 180) * ((R + (space2 + width2 / 2)) - 4 * f1 * (progress - 3 * startProgress / 4) / startProgress)) + startY;
                    littlePath.moveTo(x6, y6);
                    littlePath.lineTo(x7, y7);
                }
            }
            if (progress == startProgress) {
                if (Kits.Empty.check(percentage) || Kits.Empty.check(oilNum)) {
                    T.showToastSafe("油耗或百分比不能为空");
                    return;
                }
                @SuppressLint("DrawAllocation") Rect bounds = new Rect();
                @SuppressLint("DrawAllocation") Rect bounds1 = new Rect();
                canvas.drawText("击败", x_corner1 + UIUtils.dip2px(5), y_corner1 + UIUtils.dip2px(5) + UIUtils.dip2px(10) + textSize24 / 2, textPaint);
                textPaint.getTextBounds("击败", 0, 2, bounds1);
                int y = bounds1.width();

                canvas.drawText(percentage, x_corner1 + UIUtils.dip2px(5) + y + 2, y_corner1 + UIUtils.dip2px(5) + UIUtils.dip2px(10) + textSize24 * 2 / 3, textBluePaint);
                textBluePaint.getTextBounds(percentage, 0, percentage.length(), bounds);
                int x = bounds.width();
                canvas.drawText("的用户", x_corner1 + UIUtils.dip2px(5) + x + y + UIUtils.dip2px(4), y_corner1 + UIUtils.dip2px(5) + UIUtils.dip2px(10) + textSize24 / 2, textPaint);

                canvas.drawText("平均油耗(L/百公里)", x_corner1 + UIUtils.dip2px(5), y_corner1 + UIUtils.dip2px(7) + UIUtils.dip2px(10) + textSize24 / 2 + (textSize11 + textSize24) / 2, textLittlePaint);

                canvas.drawText(oilNum, x_corner1 + UIUtils.dip2px(5), y_corner1 + UIUtils.dip2px(7) * 2 + UIUtils.dip2px(10) + textSize24 + textSize11 + textSize14 / 2, textMaxPaint);
            }
        }


//        canvas.drawArc(startX - space1, startY - space1, startX + space1 + imageWidth, startY + space1 + imageHeight, -(90 - corner1), progress, false, maxPaint);
//        canvas.drawArc(startX - space2, startY - space2, startX + space2 + imageWidth, startY + space2 + imageHeight, -(90 - corner2), progress2, false, maxPaint);

        @SuppressLint("DrawAllocation") RectF rectF1 = new RectF(startX - space1, startY - space1, startX + space1 + imageWidth, startY + space1 + imageHeight);
        @SuppressLint("DrawAllocation") RectF rectF2 = new RectF(startX - space2, startY - space2, startX + space2 + imageWidth, startY + space2 + imageHeight);
        if (!isZero) {
            canvas.drawArc(rectF1, -(90 - corner1), progress, false, maxPaint);
        }
        if (!is360) {
            canvas.drawArc(rectF2, -(90 - corner2), progress2, false, maxPaint);
        }
//        }
//        else {
//            if (progress <= 45) {
//                m1 = (float) (x_corner + Math.sin(corner3 * Math.PI / 180) * ((space4) * 4 * progress / 45));
//                n1 = (float) (y_corner - Math.cos(corner3 * Math.PI / 180) * ((space4) * 4 * progress / 45));
//                desPath.lineTo(m1, n1);
//            } else {
//                desPath.lineTo(x_corner1, y_corner1);
//                m2 = x_corner1 + topLineLen * 4 * progress / (4 * 180);
//                desPath.lineTo(m2, y_corner1);
//            }
//            if (progress == 180) {
//                @SuppressLint("DrawAllocation") Rect bounds = new Rect();
//                @SuppressLint("DrawAllocation") Rect bounds1 = new Rect();
//                canvas.drawText("击败", x_corner1 + UIUtils.dip2px(5), y_corner1 + UIUtils.dip2px(5) + UIUtils.dip2px(10) + textSize24 / 2, textPaint);
//                textPaint.getTextBounds("击败", 0, 2, bounds1);
//                int y = bounds1.width();
//
//                canvas.drawText(percentage, x_corner1 + UIUtils.dip2px(5) + y + 2, y_corner1 + UIUtils.dip2px(5) + UIUtils.dip2px(10) + textSize24 * 2 / 3, textBluePaint);
//                textBluePaint.getTextBounds(percentage, 0, percentage.length(), bounds);
//                int x = bounds.width();
//                canvas.drawText("的用户", x_corner1 + UIUtils.dip2px(5) + x + y + UIUtils.dip2px(4), y_corner1 + UIUtils.dip2px(5) + UIUtils.dip2px(10) + textSize24 / 2, textPaint);
//
//                canvas.drawText("平均油耗(L/百公里)", x_corner1 + UIUtils.dip2px(5), y_corner1 + UIUtils.dip2px(7) + UIUtils.dip2px(10) + textSize24 / 2 + (textSize11 + textSize24) / 2, textLittlePaint);
//
//                canvas.drawText(oilNum, x_corner1 + UIUtils.dip2px(5), y_corner1 + UIUtils.dip2px(7) * 2 + UIUtils.dip2px(10) + textSize24 + textSize11 + textSize14 / 2, textMaxPaint);
//
//            }
//        }
        if (startProgress != 0 && !isZero) {//-1说明没有数据
            canvas.drawPath(maxPath, paint);
        }
//        if (startProgress != 360 && isZero) {
        canvas.drawPath(littlePath, paint);
//        }

        canvas.drawBitmap(bmp, startX, startY, paint);

        canvas.drawPath(cornerPath, circlePaint);
        canvas.drawPath(desPath, paint);

        maxPath.reset();
        littlePath.reset();
        desPath.reset();
        cornerPath.reset();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        postInvalidate();
    }


    public void startAnim(AverageOilBean averageOilBean) {
        if (Kits.Empty.check(averageOilBean)) {
            return;
        }
        this.startProgress = averageOilBean.getProgress();
        this.percentage = averageOilBean.getPercentage();
        this.oilNum = averageOilBean.getOilNum();
        if (startProgress != -1) {
            is360 = startProgress == 360;
            isZero = false;
            animator = ObjectAnimator.ofFloat(0, startProgress);
        } else {
            is360 = false;
            isZero = true;
            startProgress = 0.1f;
            animator = ObjectAnimator.ofFloat(0, startProgress);
        }
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                ProgressView.this.progress = (float) animation.getAnimatedValue();
                ProgressView.this.progress2 = (360 - startProgress) / startProgress * progress;
                postInvalidate();
            }
        });
        animator.setStartDelay(300);   //设置延迟开始
        animator.setDuration(1800);
        animator.setInterpolator(new LinearInterpolator());   //动画匀速
        animator.start();
    }
}
