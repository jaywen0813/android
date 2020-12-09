package com.dpad.telematicsclientapp.netlibrary.net;

import android.os.Build;


import com.dpad.telematicsclientapp.mvp.kit.Kits;
import com.dpad.telematicsclientapp.util.utils.UIUtils;



/**
 * @创建者 booobdai.
 * @创建时间 2017/9/11  10:28.
 * @描述 ${请求参数生成工具类}.
 */
public class ParameterUtil {
    private static final String TAG = ParameterUtil.class.getSimpleName();

    //app类型(1-ios,2-andriod)
    public static final int    TYPE           = 2;
    //手机型号
    public static final String MODEL          = Build.MODEL;
    //手机系统版本
    public static final String VERSION        = "android" + Build.VERSION.RELEASE;
    //app版本号
    public static final int    VERSION_NUMBER = Kits.Package.getVersionCode(UIUtils.getContext());
    //设备唯一id
    public static String uuid;

    /**
     * 获取时间戳 毫秒
     *
     * @return
     */
    public static long getTimeStamp() {
        return System.currentTimeMillis();
    }

    /**
     * 获取sign
     * //    一般接口: md5(client+signKey+其它参数)
     * //    用户登录后相关接口: md5(client+loginCode+signKey+其它参数)
     * //    调用注册客户端接口时签名中的client内容为空
     * //    测试signKey为：gooap12al
     * //    其他参数按照参数名的字母顺序把参数内容拼接起来
     *
     * @param shouldLogin 是否需要登录 (需要用到loginCode)
     * @param strs        其他参数
     * @return
     */
//    public static String getSign(boolean shouldLogin, String... strs) {
//        String client = (String) SPUtil.get(UIUtils.getContext(), SPConfig.API_CLIENT, "");
//        String loginCode = "";
//        if (shouldLogin) {
//            loginCode = (String) SPUtil.get(UIUtils.getContext(), SPConfig.USER_LOGIN_CODE, "");
//        }
//        String sign = client + loginCode + UrlConfig.SIGN_KEY;
//        for (String str : strs) {
//            KLog.d(TAG, str);
//            sign += str;
//        }
//        KLog.d(TAG, "getSign : " + sign + " .......... " + MD5.GetMD5Code(sign));
//        return MD5.GetMD5Code(sign);
//    }

    /**
     * 获取sign 注册client接口, client参数不需要
     *
     * @return
     */
//    public static String getSignWithoutClient(String... strs) {
//        String sign = UrlConfig.SIGN_KEY;
//        for (String str : strs) {
//            KLog.d(TAG, str);
//            sign += str;
//        }
//        KLog.d(TAG, "getSign : " + sign + " .......... " + MD5.GetMD5Code(sign));
//        return MD5.GetMD5Code(sign);
//    }


//    /**
//     * 获取设备唯一识别码
//     *
//     * @return
//     */
//    public static String getUUID() {
//        if (uuid == null) {
//            synchronized (ParameterUtil.class) {
//                if (uuid == null) {
//                    String id = (String) SPUtil.get(UIUtils.getContext(), SPConfig.API_DEVICE_ID, null);
//                    if (id != null) {
//                        uuid = id;
//                    } else {
//                        final String androidId = Settings.Secure.getString(MainApplicaton.getContext()
//                                .getContentResolver(), Settings.Secure.ANDROID_ID);
//                        try {
//                            if (!"9774d56d682e549c".equals(androidId)) {
//                                uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8")).toString();
//                            } else {
//                                @SuppressLint("MissingPermission") final String deviceId = ((TelephonyManager) MainApplicaton.getContext().getSystemService
//                                        (Context.TELEPHONY_SERVICE)).getDeviceId();
//                                uuid = deviceId != null ? UUID.nameUUIDFromBytes(deviceId.getBytes("utf8")).toString
//                                        () : UUID.randomUUID().toString();
//                            }
//                        } catch (UnsupportedEncodingException e) {
//                            throw new RuntimeException(e);
//                        }
//                        SPUtil.put(MainApplicaton.getContext(), SPConfig.API_DEVICE_ID, uuid);
//                    }
//                }
//            }
//        }
//        return uuid;
//    }

//    /**
//     * 获取client
//     *
//     * @return
//     */
//    public static String getClient() {
//        String client = (String) SPUtil.get(MainApplicaton.getContext(), SPConfig.API_CLIENT, "");
//        if (TextUtils.isEmpty(client)) {
//            //client为空时调用接口注册设备
//        }
//        return client;
//    }


}
