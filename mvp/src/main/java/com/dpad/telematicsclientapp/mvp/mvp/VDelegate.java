package com.dpad.telematicsclientapp.mvp.mvp;

import android.view.View;

/**
 * V层 代表类接口
 */

public interface VDelegate {
    void resume();

    void pause();

    void destory();

    void visible(boolean flag, View view);
    void gone(boolean flag, View view);
    void inVisible(View view);

    void toastShort(String msg);
    void toastLong(String msg);
}
