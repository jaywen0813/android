package com.dpad.telematicsclientapp.weiget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;

/**
 * ================================================
 * 作    者：wenbody
 * 版    本：1.0
 * 创建日期：2019/7/10 15:14
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class RotateTransformation extends BitmapTransformation {
    //旋转默认0
    private float rotateRotationAngle;

    public RotateTransformation(Context context, float rotateRotationAngle) {
        super(context);
        this.rotateRotationAngle = rotateRotationAngle;
    }

    @Override
    protected Bitmap transform(BitmapPool pool, Bitmap toTransform, int outWidth, int outHeight) {
        Matrix matrix = new Matrix();
        //旋转
        matrix.postRotate(rotateRotationAngle);
        //生成新的Bitmap
        return Bitmap.createBitmap(toTransform, 0, 0, toTransform.getWidth(), toTransform.getHeight(), matrix, true);

        //return null;
    }

    @Override
    public String getId() {
        return rotateRotationAngle + "";
    }

}
