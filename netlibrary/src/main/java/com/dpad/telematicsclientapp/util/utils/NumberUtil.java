package com.dpad.telematicsclientapp.util.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 * @创建者 booobdai.
 * @创建时间 2017/9/26  17:32.
 * @描述 ${数字处理工具类}.
 */
public class NumberUtil {


    private static final String TAG = NumberUtil.class.getSimpleName();

    /**
     * 获取百分百
     *
     * @param number
     * @param total
     * @return
     */
    public static String getRatio(double number, double total) {
        if (total == 0 || number == 0) {
            return "0%";
        }
        double percent = number / total;
        //获取格式化对象
        NumberFormat nt = NumberFormat.getPercentInstance();
        //设置百分数精确度小数位0
        nt.setMinimumFractionDigits(0);
        return nt.format(percent);
    }

    /**
     * 小数转化位百分比
     *
     * @param d
     * @return
     */
    public static String getRatio(double d) {
        return getRatio(d, 1d);
    }

    /**
     * 格式化小数,保留两位小数
     *
     * @param d
     * @return
     */
    public static String getFormatDecimals(double d) {
        DecimalFormat df = new DecimalFormat("#.##");
        return df.format(d);
    }


    /**
     * 格式化货币显示
     *
     * @param d
     */
    public static String getFormatMoney(double d) {
        String str = "";
        NumberFormat nf = new DecimalFormat("#,###");
        str = nf.format(d);
        return str;
    }

    /**
     * 获取格式化的数量超过千用K表示
     *
     * @param count
     * @return
     */
    //    public static String getCount(int count) {
    //        String countStr = "";
    //        if (count < 1000) {
    //            countStr = count + "";
    //        } else {
    //            double d = (double) count;
    //            DecimalFormat df = new DecimalFormat("#.0");
    //            df.setRoundingMode(RoundingMode.FLOOR);
    //            countStr = df.format(d / 1000) + "k";
    //        }
    //        KLog.d(TAG, count + " ----> " + countStr);
    //        return countStr;
    //    }

    /**
     * 获取评论, 点赞, 查看 等等数量最多四位(超过999显示999+,)
     *
     * @param count 数量
     * @return
     */
    public static String getCommentCount(int count) {
        return count >= 999 ? "999+" : count + "";
    }
}
