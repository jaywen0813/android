package com.dpad.telematicsclientapp.util.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.chad.library.adapter.base.BaseQuickAdapter;

import com.dpad.telematicsclientapp.netlibrary.R;
import com.dpad.telematicsclientapp.weiget.BasicDialog;
import com.dpad.telematicsclientapp.weiget.DownDialog;
import com.dpad.telematicsclientapp.weiget.LoadingDialog;
import com.dpad.telematicsclientapp.weiget.PinDialog;


/**
 * @创建者 booobdai.
 * @创建时间 2017/8/10  16:16.
 * @描述 ${所有提示窗管理类}.
 */
public class DialogUtil {


    private DialogUtil() {
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    public interface OnDialogItemClickListener {
        void onClick(View v);
    }


    public interface OnSinglePickerSelectListener {
        void onSelect(int index);
    }

    public interface OnPopListItemClickListener {
        void onItemClick(PopupWindow pop, int position);
    }

    /**
     * 基本的LoadingDialog
     *
     * @param activity
     */
    public static Dialog loading(Activity activity) {
        return loading(activity, "");
    }

    /**
     * 自定义文本的LoadingDialog
     *
     * @param activity
     * @param text
     */
    public static Dialog loading(Activity activity, String text) {
        LoadingDialog loadingDialog = new LoadingDialog(activity, R.style.basic_dialog, text);
        loadingDialog.show();
        return loadingDialog;
    }

    public static Dialog loading(Activity activity, int resId) {
        return loading(activity, UIUtils.getString(resId));
    }

    /**
     * 基本的 确定/取消 dialog
     *
     * @param activity
     * @param title
     * @param message
     * @param onCloseListener
     * @return
     */
    public static Dialog showBasicDialog(Activity activity, String title, String message, BasicDialog.OnCloseListener
            onCloseListener) {
        BasicDialog basicDialog = new BasicDialog(activity, R.style.basic_dialog, onCloseListener);
        basicDialog.setTitle(title)
                .setContent(message)
                .show();
        return basicDialog;
    }


    /**
     * 基本的 确定/取消 dialog
     *
     * @param activity
     * @param title
     * @param message
     * @param onCloseListener
     * @return
     */
    public static Dialog showBasicDialog(Activity activity, String title, String message,String commitText, BasicDialog.OnCloseListener
            onCloseListener) {
        BasicDialog basicDialog = new BasicDialog(activity, R.style.basic_dialog, onCloseListener);
        basicDialog.setTitle(title)
                .setCancelVisible(false)
                .setPositiveButton(commitText)
                .setContent(message)
                .show();
        return basicDialog;
    }

    /**
     * 自定义 确定/取消 dialog
     *
     * @param activity
     * @param title
     * @param message
     * @param cancleText      取消文字
     * @param confirmText     确定文字
     * @param onCloseListener
     * @return
     */
    public static Dialog showBasicDialog(Activity activity, String title, String message, String cancleText,
                                         String confirmText, BasicDialog.OnCloseListener onCloseListener) {
        BasicDialog basicDialog = new BasicDialog(activity, R.style.basic_dialog, onCloseListener);
        basicDialog.setTitle(title)
                .setContent(message)
                .setPositiveButton(confirmText)
                .setNegativeButton(cancleText)
                .show();
        return basicDialog;
    }

    /**
     * 展示顶部有图片的dialog
     *
     * @param activity
     * @param message         提示文字
     * @param cancleText      取消的文字
     * @param confirmText     确认的文字
     * @param onCloseListener 监听器
     * @param resourcesId     顶部图片资源
     * @return
     */
    public static Dialog showBasicDialog(Activity activity, String message, String cancleText,
                                         String confirmText, BasicDialog.OnCloseListener onCloseListener, int resourcesId) {
        BasicDialog basicDialog = new BasicDialog(activity, R.style.basic_dialog, onCloseListener);
        basicDialog.setContent(message)
                .setPositiveButton(confirmText)
                .setNegativeButton(cancleText)
                .setTopImageResources(resourcesId)
                .show();
        return basicDialog;
    }

    /**
     * 展示顶部有图片的dialog,底部不带取消
     *
     * @param activity
     * @param message         提示文字
     * @param confirmText     确认的文字
     * @param onCloseListener 监听器
     * @param resourcesId     顶部图片资源
     * @return
     */
    public static Dialog showBasicDialog(Activity activity, String message,
                                         String confirmText, BasicDialog.OnCloseListener onCloseListener, int resourcesId) {
        BasicDialog basicDialog = new BasicDialog(activity, R.style.basic_dialog, onCloseListener);
        basicDialog.setContent(message)
                .setPositiveButton(confirmText)
                .setCancelVisible(false)
                .setTopImageResources(resourcesId)
                .show();
        return basicDialog;
    }

    /**
     * 自定义 确定/取消 dialog
     *
     * @param activity
     * @param title
     * @param message
     * @param cancleText      取消文字
     * @param confirmText     确定文字
     * @param onCloseListener
     * @return
     */
    public static Dialog showBasicDialog(boolean isLeft, Activity activity, String title, String message, String cancleText,
                                         String confirmText, BasicDialog.OnCloseListener onCloseListener) {
        BasicDialog basicDialog = new BasicDialog(activity, R.style.basic_dialog, onCloseListener);
        basicDialog.setTitle(title)
                .setContent(message)
                .setPositiveButton(confirmText)
                .setNegativeButton(cancleText)
                .setGravity(true)
                .show();
        return basicDialog;
    }

    /**
     * 获取权限失败后 提示跳转设置页面的dialog
     *
     * @param activity
     * @param title
     * @param message
     * @return
     */
    public static Dialog showPermissionSettingDialog(final Activity activity, String title, String message, boolean needFinish) {

        final boolean need=needFinish;

        Dialog dialog=showBasicDialog(activity, title, message, new BasicDialog.OnCloseListener() {
            @Override
            public void onClose(Dialog dialog, boolean confirm) {
                if (confirm) {
                //跳转系统权限设置界面
                Uri packageURI = Uri.parse("package:" + activity.getPackageName());
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI);
                activity.startActivityForResult(intent, Constant.STARTACTIVITY_PREMISSION);
                dialog.dismiss();
            } else {
                if (need) {
                    activity.finish();
                }
            }
            }
        });


        return dialog;
    }


