package com.dpad.telematicsclientapp.netlibrary.base.basecopy;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;




import com.dpad.telematicsclientapp.mvp.kit.Kits;
import com.dpad.telematicsclientapp.mvp.mvp.XActivity;
import com.dpad.telematicsclientapp.mvp.mvp.XPresent;
import com.dpad.telematicsclientapp.netlibrary.MainApplicaton;
import com.dpad.telematicsclientapp.netlibrary.R;
import com.dpad.telematicsclientapp.util.DataStatisticsUtils;
import com.dpad.telematicsclientapp.util.utils.Constant;
import com.dpad.telematicsclientapp.util.utils.DialogUtil;
import com.dpad.telematicsclientapp.util.utils.PinDialogUtils;
import com.dpad.telematicsclientapp.util.utils.StatusBarCompat;
import com.dpad.telematicsclientapp.util.utils.StatusBarUtil;
import com.dpad.telematicsclientapp.util.utils.T;
import com.dpad.telematicsclientapp.util.utils.UIUtils;
import com.dpad.telematicsclientapp.weiget.BaseTitleView;
import com.socks.library.KLog;

import java.net.ConnectException;
import java.net.SocketTimeoutException;


import retrofit2.HttpException;
import rx.subscriptions.CompositeSubscription;


/**
 * @创建者 booobdai.
 * @创建时间 2017/8/2  13:54.
 * @描述 ${Activity基类}.
 */
public abstract class BaseCopyActivity<XP extends XPresent> extends XActivity<XP> {

    private static final String TAG = BaseCopyActivity.class.getSimpleName();

    //从其他地方进入 如 1.极光推送的通知栏进入 2.广告点击进入
    public static final String BUNDLE_IS_FROM_OTHER = "bundle_is_from_other";
    //是否是从极光推送的通知栏进入的标记
    protected boolean mIsFromOther;

    public CompositeSubscription subscriptions = new CompositeSubscription();//rxjava网络请求

    public MainApplicaton mApp;
    //通用加载dialog
    public Dialog mLoadingDialog;
    private View mErrorView;
    private BaseTitleView mTtError;

    private long startTime;
    private long endTime;

