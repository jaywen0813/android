package com.dpad.telematicsclientapp.mvp.router;

import android.app.Activity;

/**
 * 路由回调基类
 */

public class SimpleRouterCallback implements RouterCallback {

    @Override
    public void onBefore(Activity from, Class<?> to) {

    }

    @Override
    public void onNext(Activity from, Class<?> to) {

    }

    @Override
    public void onError(Activity from, Class<?> to, Throwable throwable) {

    }
}
