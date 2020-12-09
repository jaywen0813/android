package com.dpad.telematicsclientapp.util.utils;

import com.socks.library.KLog;

/**
 * ================================================
 * 作    者：booob
 * 版    本：1.0
 * 创建日期：2018-07-19-0019 13:48
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ClickUtils {

    private static long lastClickTime = 0;
    private static int lastButtonId = -1;
    private static long DIFF = 800;    //时间间隔

    /**
     * 判断两次点击的间隔，如果小于1s，则认为是多次无效点击（任意两个view，固定时长1s）
     *
     * @return
     */
    public static boolean isFastDoubleClick() {
        return isFastDoubleClick(-1, DIFF);
    }

    /**
     * 判断两次点击的间隔，如果小于diff，则认为是多次无效点击（任意两个view，自定义间隔时长）
     *
     * @return
     */
    public static boolean isFastDoubleClick(long diff) {
        return isFastDoubleClick(-1, diff);
    }

    /**
     * 判断两次点击的间隔，如果小于1s，则认为是多次无效点击（同一个view，固定时长1s）
     *
     * @return
     */
    public static boolean isFastDoubleClick(int buttonId) {
        return isFastDoubleClick(buttonId, DIFF);
    }

    /**
     * 判断两次点击的间隔，如果小于diff，则认为是多次无效点击（同一按钮，自定义间隔时长）
     *
     * @param diff
     * @return
     */
    public static boolean isFastDoubleClick(int buttonId, long diff) {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        KLog.e(time + "time" + lastClickTime + "lastClickTime" + timeD + "timeD" + buttonId + "----" + lastButtonId);
        if (lastButtonId == buttonId && lastClickTime > 0 && timeD < diff) {
            KLog.d("isFastDoubleClick", "短时间内view被多次点击");
            return true;
        }
        lastClickTime = time;
        lastButtonId = buttonId;
        return false;
    }
}
