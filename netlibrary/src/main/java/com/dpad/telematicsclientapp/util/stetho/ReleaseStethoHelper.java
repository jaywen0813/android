package com.dpad.telematicsclientapp.util.stetho;

import android.content.Context;



import com.dpad.telematicsclientapp.util.StethoHelper;

import okhttp3.OkHttpClient;

public class ReleaseStethoHelper implements StethoHelper {

    @Override
    public void init(Context context) {

    }

    @Override
    public OkHttpClient.Builder addNetworkInterceptor(OkHttpClient.Builder builder) {
        return null;
    }
}
