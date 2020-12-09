package com.dpad.telematicsclientapp.mvp.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * @创建者 booobdai.
 * @创建时间 2017/8/17  15:32.
 * @描述 ${Glide 圆形图片转换器 适用于 比赛球员半身像截取圆形 头部部分}.
 */
public class GlideCircleTransform_PlayerLogo extends BitmapTransformation {


    private static float mStrokeWidth;
    private static int   mStrokeColor;

    public GlideCircleTransform_PlayerLogo(Context context, int strokeColor, float strokeWidth) {
        super(context);
        mStrokeColor = strokeColor;
        mStrokeWidth = strokeWidth;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private static Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null)
            return null;

        int width = source.getWidth();
        int height = source.getHeight();
        int x = 0;
        int y = 0;
        int size = Math.min(width, height);
        if (width >= height) {
            x = (width - size) / 2;
            y = (height - size) / 2;
        }

        // TODO this could be acquired from the pool too
        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);

        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
        }

        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);

        //绘制边框
        Paint mBorderPaint = new Paint();
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mStrokeWidth);//边框宽度
        mBorderPaint.setColor(mStrokeColor);//边框颜色
        mBorderPaint.setStrokeCap(Paint.Cap.ROUND);
        mBorderPaint.setAntiAlias(true);

        float r = size / 2f;
        float r1 = (size - 2 * 4) / 2f;
        canvas.drawCircle(r, r, r, paint);
        canvas.drawCircle(r, r, r1, mBorderPaint);//画边框
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }
}