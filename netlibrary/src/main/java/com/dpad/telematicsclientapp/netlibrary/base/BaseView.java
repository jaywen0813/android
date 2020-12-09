package com.dpad.telematicsclientapp.netlibrary.base;

import android.content.Context;

/**
 * Created by vigss on 2018/3/13.
 */

public interface BaseView<T>{
    void setPresenter(T presenter);
    Context getContext();
}
