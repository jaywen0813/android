package com.dpad.telematicsclientapp.mvp.mvp;

import android.os.Bundle;
import android.view.View;

/**
 * V 接口
 */

public interface IView<P> {
    void bindUI(View rootView);

    void bindEvent();

    void initData(Bundle savedInstanceState);

    int getOptionsMenuId();

    int getLayoutId();

    boolean useEventBus();

    P newP();
}
