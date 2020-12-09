package com.dpad.telematicsclientapp.netlibrary.base.basecopy;

import android.app.Activity;
import android.content.Context;



import com.socks.library.KLog;

import java.util.Stack;

/**
 * @创建者 booobdai.
 * @创建时间 2017/10/9  17:18.
 * @描述 ${Activity 管理类}.
 */
public class AppManager {
    private static AppManager sInstance = new AppManager();
    private static Stack<Activity> activityStack = new Stack<Activity>();


    private AppManager() {

    }

    public static AppManager getInstance() {
        return sInstance;
    }

    public Activity getCurrentActivity() {
        Activity currentActivity = null;
        if (activityStack != null && activityStack.size() != 0) {
            try {
                currentActivity = activityStack.lastElement();
            } catch (Exception e) {
                KLog.e(e.toString());
                return null;
            }
        }
        return currentActivity;
    }


    /*
  入栈
   */
    public void addActivity(Activity activity) {
        activityStack.add(activity);
    }

    /*
    出栈
     */
    public void finishActivity(Activity activity) {
        activity.finish();
        activityStack.remove(activity);
    }

    /**
     * 获取当前的activity
     */
    public Activity currentActivity(Activity activity) {
        return activityStack.lastElement();
    }

    /**
     * 清理栈
     */
    public void clearStack() {
        for (Activity activity : activityStack) {
            activity.finish();
        }
        activityStack.clear();
    }

    /**
     * 清理栈
     */
    public void clearOnlyLoginActivity() {
//        for (Activity activity : activityStack) { //2020年12月9日 10:10:45 注释
//            if (!(activity instanceof KtLoginActivity)) {
//                activity.finish();
//            }
//
//        }

    }

    /**
     * 退出App
     */
    public void exitApp(Context context) {
        clearStack();
        android.app.ActivityManager activityManager = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        assert activityManager != null;
        activityManager.killBackgroundProcesses(context.getPackageName());
        System.exit(0);
    }

    /**
     * 浅退出App
     */
    public void lightExitApp(Context context) {
        clearStack();
//        android.app.ActivityManager activityManager = (android.app.ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
//        assert activityManager != null;
//        activityManager.killBackgroundProcesses(context.getPackageName());
//        System.exit(0);
    }

}
