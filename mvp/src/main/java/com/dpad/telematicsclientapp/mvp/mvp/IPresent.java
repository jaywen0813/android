package com.dpad.telematicsclientapp.mvp.mvp;

/**
 * P 接口
 */

public interface IPresent<V> {
    void attachV(V view);

    void detachV();
}
