package com.dpad.telematicsclientapp.util;

import android.content.Context;

import okhttp3.OkHttpClient;


public interface StethoHelper {

    void init(Context context);

    OkHttpClient.Builder addNetworkInterceptor(OkHttpClient.Builder builder);
}
