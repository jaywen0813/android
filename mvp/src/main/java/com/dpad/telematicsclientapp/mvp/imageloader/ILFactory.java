package com.dpad.telematicsclientapp.mvp.imageloader;

/**
 * Imageloader 工厂
 */
public class ILFactory {

    private static ILoader loader;


    public static ILoader getLoader() {
        if (loader == null) {
            synchronized (ILFactory.class) {
                if (loader == null) {
                    loader = new GlideLoader();
                }
            }
        }
        return loader;
    }


}