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
 * @描述 ${Glide 圆形图片转换器}.
 */
public class GlideCircleTransform extends BitmapTransformation {

    private Paint mBorderPaint;
    private float mBorderWidth;


    public GlideCircleTransform(Context context, int borderColor, int borderWidth) {
        super(context);
        //        mBorderWidth = Resources.getSystem().getDisplayMetrics().density * borderWidth;
        mBorderWidth = borderWidth;
        mBorderPaint = new Paint();
        mBorderPaint.setDither(true);
        mBorderPaint.setAntiAlias(true);
        mBorderPaint.setColor(borderColor);
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(mBorderWidth);
    }


    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        return circleCrop(pool, toTransform);
    }

    private Bitmap circleCrop(BitmapPool pool, Bitmap source) {
        if (source == null)
            return null;

        int size = (int) (Math.min(source.getWidth(), source.getHeight()) - (mBorderWidth / 2));
        int x = (source.getWidth() - size) / 2;
        int y = (source.getHeight() - size) / 2;
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
        float r = size / 2f;
        canvas.drawCircle(r, r, r, paint);
        if (mBorderPaint != null) {
            float borderRadius = r - mBorderWidth / 2;
            canvas.drawCircle(r, r, borderRadius, mBorderPaint);
        }
        return result;
    }

    @Override
    public String getId() {
        return getClass().getName();
    }

    //    private float mStrokeWidth;
    //    private int   mStrokeColor;
    //
    //    public GlideCircleTransform(Context context, int strokeColor, float strokeWidth) {
    //        super(context);
    //        mStrokeColor = strokeColor;
    //        mStrokeWidth = strokeWidth;
    //    }
    //
    //    @Override
    //    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
    //        return circleCrop(pool, toTransform);
    //    }
    //
    //    private  Bitmap circleCrop(BitmapPool pool, Bitmap source) {
    //        if (source == null)
    //            return null;
    //
    //        int size = Math.min(source.getWidth(), source.getHeight());
    //        int x = (source.getWidth() - size) / 2;
    //        int y = (source.getHeight() - size) / 2;
    //
    //        // TODO this could be acquired from the pool too
    //        Bitmap squared = Bitmap.createBitmap(source, x, y, size, size);
    //
    //        Bitmap result = pool.get(size, size, Bitmap.Config.ARGB_8888);
    //        if (result == null) {
    //            result = Bitmap.createBitmap(size, size, Bitmap.Config.ARGB_8888);
    //        }
    //
    //        Canvas canvas = new Canvas(result);
    //        Paint paint = new Paint();
    //        paint.setShader(new BitmapShader(squared, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
    //        paint.setAntiAlias(true);
    //
    //        //绘制边框
    //        Paint mBorderPaint = new Paint();
    //        mBorderPaint.setStyle(Paint.Style.STROKE);
    //        mBorderPaint.setStrokeWidth(mStrokeWidth);//边框宽度
    //        mBorderPaint.setColor(mStrokeColor);//边框颜色
    //        mBorderPaint.setStrokeCap(Paint.Cap.ROUND);
    //        mBorderPaint.setAntiAlias(true);
    //
    //        float r = size / 2f;
    //        float r1 = (size - 2 * 4) / 2f;
    //        canvas.drawCircle(r, r, r, paint);
    //        canvas.drawCircle(r, r, r1, mBorderPaint);//画边框
    //        return result;
    //    }
    //
    //    @Override
    //    public String getId() {
    //        return getClass().getName();
    //    }
}