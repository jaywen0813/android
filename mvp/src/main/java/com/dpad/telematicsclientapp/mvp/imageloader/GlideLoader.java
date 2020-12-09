package com.dpad.telematicsclientapp.mvp.imageloader;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.GlideBitmapDrawable;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.dpad.telematicsclientapp.mvp.R;

import java.io.File;



/**
 * Glide封装
 */

public class GlideLoader implements ILoader {
    private Context context;

    @Override
    public void init(Context context) {
        this.context = context;
    }

    @Override
    public void loadNetCircle(ImageView target, String url) {
        loadNetCircle(target, url, null);
    }

    @Override
    public void loadNetCircle(ImageView target, String url, Options options) {
        if (TextUtils.isEmpty(url)) {
            load(getRequestManager(target.getContext()).load(R.drawable.shape_img_holder), target, options, true, 0, 0);
            return;
        }
        load(getRequestManager(target.getContext()).load(url.trim()), target, options, true, 0, 0);
    }

    @Override
    public void loadNetCircle(ImageView target, String url, Options options, int strokeColor, int strokeWidth) {
        if (TextUtils.isEmpty(url)) {
            load(getRequestManager(target.getContext()).load(R.drawable.shape_img_holder), target, options, true,
                    strokeColor, strokeWidth);
            return;
        }
        load(getRequestManager(target.getContext()).load(url.trim()), target, options, true, strokeColor, strokeWidth);
    }

    @Override
    public void loadPlayerLogoCircle(ImageView target, String url, Options options) {
        if (TextUtils.isEmpty(url)) {
            load(getRequestManager(target.getContext()).load(R.drawable.shape_img_holder), target, options, true, 0, 0);
            return;
        }
        if (options == null)
            options = Options.defaultOptions();
        DrawableTypeRequest<String> request = getRequestManager(target.getContext()).load(url.trim());
        if (options.loadingResId != Options.RES_NONE) {
            request.placeholder(options.loadingResId);
        }
        if (options.loadErrorResId != Options.RES_NONE) {
            request.error(options.loadErrorResId);
        }
        wrapScaleType(request, options)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .transform(new GlideCircleTransform_PlayerLogo(target.getContext(), 0, 0))
                .crossFade()
                .into(target);
    }

