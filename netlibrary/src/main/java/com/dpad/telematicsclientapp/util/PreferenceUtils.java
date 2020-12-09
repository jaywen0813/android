package com.dpad.telematicsclientapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.dpad.crmclientapp.android.util.utils.Constant;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class PreferenceUtils {
    /**
     * 保存Preference的name
     */
    public static final String PREFERENCE_NAME = "DPAD";
    private static SharedPreferences mSharedPreferences;
    private static PreferenceUtils mPreferenceUtils;
    private static SharedPreferences.Editor editor;

    private String SHARED_KEY_SETTING_NOTIFICATION = "shared_key_setting_notification";

    private PreferenceUtils(Context cxt) {
        mSharedPreferences = cxt.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * 单例模式，获取instance实例
     *
     * @param cxt
     * @return
     */
    public static PreferenceUtils getInstance(Context cxt) {
        if (mPreferenceUtils == null) {
            mPreferenceUtils = new PreferenceUtils(cxt);
        }
        editor = mSharedPreferences.edit();
        return mPreferenceUtils;
    }

    public void setSettingMsgNotification(boolean paramBoolean) {
        editor.putBoolean(SHARED_KEY_SETTING_NOTIFICATION, paramBoolean);
        editor.commit();
    }

    public boolean getSettingMsgNotification() {
        return mSharedPreferences.getBoolean(SHARED_KEY_SETTING_NOTIFICATION,
                true);
    }

    public void setString(String key, String value) {
        editor.putString(key, value);
        editor.commit();
    }

    public String getString(String key) {
        return mSharedPreferences.getString(key, "");
    }

    public boolean getBoolean(String key) {
        return mSharedPreferences.getBoolean(key, false);
    }

    public void setBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }


    /**
     * 清除所有数据
     *
     * @param context
     */
    public void clear(Context context) {
        String uniqueId = PreferenceUtils.getInstance(context).getString(Constant.DEVICEID_KEY);
        String userName = PreferenceUtils.getInstance(context).getString(Constant.LOGIN_NAME);
        String cityName = PreferenceUtils.getInstance(context).getString("cityName");
        String fingerData = PreferenceUtils.getInstance(context).getString(Constant.USE_FINGERPRINT_KEY);

        boolean agreeMent = PreferenceUtils.getInstance(context).getBoolean(Constant.KEY_AGREEMENT);
        String versionCode = PreferenceUtils.getInstance(context).getString(Constant.KEY_VERSIONCODE);
        SharedPreferences sp = context.getSharedPreferences(PREFERENCE_NAME,
                Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);

        setString(Constant.DEVICEID_KEY, uniqueId);
        setString(Constant.LOGIN_NAME, userName);
        setString("cityName", cityName);
        setString(Constant.USE_FINGERPRINT_KEY, fingerData);
        setBoolean(Constant.KEY_AGREEMENT, agreeMent);
        setString(Constant.KEY_VERSIONCODE, versionCode);
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     *
     * @author zhy
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         *
         * @return
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         *
         * @param editor
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
            } catch (IllegalAccessException e) {
            } catch (InvocationTargetException e) {
            }
            editor.commit();
        }
    }
}
