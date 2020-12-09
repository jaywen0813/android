package com.dpad.telematicsclientapp.netlibrary.base;

import android.app.Dialog;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.view.Menu;
import android.view.MenuItem;

//import com.dpad.crmclientapp.android.R;
//import com.dpad.crmclientapp.android.base.basecopy.AppManager;
//import com.dpad.crmclientapp.android.util.DataStatisticsUtils;
//import com.dpad.crmclientapp.android.util.utils.Constant;
//import com.dpad.crmclientapp.android.util.utils.DialogUtil;
//import com.dpad.crmclientapp.android.util.utils.NetBroadcastReceiver;
//import com.dpad.crmclientapp.android.util.utils.NetUtil;
//import com.dpad.crmclientapp.android.util.utils.StatusBarCompat;
//import com.dpad.crmclientapp.android.util.utils.StatusBarUtil;
//import com.dpad.crmclientapp.android.util.utils.T;
//import com.dpad.crmclientapp.android.util.utils.UIUtils;
import com.dpad.telematicsclientapp.mvp.kit.Kits;
import com.dpad.telematicsclientapp.netlibrary.R;
import com.dpad.telematicsclientapp.netlibrary.base.basecopy.AppManager;
import com.dpad.telematicsclientapp.util.DataStatisticsUtils;
import com.dpad.telematicsclientapp.util.utils.Constant;
import com.dpad.telematicsclientapp.util.utils.DialogUtil;
import com.dpad.telematicsclientapp.util.utils.NetBroadcastReceiver;
import com.dpad.telematicsclientapp.util.utils.NetUtil;
import com.dpad.telematicsclientapp.util.utils.StatusBarCompat;
import com.dpad.telematicsclientapp.util.utils.StatusBarUtil;
import com.dpad.telematicsclientapp.util.utils.T;
import com.dpad.telematicsclientapp.util.utils.UIUtils;
import com.socks.library.KLog;

//import cn.droidlover.xdroidmvp.kit.Kits;

/**
 * Created by vigss on 2018/3/13.
 */

public abstract class BaseActivity extends AppCompatActivity implements NetBroadcastReceiver.netEventHandler {
    public Dialog mLoadingDialog;

    private long startTime;
    private long endTime;

    static {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        mApp = (MainApplicaton) getApplication();
//        mApp.addActivity(this);
        AppManager.getInstance().addActivity(this);
        NetBroadcastReceiver.mListeners.add(this);
        if (getNeedSetDarkStatusText()) {
            StatusBarCompat.compat(this, UIUtils.getColor(R.color.base_app_bg));
            StatusBarUtil.StatusBarLightMode(this);
            StatusBarUtil.setStatusBarColor(this, R.color.base_app_bg);
        }
//        StatusBarUtil.fullScreen(this);
//        StatusBarUtil.StatusBarLightMode(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppManager.getInstance().finishActivity(this);
//        if (mApp != null) {
//            mApp.removeActivity(this);
//        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_UP) {
//            finish();
//        }
//        return super.dispatchKeyEvent(event);
//    }

    @Override
    public void onNetChange() {
        if (NetUtil.getNetworkState(this) == NetUtil.NETWORN_NONE) {
//            Toast.makeText(BaseActivity.this,"网络不可以使用", Toast.LENGTH_SHORT).show();
            T.showToastSafeError("网络不可以使用");
        } else {
//            Toast.makeText(BaseActivity.this,"网络可以使用",Toast.LENGTH_SHORT).show();
            T.showToastSafeOk("网络可以使用");
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
        if (!Kits.Empty.check(getPageTitle())) {
            KLog.e("页面统计开始");
            startTime = System.currentTimeMillis();
//            MobclickAgent.onPageStart(getPageTitle());
            if (isTActivity()) {
                DataStatisticsUtils.addData(DataStatisticsUtils.getDatasBean( "", getPageTitle(), startTime + "", Constant.GO_IN));
            }
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        MobclickAgent.onPause(this);
        if (!Kits.Empty.check(getPageTitle())) {
            KLog.e("页面统计结束");
            endTime = System.currentTimeMillis();
            //统计数据写入本地
            if (isTActivity()) {
                DataStatisticsUtils.addData(DataStatisticsUtils.getDatasBean( "", getPageTitle(), startTime + "",Constant.LEAVE));
            }
//            MobclickAgent.onPageEnd(getPageTitle());
        }
    }

    //友盟统计的pageTitle
    protected abstract String getPageTitle();

    //加载数据
    protected void showLoadingDialog(String text) {
        String s = "";
        if (!Kits.Empty.check(text)) {
            s = text;
        }
        if (mLoadingDialog == null) {
            mLoadingDialog = DialogUtil.loading(this, s);
        } else {
            mLoadingDialog.show();
        }
    }

    protected void cancleDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.cancel();
            mLoadingDialog = null;

        }
    }

    /**
     * 是否需要设置深色的状态栏
     *
     * @return
     */
    protected boolean getNeedSetDarkStatusText() {
        return false;
    }

    /**
     * 是否是车联网的activity,数据统计只统计车联网
     *
     * @return
     */
    protected boolean isTActivity() {
        return false;
    }
}
