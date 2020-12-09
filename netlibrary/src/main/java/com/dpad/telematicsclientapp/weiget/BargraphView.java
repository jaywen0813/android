package com.dpad.telematicsclientapp.weiget;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;


import com.dpad.telematicsclientapp.netlibrary.newapp.entity.BargrapHBean;
import com.dpad.telematicsclientapp.util.utils.UIUtils;

import java.util.HashMap;
import java.util.List;

/**
 * ================================================
 * 作    者：booob
 * 版    本：1.0
 * 创建日期：2018-10-31-0031 10:41
 * 描    述：应产品要求,自定义柱状图(顶部圆角,底部反圆角,并且带动画) 柱状表背景色(#2D486E),柱状表颜色(#1758B6),柱状表当前颜色(#1684F1),文字颜色(#ffffffff)
 * 修订历史：
 * ================================================
 */

public class BargraphView extends View {


    private List<BargrapHBean.Data> integerList;
    private HashMap<Integer, Float> progessMap = new HashMap<>();


    private int currentItem;
    private int topJiaoDu = 30;//顶部线的角度

    private float startX;
    private float startY;
    private float itemWidth = 0;
    private float itemHeight = UIUtils.dip2px(200);
    private float textMargin = UIUtils.dip2px(4);

    private float bottomCornerWidth = UIUtils.dip2px(5);
    private float topCornerWidth = UIUtils.dip2px(2);

    private float viewMargin = UIUtils.dip2px(20); //整个view的外边距
    private float topMargin = UIUtils.dip2px(70);
    private float itemMargin = UIUtils.dip2px(1);//view 之间的距离
    private int screenWidth;
    private float topLineWidth = UIUtils.dip2px(60);//顶部横线的长度
    private int cornerWidth = UIUtils.dip2px(2);//圆圈的大小

    private float topTextX;
    private float topTextY;

    private float circleMargin = 0;//顶部圆圈的高度


    private boolean needReDraw = false;//是否需要重新绘制,当数据条目变了之后才需要重新绘制

    private Paint textPaint;//文字的画笔
    private Paint leftTextPaint;//文字的画笔
    private Paint backgroudPaint;//背景画笔
    private Paint paint;//柱状表的画笔
    private Paint currentPaint;//当前时间的画笔
    private Paint linePaint;//虚线
    private Paint topLinePaint;//顶部实线
    private Paint topLineCornerPaint;//顶部圆圈

    private Paint desTextPaint;//详细数据

    private float f;//进度

    Path path;
    Path pathBottom;
    Path currentPathBottom;
    Path pathBg;
    Path currentPath;
    Path linePath;
    Path topLinePath;//顶部的横线
    Path topLinePathCorner;//顶部的圆圈

    private String titleText = "周里程(km)";//可能是月里程


    private int textSize14 = UIUtils.dip2px(14);
    private int textSize11 = UIUtils.dip2px(10);
    private int textSize13 = UIUtils.dip2px(12);

    private int firstLineS = 75;//第一段线动画时长所占比例,总共一百

    float radii[] = {topCornerWidth, topCornerWidth, topCornerWidth, topCornerWidth, 0, 0, 0, 0};

    public void setIntegerList(BargrapHBean bargrapHBean, boolean isMouth) {
        this.integerList = bargrapHBean.getDataList();
        this.needReDraw = bargrapHBean.isNeedReDraw();
        this.currentItem = bargrapHBean.getCurrentItem();
        this.titleText = isMouth ? "月里程(km)" : "周里程(km)";
        startAnim();
    }

    private ValueAnimator animator;

    public BargraphView(Context context) {
        super(context);
    }

    public BargraphView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        //获取不同屏幕的比例大小  这后来想想取比例没什么意义
        DisplayMetrics dm = getResources().getDisplayMetrics();
        screenWidth = dm.widthPixels;
        //初始化画笔
        //背景柱
        backgroudPaint = new Paint();
        backgroudPaint.setColor(Color.parseColor("#2D486E"));
        backgroudPaint.setAntiAlias(true);
        backgroudPaint.setStyle(Paint.Style.FILL);

