package com.dpad.telematicsclientapp.mvp.router;

import android.app.Activity;

/**
 * 路由回调 接口
 */

public interface RouterCallback {

    void onBefore(Activity from, Class<?> to);

    void onNext(Activity from, Class<?> to);

    void onError(Activity from, Class<?> to, Throwable throwable);

}
