package com.dpad.telematicsclientapp.mvp;


import com.dpad.telematicsclientapp.mvp.router.Router;

/**
 * 基础配置
 */
public class XDroidConf {
    // #log
    public static final boolean LOG = true;
    public static final String LOG_TAG = "Dpadclient";

    // #cache
    public static final String CACHE_SP_NAME = "config";
    public static final String CACHE_DISK_DIR = "cache";

    // #router
    public static final int ROUTER_ANIM_ENTER = Router.RES_NONE;
    public static final int ROUTER_ANIM_EXIT = Router.RES_NONE;

    // #imageloader
    public static final int IL_LOADING_RES = R.drawable.shape_img_holder;
    public static final int IL_ERROR_RES = R.drawable.shape_img_holder;

    // #dev model
    public static final boolean DEV = true;
}