    @Override
    public void loadNetAutoHeight(ImageView target, String url) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        loadAutoHeight(target, url.trim());
    }

    @Override
    public void loadNet(ImageView target, String url) {
        loadNet(target, url, Options.defaultOptions());
//        if (!Kits.Empty.check(target) && target.getContext() != null) {
//            Glide.with(target.getContext())
//                    .load(url)
//                    .crossFade()  //淡入淡出
//                    .error(R.drawable.shape_img_holder)    //错误加载
//                    .placeholder(R.drawable.shape_img_holder);   //加载图
//        }
    }

    @Override
    public void loadNet(ImageView target, String url, Options options) {
        if (TextUtils.isEmpty(url)) {
            load(getRequestManager(target.getContext()).load(R.drawable.shape_img_holder), target, options, false,
                    0, 0);
            return;
        }
        load(getRequestManager(target.getContext()).load(url.trim()), target, options, false, 0, 0);
    }

    @Override
    public void loadNet(Context context, String url, Options options, final LoadCallback callback) {
        if (TextUtils.isEmpty(url)) {
            return;
        }
        DrawableTypeRequest request = getRequestManager(context).load(url.trim());
        if (options == null)
            options = Options.defaultOptions();

        if (options.loadingResId != Options.RES_NONE) {
            request.placeholder(options.loadingResId);
        }
        if (options.loadErrorResId != Options.RES_NONE) {
            request.error(options.loadErrorResId);
        }

        wrapScaleType(request, options)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .crossFade()
                .into(new SimpleTarget<GlideBitmapDrawable>() {

                    @Override
                    public void onLoadFailed(Exception e, Drawable errorDrawable) {
                        super.onLoadFailed(e, errorDrawable);
                        if (callback != null) {
                            callback.onLoadFailed(e);
                        }
                    }

                    @Override
                    public void onResourceReady(GlideBitmapDrawable resource, GlideAnimation<? super
                            GlideBitmapDrawable> glideAnimation) {
                        if (resource != null && resource.getBitmap() != null) {
                            if (callback != null) {
                                callback.onLoadReady(resource.getBitmap());
                            }
                        }
                    }

                });
    }

    @Override
    public void loadResource(ImageView target, int resId, Options options) {
        load(getRequestManager(target.getContext()).load(resId), target, options, false, 0, 0);
    }

    @Override
    public void loadAssets(ImageView target, String assetName, Options options) {
        load(getRequestManager(target.getContext()).load("file:///android_asset/" + assetName), target, options,
                false, 0, 0);
    }

    @Override
    public void loadFile(ImageView target, File file, Options options) {
        load(getRequestManager(target.getContext()).load(file), target, options, false, 0, 0);
    }

    @Override
    public void clearMemoryCache(Context context) {
        Glide.get(context).clearMemory();
    }

    @Override
    public void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();
    }

    @Override
    public void resume(Context context) {
        getRequestManager(context).resumeRequests();
    }

    @Override
    public void pause(Context context) {
        getRequestManager(context).pauseRequests();
    }

    private RequestManager getRequestManager(Context context) {
        if (context instanceof Activity) {
            return Glide.with((Activity) context);
        }
        return Glide.with(context);
    }

    /**
     * @param request
     * @param target
     * @param options
     * @param isCircle    是否圆形
     * @param strokeColor 圆形边框颜色
     * @param strokeWidth 圆形边框宽度
     */
    private void load(DrawableTypeRequest request, ImageView target, Options options, boolean isCircle, int
            strokeColor, int strokeWidth) {
        if (options == null)
            options = Options.defaultOptions();

        if (options.loadingResId != Options.RES_NONE) {
            request.placeholder(options.loadingResId);
        }
        if (options.loadErrorResId != Options.RES_NONE) {
            request.error(options.loadErrorResId);
        }
        if (isCircle) {
            wrapScaleType(request, options)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                    .transform(new GlideCircleTransform(target.getContext(), strokeColor, strokeWidth))
//                    .crossFade()
                    .dontAnimate()
                    .into(target);
        } else {
            wrapScaleType(request, options)
                    .diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                    .crossFade()
                    .dontAnimate()
                    .into(target);
        }
    }

    /**
     * 加载自适应高度图片(宽度充满, 高度自适应)
     *
     * @param iv
     * @param url
     */
    private void loadAutoHeight(final ImageView iv, String url) {
        Glide.with(iv.getContext())
                .load(url)
                .listener(new RequestListener<String, GlideDrawable>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<GlideDrawable> target, boolean
                            isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(GlideDrawable resource, String model, Target<GlideDrawable>
                            target, boolean isFromMemoryCache, boolean isFirstResource) {
                        if (iv.getScaleType() != ImageView.ScaleType.FIT_XY) {
                            iv.setScaleType(ImageView.ScaleType.FIT_XY);
                        }
                        ViewGroup.LayoutParams params = iv.getLayoutParams();
                        int vw = iv.getWidth() - iv.getPaddingLeft() - iv.getPaddingRight();
                        float scale = (float) vw / (float) resource.getIntrinsicWidth();
                        int vh = Math.round(resource.getIntrinsicHeight() * scale);
                        params.height = vh + iv.getPaddingTop() + iv.getPaddingBottom();
                        iv.setLayoutParams(params);
                        return false;
                    }
                })
                .into(iv);
    }

    private DrawableTypeRequest wrapScaleType(DrawableTypeRequest request, Options options) {
        if (options != null
                && options.scaleType != null) {
            switch (options.scaleType) {
                case MATRIX:
                    break;

                case FIT_XY:
                    break;

                case FIT_START:
                    break;

                case FIT_END:
                    break;

                case CENTER:
                    break;

                case CENTER_INSIDE:
                    break;

                case FIT_CENTER:
                    request.fitCenter();
                    break;

                case CENTER_CROP:
                    request.centerCrop();
                    break;
            }
        }

        return request;
    }
}
