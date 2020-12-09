package com.dpad.telematicsclientapp.util;

import android.app.Activity;
import android.graphics.Point;
import android.text.InputFilter;
import android.text.Spanned;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;


import com.dpad.telematicsclientapp.util.utils.StatusBarUtil;
import com.dpad.telematicsclientapp.util.utils.T;
import com.socks.library.KLog;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ================================================
 * 作    者：booob
 * 版    本：1.0
 * 创建日期：2019-03-05-0005 13:52
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ViewUtils {

    private static Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\u4E00-\\u9FA5_]");

    /**
     * 伸入状态栏时,避免状态栏文字被遮挡,动态计算事先设定的view的高度等于状态栏高度
     *
     * @param activity
     * @param view
     */
    public static void setStatusView(Activity activity, View view) {
        if (view == null || activity == null) {
            KLog.e("view 或者 activity 不能为空");
            return;
        }
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.height = StatusBarUtil.getStatusHeight(activity);
        view.setLayoutParams(layoutParams);
    }

    /**
     * 根据ui给定的尺寸,动态设定图片的高度,避免图片在设定scaleType为fitXY时变形
     *
     * @param activity       当前页面的context
     * @param imageView      需要改变的image
     * @param webImageHeight 服务器图片的高度
     * @param webImageWidth  服务器图片的宽度
     * @param imageMargin    当前页面图片比屏幕小的宽度,默认为o
     */
    public static void setImageFitScreen(Activity activity, ImageView imageView, int webImageWidth, int webImageHeight, int imageMargin) {
        ViewGroup.LayoutParams layoutParams = imageView.getLayoutParams();
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        layoutParams.height = (width - imageMargin) * webImageHeight / webImageWidth;
        layoutParams.width = (width - imageMargin);
        imageView.setLayoutParams(layoutParams);
    }


    /**
     * 问题答案的校验,只能输入中文,英文,数字,长度不能超过15个汉字
     *
     * @param editText
     */
    public static void setInputFilter(EditText editText) {
        InputFilter[] filters = {new InputFilter() {
            @Override
            public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
                Matcher matcher = pattern.matcher(source);
                if (!matcher.find()) {
                    KLog.e("InputFilter", source.toString());
                    return null;
                } else {
                    T.showToastSafe("只能输入汉字,英文，数字");
                    return "";
                }
            }
        }, new InputFilter.LengthFilter(15)};
        editText.setFilters(filters);
    }

}
