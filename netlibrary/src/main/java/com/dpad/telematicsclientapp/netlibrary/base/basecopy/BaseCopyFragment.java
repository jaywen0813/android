package com.dpad.telematicsclientapp.netlibrary.base.basecopy;

import android.app.Dialog;
import android.os.Bundle;


import com.dpad.telematicsclientapp.mvp.kit.Kits;
import com.dpad.telematicsclientapp.mvp.mvp.XFragment;
import com.dpad.telematicsclientapp.mvp.mvp.XPresent;
import com.dpad.telematicsclientapp.util.DataStatisticsUtils;
import com.dpad.telematicsclientapp.util.utils.Constant;
import com.dpad.telematicsclientapp.util.utils.DialogUtil;
import com.dpad.telematicsclientapp.util.utils.T;
import com.socks.library.KLog;

import java.net.ConnectException;
import java.net.SocketTimeoutException;


import retrofit2.HttpException;
import rx.subscriptions.CompositeSubscription;

/**
 * @创建者 booobdai.
 * @创建时间 2017/8/2  15:28.
 * @描述 ${Fragment 基类}.
 */
public abstract class BaseCopyFragment<XP extends XPresent> extends XFragment<XP> {

    private static final String TAG = BaseCopyFragment.class.getSimpleName();

    //通用加载dialog
    private Dialog mLoadingDialog;

    public CompositeSubscription subscriptions = new CompositeSubscription();//rxjava网络请求

    private long startTime;
    private long endTime;

    /**
     * 首次初始化
     */
    private boolean mFirstInit = true;

    /**
     * 是否可见
     */
    private boolean mVisiable;

    @Override
    public void initData(Bundle savedInstanceState) {
        initPageData();
    }

    @Override
    public int getLayoutId() {
        return getLayout();
    }

    @Override
    public XP newP() {
        return newPresenter(subscriptions);
    }

    /**
     * 显示通用的加载中dialog
     *
     * @param text 显示的文本
     */
    public void loading(String text) {
        if (mLoadingDialog == null) {
            mLoadingDialog = DialogUtil.loading(context, text);
        } else {
            mLoadingDialog.show();
        }
    }

    /**
     * 加载完成
     */
    public void loadingComplete() {
        if (mLoadingDialog != null&&mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
        }
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mVisiable = true;
//            if (!Kits.Empty.check(getPageTitle())) {
                KLog.e("页面统计开始");
                startTime = System.currentTimeMillis();
                visiableLoadData();
                if (isTFragment()) {
                    DataStatisticsUtils.addData(DataStatisticsUtils.getDatasBean("", getPageTitle(), startTime + "", Constant.GO_IN));
                }
//            }
        } else {
            mVisiable = false;
//            if (!Kits.Empty.check(getPageTitle())) {
                KLog.e("页面统计结束");
                endTime = System.currentTimeMillis();
                if (isTFragment()) {
                    DataStatisticsUtils.addData(DataStatisticsUtils.getDatasBean("", getPageTitle(), startTime + "", Constant.LEAVE));
                }
//            }
        }
    }

    /**
     * 可见的时候刷新数据
     */
    public void visiableLoadData() {

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
    public void onResume() {
        super.onResume();
        //若首次初始化,默认可见并开启友盟统计
        if (mFirstInit) {
            mVisiable = true;
            mFirstInit = false;
            if (!Kits.Empty.check(getPageTitle())) {
                KLog.e("页面统计开始");
                startTime = System.currentTimeMillis();
                visiableLoadData();
                if (isTFragment()) {
                    DataStatisticsUtils.addData(DataStatisticsUtils.getDatasBean("", getPageTitle(), startTime + "", Constant.GO_IN));
                }
            }
            return;
        }

        //若当前界面可见,调用友盟开启跳转统计
        if (mVisiable) {
            visiableLoadData();
            if (!Kits.Empty.check(getPageTitle())) {
                KLog.e("页面统计开始");
                startTime = System.currentTimeMillis();
                if (isTFragment()) {
                    DataStatisticsUtils.addData(DataStatisticsUtils.getDatasBean("", getPageTitle(), startTime + "", Constant.GO_IN));
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //若当前界面可见,调用友盟结束跳转统计
        if (mVisiable) {
            if (!Kits.Empty.check(getPageTitle())) {
                KLog.e("页面统计结束");
                endTime = System.currentTimeMillis();
                if (isTFragment()) {
                    DataStatisticsUtils.addData(DataStatisticsUtils.getDatasBean("", getPageTitle(), startTime + "", Constant.LEAVE));
                }
//                MobclickAgent.onPageEnd(getPageTitle());
            }
        }
    }


    /**
     * 获取页面标题
     *
     * @return
     */
    protected String getPageTitle() {
        return null;
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
     * 初始化对应的PresenteKLog.d(TAG, "百度统计 : home " + mStatisticsTitles[mCurrentPosition] + " end , " +
     * mStatisticsTitles[position] + "start");r 可为null
     *
     * @return
     */
    protected abstract XP newPresenter(CompositeSubscription subscriptions);

    protected String getStatisticsTitle() {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mLoadingDialog != null && mLoadingDialog.isShowing()) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
        KLog.d(TAG, "Fragment onDestroy ----> " + this.getClass().getName());

    }

    /**
     * 是否是车联网的fragment,如果是,需要重新
     *
     * @return
     */
    protected boolean isTFragment() {
        return false;
    }
}