    private long currentTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        //设置滑动切换动画和透明状态栏主题
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        int statusColor = getStatusColor();
        if (getNeedSetDarkStatusText()) {
            StatusBarCompat.compat(this, statusColor);
            StatusBarUtil.clearStatusBarLightMode(this);
            StatusBarUtil.setStatusBarColor(this, R.color.base_app_bg);
        }
    }

    protected int getStatusColor() {
        return UIUtils.getColor(R.color.base_app_bg);
    }

    @Override
    public void initData(Bundle savedInstanceState) {
//        if (mApp == null) {
//            mApp = (MainApplicaton) getApplication();
//        }
//        mApp.addActivity(this);
        AppManager.getInstance().addActivity(this);
        Intent intent = getIntent();
        mIsFromOther = intent.getBooleanExtra(BUNDLE_IS_FROM_OTHER, false);
        if (MainApplicaton.isNeedShowPinDialog()) {
            MainApplicaton.currentTouchTime = System.currentTimeMillis();
        }
        initPageData();
    }

    /**
     * 检查当前是否登录
     *
     * @return
     */
    protected boolean checkLogin() {
        //检查是否登录,
        if (MainApplicaton.sIsLogin) {
            return true;
        }
        //未登录就去登录页面
        return false;
    }

    @Override
    public int getLayoutId() {
        return getLayout();
    }


    @Override
    public XP newP() {
        return newPresenter(subscriptions);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
        if (subscriptions != null) {
            subscriptions.clear();
        }
        AppManager.getInstance().finishActivity(this);
//        if (mApp != null) {
//            mApp.removeActivity(this);
//        }


    }

    /**
     * 显示通用的加载中dialog
     *
     * @param text 显示的文本
     */
    public void loading(String text) {
        if (mLoadingDialog == null) {
            mLoadingDialog = DialogUtil.loading(this, text);
        } else {
            mLoadingDialog.show();
        }
    }

    /**
     * 加载完成
     */
    public void loadingComplete() {
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    /**
     * 处理网络异常
     *
     * @param throwable
     */
    public void dealHttpError(Throwable throwable) {
        if (throwable instanceof HttpException || throwable instanceof ConnectException || throwable instanceof SocketTimeoutException) {
            T.showToastSafeError("服务器开小差了，请稍后再试~");
        } else {
            KLog.e(throwable.toString());
        }
    }

    @Override
    public void finish() {
        closeThisActivity();
    }

    /**
     * 关闭当前页面
     */
    public void closeThisActivity() {
        super.finish();
    }


    /**
     * 获取页面标题
     *
     * @return
     */
    protected String getTitleText() {
        return null;
    }

    /**
     * 重新加载
     */
    public void reLoad() {
        setContentView(contentView);
    }

    /**
     * 加载页面数据
     */
    protected abstract void initPageData();

    /**
     * 初始化布局文件Id
     *
     * @return
     */
    protected abstract int getLayout();

    /**
     * 初始化对应的Presenter 可为null
     *
     * @return
     */
    protected abstract XP newPresenter(CompositeSubscription subscriptions);


    @Override
    protected void onResume() {
        super.onResume();
//        MobclickAgent.onResume(this);
        if (!Kits.Empty.check(getPageTitle())) {
            KLog.e("页面统计开始");
            startTime = System.currentTimeMillis();
//            MobclickAgent.onPageStart(getPageTitle());
            if (isTActivity()) {
                if (!Kits.Empty.check(getLocalVin())) {
                    DataStatisticsUtils.addData(DataStatisticsUtils.getDatasBeanWithVin("", getPageTitle(), startTime + "",getLocalVin(), Constant.GO_IN));
                } else {
                    DataStatisticsUtils.addData(DataStatisticsUtils.getDatasBean("", getPageTitle(), startTime + "", Constant.GO_IN));
                }
            }
        }
        if (MainApplicaton.isNeedShowPinDialog()) {
            currentTime = System.currentTimeMillis();
            if (currentTime - MainApplicaton.currentTouchTime <= Constant.BREAKTIME) {
                MainApplicaton.currentTouchTime = currentTime;
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
                if (!Kits.Empty.check(getLocalVin())) {

                    DataStatisticsUtils.addData(DataStatisticsUtils.getDatasBeanWithVin("", getPageTitle(), startTime + "", getLocalVin(), Constant.LEAVE));

                } else {
                    DataStatisticsUtils.addData(DataStatisticsUtils.getDatasBean("", getPageTitle(), startTime + "", Constant.LEAVE));
                }
            }
//            MobclickAgent.onPageEnd(getPageTitle());
        }
    }

    //友盟统计的pageTitle
    protected abstract String getPageTitle();

    /**
     * 是否需要设置深色的状态栏
     *
     * @return
     */
    protected boolean getNeedSetDarkStatusText() {
        return true;
    }

    /**
     * 是否是车联网的activity,数据统计只统计车联网
     *
     * @return
     */
    protected boolean isTActivity() {
        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (MainApplicaton.isNeedShowPinDialog()) {
            currentTime = System.currentTimeMillis();
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    if (currentTime - MainApplicaton.currentTouchTime > Constant.BREAKTIME) {
                        PinDialogUtils.getPinDialogUtils(this).checkFingerPrint();
                        return true;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    if (currentTime - MainApplicaton.currentTouchTime > Constant.BREAKTIME) {
                        PinDialogUtils.getPinDialogUtils(this).checkFingerPrint();
                        return true;
                    }
                    break;
                default:
                    break;
            }
            MainApplicaton.currentTouchTime = System.currentTimeMillis();
            return super.dispatchTouchEvent(ev);
        } else {
            MainApplicaton.currentTouchTime = Long.MAX_VALUE;
        }
        return super.dispatchTouchEvent(ev);
    }

    public Activity mContext() {
        return this;
    }

    /**
     * 部分页面需要传其他Vin
     *
     * @return
     */
    protected String getLocalVin() {
        return null;
    }

}
