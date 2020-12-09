package com.dpad.telematicsclientapp.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;

import com.jph.takephoto.uitl.TUriParse;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


import static com.dpad.telematicsclientapp.util.ImageUtils.calculateInSampleSize;

/**
 * 图片旋转角度修正工具类
 * Author: crazycodeboy
 * Date: 2016/9/21 0007 20:10
 * Version:3.0.0
 * 技术博文：http://www.cboy.me
 * GitHub:https://github.com/crazycodeboy
 * Eamil:crazycodeboy@gmail.com
 */
public class ImageRotateUtil1 {

    public static ImageRotateUtil1 of(){
        return new ImageRotateUtil1();
    }

    private ImageRotateUtil1() {
    }

    /**
     * 纠正照片的旋转角度
     * @param path
     */
    public void correctImage(Context context,Uri path){

        String imagePath=TUriParse.parseOwnUri(context,path);
        int degree;
        if((degree=getBitmapDegree(imagePath))!=0){
            // *** add to fix oom
            BitmapFactory.Options opts = new BitmapFactory.Options();
            opts.inJustDecodeBounds = true; // 设置成了true,不占用内存，只获取bitmap宽高
            BitmapFactory.decodeFile(imagePath, opts);

            opts.inSampleSize = calculateInSampleSize(opts, 360, 640); // 调用上面定义的方法计算inSampleSize值
            opts.inJustDecodeBounds = false;// 使用获取到的inSampleSize值再次解析图片
            opts.inPreferredConfig = Bitmap.Config.RGB_565;
            opts.inDither = true;
            Bitmap bitmap = BitmapFactory.decodeFile(imagePath, opts);

            // *** add to fix end
            if(bitmap==null)return;
            Bitmap resultBitmap=rotateBitmapByDegree(bitmap,degree);
            if(resultBitmap==null)return;
            try {
                resultBitmap.compress(Bitmap.CompressFormat.JPEG,100,new FileOutputStream(new File(imagePath)));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }catch (OutOfMemoryError e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取图片的旋转的角度
     *
     * @param path 图片绝对路径
     * @return 图片的旋转角度
     */
    private int getBitmapDegree(String path) {
        int degree = 0;
        try {
            // 从指定路径下读取图片，并获取其EXIF信息
            ExifInterface exifInterface = new ExifInterface(path);
            // 获取图片的旋转信息
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }

    /**
     * 将图片按照某个角度进行旋转
     *
     * @param bm     需要旋转的图片
     * @param degree 旋转角度
     * @return 旋转后的图片
     */
    private Bitmap rotateBitmapByDegree(Bitmap bm, int degree) {
        Bitmap returnBm = null;

        // 根据旋转角度，生成旋转矩阵
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        try {
            // 将原始图片按照旋转矩阵进行旋转，并得到新的图片
            returnBm = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), matrix, true);
        } catch (OutOfMemoryError e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (returnBm == null) {
            returnBm = bm;
        }
        if (bm != returnBm) {
            bm.recycle();
        }
        return returnBm;
    }
}