    /**
     * 获取权限失败后 提示跳转设置页面的dialog
     *
     * @param activity
     * @param title
     * @param message
     * @return
     */
    public static Dialog showPermissionSettingDialog(final Activity activity, String title, String message, BasicDialog.OnCloseListener closeListener) {

        Dialog dialog = showBasicDialog(activity, title, message, closeListener);
        return dialog;
    }


    /**
     * 显示列表菜单 PopUpWindow
     *
     * @param activity
     * @param view     弹窗依附的view
     * @param adapter  列表适配器
     */
    public static PopupWindow showListPop(final Activity activity, View view, BaseQuickAdapter adapter,
                                          final OnPopListItemClickListener listener) {

        View popupView = LayoutInflater.from(activity).inflate(R.layout.pop_base_list, null);
        RecyclerView lvPopList = popupView.findViewById(R.id.lv_pop_list);
        lvPopList.setLayoutManager(new LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false));
        adapter.bindToRecyclerView(lvPopList);
        lvPopList.setAdapter(adapter);
//        lvPopList.addItemDecoration(new RecyclerViewDivider(activity, LinearLayoutManager.HORIZONTAL));
//        lvPopList.addItemDecoration(new RecyclerViewDivider(activity, LinearLayoutManager.HORIZONTAL, UIUtils.dip2px(1), activity.getResources().getColor(R.color.color_59595D)));

        final PopupWindow window = new PopupWindow(popupView,
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(activity, 1);
            }
        });
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                listener.onItemClick(window, position);
                window.dismiss();
            }
        });
        window.setOutsideTouchable(false);
        window.setFocusable(false);
        window.setTouchable(true);
        window.update();
        window.showAtLocation(view, Gravity.CENTER, 0, 0);
        setBackgroundAlpha(activity, 0.5f);
        return window;
    }


    /**
     * 设置添加屏幕的背景透明度
     *
     * @param bgAlpha 屏幕透明度0.0-1.0 1表示完全不透明
     */
    public static void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        activity.getWindow().setAttributes(lp);

    }

    /**
     * @param activity
     * @param onCloseListener
     * @return
     */
    public static DownDialog showDownDialog(Activity activity, DownDialog.OnCloseListener
            onCloseListener) {
        DownDialog downDialog = new DownDialog(activity, R.style.basic_dialog, onCloseListener);
        downDialog.show();
        return downDialog;
    }

    /**
     * @param activity
     * @param onCloseListener
     * @return
     */
    public static PinDialog showPinDialog(Activity activity, PinDialog.OnCloseListener
            onCloseListener) {
        PinDialog downDialog = new PinDialog(activity, R.style.basic_dialog, onCloseListener);
        downDialog.show();
        return downDialog;
    }
}