        //当前柱
        currentPaint = new Paint();
        currentPaint.setColor(Color.parseColor("#1684F1"));
        currentPaint.setAntiAlias(true);
        currentPaint.setStyle(Paint.Style.FILL);

        //非当前柱
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.parseColor("#1758B6"));
        paint.setStyle(Paint.Style.FILL);

//        居中文字
        textPaint = new Paint();
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.parseColor("#ffffffff"));
        textPaint.setTextSize(textSize11);
        textPaint.setStyle(Paint.Style.FILL);
        textPaint.setTextAlign(Paint.Align.CENTER);

//        靠左文字
        leftTextPaint = new Paint();
        leftTextPaint.setAntiAlias(true);
        leftTextPaint.setColor(Color.parseColor("#ffffffff"));
        leftTextPaint.setTextSize(textSize13);
        leftTextPaint.setStyle(Paint.Style.FILL);
        leftTextPaint.setTextAlign(Paint.Align.LEFT);

        //        靠左文字
        desTextPaint = new Paint();
        desTextPaint.setAntiAlias(true);
        desTextPaint.setColor(Color.parseColor("#ffffffff"));
        desTextPaint.setTextSize(textSize14);
        desTextPaint.setStyle(Paint.Style.FILL);
        desTextPaint.setTextAlign(Paint.Align.LEFT);


        //虚线
        linePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        float m = (screenWidth - 2 * viewMargin) / 81;//虚线的长度
        linePaint.setPathEffect(new DashPathEffect(new float[]{m, m}, 0));
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.parseColor("#55ffffff"));
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(3);

        //实线
        topLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        topLinePaint.setAntiAlias(true);
        topLinePaint.setColor(Color.parseColor("#1758B6"));
        topLinePaint.setStyle(Paint.Style.STROKE);
        topLinePaint.setStrokeWidth(3);

        //圆圈
        topLineCornerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        topLineCornerPaint.setAntiAlias(true);
        topLineCornerPaint.setColor(Color.parseColor("#1758B6"));
        topLineCornerPaint.setStyle(Paint.Style.FILL);
        topLineCornerPaint.setStrokeWidth(3);


        path = new Path();
        pathBg = new Path();
        pathBottom = new Path();
        currentPathBottom = new Path();
        currentPath = new Path();
        linePath = new Path();
        topLinePath = new Path();
        topLinePathCorner = new Path();
    }

    public BargraphView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);

        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {//精确模式
            itemWidth = widthSize - (2 * viewMargin); //减去外边距
            startX = viewMargin; //起始位置定位到默认的
            startY = topMargin;
        } else if (widthMode == MeasureSpec.AT_MOST) {//matchparent
            itemWidth = widthSize - (2 * viewMargin); //减去外边距
            startX = viewMargin; //起始位置定位到默认的
            startY = topMargin;
        } else {//自适应
            itemWidth = widthSize - (2 * viewMargin); //减去外边距
            startX = viewMargin; //起始位置定位到默认的
            startY = topMargin;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            itemHeight = heightSize - (topMargin + viewMargin) - UIUtils.dip2px(25);//100是文字的高度
            startY = topMargin;
        } else if (widthMode == MeasureSpec.AT_MOST) {//matchparent
            itemHeight = heightSize - (topMargin + viewMargin) - UIUtils.dip2px(25);//100是文字的高度
            startY = topMargin;
        } else {//自适应
            itemHeight = heightSize - (topMargin + viewMargin) - UIUtils.dip2px(25);//100是文字的高度
            startY = topMargin;
        }
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (integerList != null && integerList.size() > 0) {
            itemWidth = (screenWidth - viewMargin * 2 - (integerList.size() - 1) * itemMargin + 2 * bottomCornerWidth) / integerList.size();
            circleMargin = topCornerWidth + bottomCornerWidth + textSize11 + 10;
            for (int i = 0; i < integerList.size(); i++) {
                if (i == 0) {
                    if (currentItem == i) {
                        currentPathBottom.moveTo(startX + bottomCornerWidth, startY + itemHeight);
                        currentPathBottom.quadTo(startX, startY + itemHeight, startX, startY + itemHeight - bottomCornerWidth);
                        currentPathBottom.lineTo(startX, startY + itemHeight - bottomCornerWidth - topCornerWidth);
                        currentPathBottom.lineTo(startX + itemWidth - 2 * bottomCornerWidth, startY + itemHeight - bottomCornerWidth - topCornerWidth);
                        currentPathBottom.lineTo(startX + itemWidth - 2 * bottomCornerWidth, startY + itemHeight - bottomCornerWidth);
                        currentPathBottom.quadTo(startX + itemWidth - 2 * bottomCornerWidth, startY + itemHeight, startX + itemWidth - bottomCornerWidth, startY + itemHeight);
                        currentPathBottom.lineTo(startX + bottomCornerWidth, startY + itemHeight);
                    } else {
                        pathBottom.moveTo(startX + bottomCornerWidth, startY + itemHeight);
                        pathBottom.quadTo(startX, startY + itemHeight, startX, startY + itemHeight - bottomCornerWidth);
                        pathBottom.lineTo(startX, startY + itemHeight - bottomCornerWidth - topCornerWidth);
                        pathBottom.lineTo(startX + itemWidth - 2 * bottomCornerWidth, startY + itemHeight - bottomCornerWidth - topCornerWidth);
                        pathBottom.lineTo(startX + itemWidth - 2 * bottomCornerWidth, startY + itemHeight - bottomCornerWidth);
                        pathBottom.quadTo(startX + itemWidth - 2 * bottomCornerWidth, startY + itemHeight, startX + itemWidth - bottomCornerWidth, startY + itemHeight);
                        pathBottom.lineTo(startX + bottomCornerWidth, startY + itemHeight);
                    }
                    @SuppressLint("DrawAllocation") RectF rectF1 = new RectF(startX, startY, startX + itemWidth - 2 * bottomCornerWidth, startY + itemHeight - bottomCornerWidth);
                    pathBg.addRoundRect(rectF1, topCornerWidth, topCornerWidth, Path.Direction.CCW);

                    if (progessMap != null && progessMap.get(i) != null) {
                        if (currentItem == i) {
                            @SuppressLint("DrawAllocation") RectF rectF2 = new RectF(startX, startY + itemHeight - bottomCornerWidth - topCornerWidth - progessMap.get(i), startX + itemWidth - 2 * bottomCornerWidth, startY + itemHeight - bottomCornerWidth);
                            currentPath.addRoundRect(rectF2, radii, Path.Direction.CCW);

                            float topTextHeight = ((topMargin + itemHeight - itemHeight / 100 * integerList.get(i).getData()) - 80 - topMargin * 1 / 8);

//                            float topTextWith = (float) (Math.tan(Math.PI * topJiaoDu / 180) * topTextHeight);
                            float topTextWith = itemWidth * (6 - i) + (5 - i) * itemMargin - bottomCornerWidth - itemWidth / 2;//中间往右便宜

                            topLinePathCorner.addCircle(startX + itemWidth * (i + 1) + i * itemMargin - bottomCornerWidth - itemWidth / 2, topMargin + itemHeight - (itemHeight - bottomCornerWidth - topCornerWidth) * integerList.get(i).getData() / 100 - circleMargin, cornerWidth, Path.Direction.CCW);
                            topLinePath.moveTo(startX + itemWidth * (i + 1) + i * itemMargin - bottomCornerWidth - itemWidth / 2, topMargin + itemHeight - (itemHeight - bottomCornerWidth - topCornerWidth) * integerList.get(i).getData() / 100 - circleMargin);
                            if (f <= firstLineS) {
                                topLinePath.lineTo(startX + itemWidth * (i + 1) + i * itemMargin - bottomCornerWidth - itemWidth / 2 + topTextWith / firstLineS * f, topMargin / 8 + topTextHeight / firstLineS * (firstLineS - f));
                            } else {
                                topLinePath.lineTo(startX + itemWidth * (i + 1) + i * itemMargin - bottomCornerWidth - itemWidth / 2 + topTextWith, topMargin / 8);
                                topLinePath.lineTo(startX + itemWidth * (i + 1) + i * itemMargin - bottomCornerWidth - itemWidth / 2 + topTextWith + topLineWidth / (100 - firstLineS) * (f - firstLineS), topMargin / 8);
                                if (f == 100) {
                                    topTextX = startX + itemWidth * (i + 1) + i * itemMargin - bottomCornerWidth - itemWidth / 2 + topTextWith;
                                    topTextY = topMargin / 8 + textMargin;
                                    canvas.drawText(titleText, topTextX + 15, topTextY + textMargin + textSize14 / 2, leftTextPaint);
                                    canvas.drawText(integerList.get(currentItem).getMile() + "", topTextX + 15, topTextY + textMargin * 2 + textSize14 + textSize11 / 2, desTextPaint);
                                }
                            }
                        } else {
                            @SuppressLint("DrawAllocation") RectF rectF3 = new RectF(startX, startY + itemHeight - bottomCornerWidth - topCornerWidth - progessMap.get(i), startX + itemWidth - 2 * bottomCornerWidth, startY + itemHeight - bottomCornerWidth);
                            path.addRoundRect(rectF3, radii, Path.Direction.CCW);
                        }
                    }
                } else if (i == integerList.size() - 1) {
                    if (currentItem == i) {
                        currentPathBottom.moveTo(startX + (itemWidth + itemMargin) * i - bottomCornerWidth, startY + itemHeight);
                        currentPathBottom.quadTo(startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth, startY + itemHeight, startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth, startY + itemHeight - bottomCornerWidth);
                        currentPathBottom.lineTo(startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth, startY + itemHeight - bottomCornerWidth - topCornerWidth);
                        currentPathBottom.lineTo(startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth + itemWidth - 2 * bottomCornerWidth, startY + itemHeight - bottomCornerWidth - topCornerWidth);
                        currentPathBottom.lineTo(startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth + itemWidth - 2 * bottomCornerWidth, startY + itemHeight - bottomCornerWidth);
                        currentPathBottom.quadTo(startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth + itemWidth - 2 * bottomCornerWidth, startY + itemHeight, startX + (itemWidth + itemMargin) * i - bottomCornerWidth + itemWidth - 2 * bottomCornerWidth, startY + itemHeight);
                        currentPathBottom.lineTo(startX + (itemWidth + itemMargin) * i - bottomCornerWidth, startY + itemHeight);
                    } else {
                        pathBottom.moveTo(startX + (itemWidth + itemMargin) * i - bottomCornerWidth, startY + itemHeight);
                        pathBottom.quadTo(startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth, startY + itemHeight, startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth, startY + itemHeight - bottomCornerWidth);
                        pathBottom.lineTo(startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth, startY + itemHeight - bottomCornerWidth - topCornerWidth);
                        pathBottom.lineTo(startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth + itemWidth - 2 * bottomCornerWidth, startY + itemHeight - bottomCornerWidth - topCornerWidth);
                        pathBottom.lineTo(startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth + itemWidth - 2 * bottomCornerWidth, startY + itemHeight - bottomCornerWidth);
                        pathBottom.quadTo(startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth + itemWidth - 2 * bottomCornerWidth, startY + itemHeight, startX + (itemWidth + itemMargin) * i - bottomCornerWidth + itemWidth - 2 * bottomCornerWidth, startY + itemHeight);
                        pathBottom.lineTo(startX + (itemWidth + itemMargin) * i - bottomCornerWidth, startY + itemHeight);
                    }
                    @SuppressLint("DrawAllocation") RectF rectF4 = new RectF(startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth, startY, startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth + itemWidth - 2 * bottomCornerWidth, startY + itemHeight - bottomCornerWidth);
                    pathBg.addRoundRect(rectF4, topCornerWidth, topCornerWidth, Path.Direction.CCW);

                    if (progessMap != null && progessMap.get(i) != null) {
                        if (currentItem == i) {
                            @SuppressLint("DrawAllocation") RectF rectF5 = new RectF(startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth, startY + itemHeight - bottomCornerWidth - topCornerWidth - progessMap.get(i), startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth + itemWidth - 2 * bottomCornerWidth, startY + itemHeight - bottomCornerWidth);
                            currentPath.addRoundRect(rectF5, radii, Path.Direction.CCW);


                            float topTextHeight = ((topMargin + itemHeight - itemHeight / 100 * integerList.get(i).getData()) - 80 - topMargin * 1 / 8);
//                            float topTextWith = (float) (Math.tan(Math.PI * topJiaoDu / 180) * topTextHeight);
                            float topTextWith = itemWidth * (i - 5 + 1) + (i - 4 + 1) * itemMargin - bottomCornerWidth - itemWidth / 2;//偏移一个

                            topLinePathCorner.addCircle(startX + itemWidth * (i + 1) + i * itemMargin - bottomCornerWidth - itemWidth / 2, topMargin + itemHeight - (itemHeight - bottomCornerWidth - topCornerWidth) * integerList.get(i).getData() / 100 - circleMargin, cornerWidth, Path.Direction.CCW);
                            topLinePath.moveTo(startX + itemWidth * (i + 1) + i * itemMargin - bottomCornerWidth - itemWidth / 2, topMargin + itemHeight - (itemHeight - bottomCornerWidth - topCornerWidth) * integerList.get(i).getData() / 100 - circleMargin);
                            if (f <= firstLineS) {
                                topLinePath.lineTo(startX + itemWidth * (i + 1) + i * itemMargin - bottomCornerWidth - itemWidth / 2 - topTextWith / firstLineS * f, topMargin / 8 + topTextHeight / firstLineS * (firstLineS - f));
                            } else {
                                topLinePath.lineTo(startX + itemWidth * (i + 1) + i * itemMargin - bottomCornerWidth - itemWidth / 2 - topTextWith, topMargin / 8);
                                topLinePath.lineTo(startX + itemWidth * (i + 1) + i * itemMargin - bottomCornerWidth - itemWidth / 2 - topTextWith - topLineWidth / (100 - firstLineS) * (f - firstLineS), topMargin / 8);
                                if (f == 100) {
                                    topTextX = startX + itemWidth * (i + 1) + i * itemMargin - bottomCornerWidth - itemWidth / 2 - topTextWith - topLineWidth;
                                    topTextY = topMargin / 8 + textMargin;
                                    canvas.drawText(titleText, topTextX + 15, topTextY + textMargin + textSize14 / 2, leftTextPaint);
                                    canvas.drawText(integerList.get(currentItem).getMile() + "", topTextX + 15, topTextY + textMargin * 2 + textSize14 + textSize11 / 2, desTextPaint);
                                }
                            }
                        } else {
                            @SuppressLint("DrawAllocation") RectF rectF6 = new RectF(startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth, startY + itemHeight - bottomCornerWidth - topCornerWidth - progessMap.get(i), startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth + itemWidth - 2 * bottomCornerWidth, startY + itemHeight - bottomCornerWidth);

                            path.addRoundRect(rectF6, radii, Path.Direction.CCW);
                        }
                    }
                } else {

                    if (currentItem == i) {
                        currentPathBottom.moveTo(startX + (itemWidth + itemMargin) * i - bottomCornerWidth, startY + itemHeight);
                        currentPathBottom.quadTo(startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth, startY + itemHeight, startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth, startY + itemHeight - bottomCornerWidth);
                        currentPathBottom.lineTo(startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth, startY + itemHeight - bottomCornerWidth - topCornerWidth);
                        currentPathBottom.lineTo(startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth + itemWidth - 2 * bottomCornerWidth, startY + itemHeight - bottomCornerWidth - topCornerWidth);
                        currentPathBottom.lineTo(startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth + itemWidth - 2 * bottomCornerWidth, startY + itemHeight - bottomCornerWidth);
                        currentPathBottom.quadTo(startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth + itemWidth - 2 * bottomCornerWidth, startY + itemHeight, startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth + itemWidth - 2 * bottomCornerWidth + bottomCornerWidth, startY + itemHeight);
                        currentPathBottom.lineTo(startX + (itemWidth + itemMargin) * i - bottomCornerWidth, startY + itemHeight);
                    } else {
                        pathBottom.moveTo(startX + (itemWidth + itemMargin) * i - bottomCornerWidth, startY + itemHeight);
                        pathBottom.quadTo(startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth, startY + itemHeight, startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth, startY + itemHeight - bottomCornerWidth);
                        pathBottom.lineTo(startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth, startY + itemHeight - bottomCornerWidth - topCornerWidth);
                        pathBottom.lineTo(startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth + itemWidth - 2 * bottomCornerWidth, startY + itemHeight - bottomCornerWidth - topCornerWidth);
                        pathBottom.lineTo(startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth + itemWidth - 2 * bottomCornerWidth, startY + itemHeight - bottomCornerWidth);
                        pathBottom.quadTo(startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth + itemWidth - 2 * bottomCornerWidth, startY + itemHeight, startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth + itemWidth - 2 * bottomCornerWidth + bottomCornerWidth, startY + itemHeight);
                        pathBottom.lineTo(startX + (itemWidth + itemMargin) * i - bottomCornerWidth, startY + itemHeight);
                    }
                    @SuppressLint("DrawAllocation") RectF rectF7 = new RectF(startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth, startY, startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth + itemWidth - 2 * bottomCornerWidth, startY + itemHeight - bottomCornerWidth);
                    pathBg.addRoundRect(rectF7, topCornerWidth, topCornerWidth, Path.Direction.CCW);

                    if (progessMap != null && progessMap.get(i) != null) {
                        if (currentItem == i) {
                            @SuppressLint("DrawAllocation") RectF rectF8 = new RectF(startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth, startY + itemHeight - bottomCornerWidth - topCornerWidth - progessMap.get(i), startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth + itemWidth - 2 * bottomCornerWidth, startY + itemHeight - bottomCornerWidth);
                            currentPath.addRoundRect(rectF8, radii, Path.Direction.CCW);

                            if (i < integerList.size() / 2) {//i是从0开始的

                                float topTextHeight = ((topMargin + itemHeight - itemHeight / 100 * integerList.get(i).getData()) - 80 - topMargin * 1 / 8);
//                                float topTextWith = (float) (Math.tan(Math.PI * topJiaoDu / 180) * topTextHeight);
                                float topTextWith = itemWidth * (6 - i) + (5 - i) * itemMargin - bottomCornerWidth - itemWidth / 2;
                                topLinePathCorner.addCircle(startX + itemWidth * (i + 1) + i * itemMargin - bottomCornerWidth - itemWidth / 2, topMargin + itemHeight - (itemHeight - bottomCornerWidth - topCornerWidth) * integerList.get(i).getData() / 100 - circleMargin, cornerWidth, Path.Direction.CCW);
                                topLinePath.moveTo(startX + itemWidth * (i + 1) + i * itemMargin - bottomCornerWidth - itemWidth / 2, topMargin + itemHeight - (itemHeight - bottomCornerWidth - topCornerWidth) * integerList.get(i).getData() / 100 - circleMargin);
                                if (f <= firstLineS) {
                                    topLinePath.lineTo(startX + itemWidth * (i + 1) + i * itemMargin - bottomCornerWidth - itemWidth / 2 + topTextWith / firstLineS * f, topMargin / 8 + topTextHeight / firstLineS * (firstLineS - f));
                                } else {
                                    topLinePath.lineTo(startX + itemWidth * (i + 1) + i * itemMargin - bottomCornerWidth - itemWidth / 2 + topTextWith, topMargin / 8);
                                    topLinePath.lineTo(startX + itemWidth * (i + 1) + i * itemMargin - bottomCornerWidth - itemWidth / 2 + topTextWith + topLineWidth / (100 - firstLineS) * (f - firstLineS), topMargin / 8);
                                    if (f == 100) {
                                        topTextX = startX + itemWidth * (i + 1) + i * itemMargin - bottomCornerWidth - itemWidth / 2 + topTextWith;
                                        topTextY = topMargin / 8 + textMargin;
                                        canvas.drawText(titleText, topTextX + 15, topTextY + textMargin + textSize14 / 2, leftTextPaint);
                                        canvas.drawText(integerList.get(currentItem).getMile() + "", topTextX + 15, topTextY + textMargin * 2 + textSize14 + textSize11 / 2, desTextPaint);
                                    }
                                }
                            } else {

                                float topTextHeight = ((topMargin + itemHeight - itemHeight / 100 * integerList.get(i).getData()) - 80 - topMargin * 1 / 8);
//                                float topTextWith = (float) (Math.tan(Math.PI * topJiaoDu / 180) * topTextHeight);
                                float topTextWith = itemWidth * (i - 4 + 1) + (i - 3 + 1) * itemMargin - bottomCornerWidth - itemWidth / 2;

                                topLinePathCorner.addCircle(startX + itemWidth * (i + 1) + i * itemMargin - bottomCornerWidth - itemWidth / 2, topMargin + itemHeight - (itemHeight - bottomCornerWidth - topCornerWidth) * integerList.get(i).getData() / 100 - circleMargin, cornerWidth, Path.Direction.CCW);
                                topLinePath.moveTo(startX + itemWidth * (i + 1) + i * itemMargin - bottomCornerWidth - itemWidth / 2, topMargin + itemHeight - (itemHeight - bottomCornerWidth - topCornerWidth) * integerList.get(i).getData() / 100 - circleMargin);
                                if (f <= firstLineS) {
                                    topLinePath.lineTo(startX + itemWidth * (i + 1) + i * itemMargin - bottomCornerWidth - itemWidth / 2 - topTextWith / firstLineS * f, topMargin / 8 + topTextHeight / firstLineS * (firstLineS - f));
                                } else {
                                    topLinePath.lineTo(startX + itemWidth * (i + 1) + i * itemMargin - bottomCornerWidth - itemWidth / 2 - topTextWith, topMargin / 8);
                                    topLinePath.lineTo(startX + itemWidth * (i + 1) + i * itemMargin - bottomCornerWidth - itemWidth / 2 - topTextWith - topLineWidth / (100 - firstLineS) * (f - firstLineS), topMargin / 8);
                                    if (f == 100) {
                                        topTextX = startX + itemWidth * (i + 1) + i * itemMargin - bottomCornerWidth - itemWidth / 2 - topTextWith - topLineWidth;
                                        topTextY = topMargin / 8 + textMargin;
                                        canvas.drawText(titleText, topTextX + 15, topTextY + textMargin + textSize14 / 2, leftTextPaint);
                                        canvas.drawText(integerList.get(currentItem).getMile() + "", topTextX + 15, topTextY + textMargin * 2 + textSize14 + textSize11 / 2, desTextPaint);
                                    }
                                }
                            }

                        } else {
                            @SuppressLint("DrawAllocation") RectF rectF9 = new RectF(startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth, startY + itemHeight - bottomCornerWidth - topCornerWidth - progessMap.get(i), startX + (itemWidth + itemMargin) * i - bottomCornerWidth + bottomCornerWidth + itemWidth - 2 * bottomCornerWidth, startY + itemHeight - bottomCornerWidth);
                            path.addRoundRect(rectF9, radii, Path.Direction.CCW);

                        }
                    }
                }


                canvas.drawPath(pathBg, backgroudPaint);
                canvas.drawPath(pathBottom, paint);
                canvas.drawPath(currentPathBottom, currentPaint);
                canvas.drawPath(path, paint);
                canvas.drawPath(currentPath, currentPaint);
                canvas.drawPath(topLinePath, topLinePaint);
                canvas.drawPath(topLinePathCorner, topLineCornerPaint);


            }
            for (int i = 0; i < integerList.size(); i++) {
                if (progessMap != null && progessMap.get(i) != null) {
                    canvas.drawText(integerList.get(i).getPrecentage() + "%", startX + itemWidth * (i + 1) + i * itemMargin - bottomCornerWidth - itemWidth / 2, topMargin + itemHeight - topCornerWidth - bottomCornerWidth - progessMap.get(i) - 10, textPaint);
                }
            }
            for (int j = 0; j < integerList.size() + 1; j++) {
                if (j == 0) {
                    canvas.drawText(j * 50 + "", startX, itemHeight + startY + UIUtils.dip2px(5) + textSize11, textPaint);
                } else if (j == integerList.size()) {
                    canvas.drawText("∞km", startX + j * (itemWidth + itemMargin) - itemMargin / 2 - 2 * bottomCornerWidth, itemHeight + startY + UIUtils.dip2px(5) + textSize11, textPaint);
                } else {
                    canvas.drawText(j * 50 + "", startX + j * (itemWidth + itemMargin) - itemMargin / 2 - bottomCornerWidth, itemHeight + startY + UIUtils.dip2px(5) + textSize11, textPaint);
                }
            }
            canvas.drawText("用户占比", startX, startY - 25 - textSize11 - 10, leftTextPaint);
            float height = (itemHeight - bottomCornerWidth - bottomCornerWidth) / 4;//虚线每一段的高度

            linePath.moveTo(startX, itemHeight + startY - bottomCornerWidth - 4 * height);
            linePath.lineTo(startX + (itemWidth + itemMargin) * (integerList.size()) - 2 * bottomCornerWidth - itemMargin, itemHeight + startY - bottomCornerWidth - 4 * height);
            linePath.moveTo(startX, itemHeight + startY - bottomCornerWidth - 3 * height);
            linePath.lineTo(startX + (itemWidth + itemMargin) * (integerList.size()) - 2 * bottomCornerWidth - itemMargin, itemHeight + startY - bottomCornerWidth - 3 * height);
            linePath.moveTo(startX, itemHeight + startY - bottomCornerWidth - 2 * height);
            linePath.lineTo(startX + (itemWidth + itemMargin) * (integerList.size()) - 2 * bottomCornerWidth - itemMargin, itemHeight + startY - bottomCornerWidth - 2 * height);
            linePath.moveTo(startX, itemHeight + startY - bottomCornerWidth - height);
            linePath.lineTo(startX + (itemWidth + itemMargin) * (integerList.size()) - 2 * bottomCornerWidth - itemMargin, itemHeight + startY - bottomCornerWidth - height);
            linePath.moveTo(startX, itemHeight + startY - bottomCornerWidth);
            linePath.lineTo(startX + (itemWidth + itemMargin) * (integerList.size()) - 2 * bottomCornerWidth - itemMargin, itemHeight + startY - bottomCornerWidth);
            canvas.drawPath(linePath, linePaint);

        }
        path.reset();
        pathBg.reset();
        currentPath.reset();
        currentPathBottom.reset();
        pathBottom.reset();
        linePath.reset();
        topLinePath.reset();
        topLinePathCorner.reset();
    }

    private void startAnim() {
        this.setLayerType(LAYER_TYPE_HARDWARE, null);
        animator = ObjectAnimator.ofFloat(0, 100);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                f = (float) animation.getAnimatedValue();
                for (int i = 0; i < integerList.size(); i++) {
                    progessMap.put(i, (itemHeight - bottomCornerWidth - topCornerWidth) / 100 * (f / 100) * integerList.get(i).getData());
                }
                postInvalidate();
                if (f == 100) {
                    BargraphView.this.setLayerType(LAYER_TYPE_NONE, null);
                }
            }

        });
        animator.setStartDelay(300);   //设置延迟开始
        animator.setDuration(1800);
        animator.setInterpolator(new LinearInterpolator());   //动画匀速
        animator.start();
    }
}
