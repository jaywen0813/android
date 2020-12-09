package com.dpad.telematicsclientapp.util.utils;

import android.app.Activity;
import android.view.Gravity;


import com.dpad.telematicsclientapp.netlibrary.base.basecopy.AppManager;
import com.dpad.telematicsclientapp.weiget.toast.StyleableToast;
import com.socks.library.KLog;


/**
 * @创建者 booob.
 * @创建时间 2018/8/3  16:38.
 * @描述 ${Toast 工具类}.
 */
public class T {

    private static final String TAG = T.class.getSimpleName();
    private static StyleableToast.Builder builder;
//    private static Toast sToast;

    /**
     * 线程安全，可以在非UI线程调用。
     */
    public static void showToastSafe(final int resId) {
        showToastSafe(UIUtils.getString(resId));
    }

    /**
     * 线程安全，可以在非UI线程调用。
     */
    public static void showToastSafe(final String str) {
        if (UIUtils.isRunInMainThread()) {
            showToast(str);
        } else {
            UIUtils.post(new Runnable() {
                @Override
                public void run() {
                    showToast(str);
                }
            });
        }
    }

    private static void showToast(String str) {
        Activity activity = AppManager.getInstance().getCurrentActivity();
        if (activity == null) {
            return;
        }
        if (android.text.TextUtils.isEmpty(str)) {
            return;
        }

        if (builder == null) {
            KLog.d(TAG, "toast new");
            builder = new StyleableToast.Builder(activity);
            builder.text(str);
            builder.type(0);
            builder.gravity(Gravity.CENTER);
        } else {
            builder.cancel();
            KLog.d(TAG, "toast old");

            builder.text(str);
            builder.type(0);
        }
        builder.show();
    }

    /**
     * 线程安全，可以在非UI线程调用。
     */
    public static void showToastSafeError(final String str) {
        if (UIUtils.isRunInMainThread()) {
            showToastError(str);
        } else {
            UIUtils.post(new Runnable() {
                @Override
                public void run() {
                    showToastError(str);
                }
            });
        }
    }

    private static void showToastError(String str) {
        Activity activity = AppManager.getInstance().getCurrentActivity();
        if (activity == null) {
            return;
        }
        if (android.text.TextUtils.isEmpty(str)) {
            return;
        }

        if (builder == null) {
            KLog.d(TAG, "toast new");
            builder = new StyleableToast.Builder(activity);
            builder.text(str);
            builder.type(2);
            builder.gravity(Gravity.CENTER);
        } else {
            builder.cancel();
            KLog.d(TAG, "toast old");
            builder.text(str);
            builder.type(2);
        }
        builder.show();
    }

    /**
     * 线程安全，可以在非UI线程调用。
     */
    public static void showToastSafeOk(final String str) {
        if (UIUtils.isRunInMainThread()) {
            showToastOk(str);
        } else {
            UIUtils.post(new Runnable() {
                @Override
                public void run() {
                    showToastOk(str);
                }
            });
        }
    }

    private static void showToastOk(String str) {
        Activity activity = AppManager.getInstance().getCurrentActivity();
        if (activity == null) {
            return;
        }
        if (android.text.TextUtils.isEmpty(str)) {
            return;
        }

        if (builder == null) {
            KLog.d(TAG, "toast new");
            builder = new StyleableToast.Builder(activity);
            builder.text(str);
            builder.type(1);
            builder.gravity(Gravity.CENTER);
        } else {
            builder.cancel();
            KLog.d(TAG, "toast old");
            builder.type(1);
            builder.text(str);
        }
        builder.show();
    }

    /**
     * 取消吐司
     */
    public static void cancelToast() {
        if (builder != null) {
            builder.cancel();
        }
    }
}
