package com.dpad.telematicsclientapp.netlibrary.base;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.dpad.telematicsclientapp.mvp.kit.Kits;
import com.dpad.telematicsclientapp.util.DataStatisticsUtils;
import com.dpad.telematicsclientapp.util.utils.Constant;
import com.socks.library.KLog;



/**
 * Created by vigss on 2018/3/13.
 */

public abstract class BaseFragment extends Fragment {

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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
//        fragmentLifecycleSubject.onNext(FragmentLifecycleEvent.DESTROY_VIEW);
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
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
     * 是否是车联网的fragment,如果是,需要重写
     *
     * @return
     */
    protected boolean isTFragment() {
        return false;
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            mVisiable = true;
            if (!Kits.Empty.check(getPageTitle())) {
                KLog.e("页面统计开始");
                startTime = System.currentTimeMillis();
//                MobclickAgent.onPageStart(getPageTitle());
                if (isTFragment()) {
                    DataStatisticsUtils.addData(DataStatisticsUtils.getDatasBean((endTime - startTime) + "", getPageTitle(), startTime + "", Constant.GO_IN));
                }
            }
        } else {
            mVisiable = false;
            if (!Kits.Empty.check(getPageTitle())) {
                KLog.e("页面统计结束");
                endTime = System.currentTimeMillis();
                if (isTFragment()) {
                    DataStatisticsUtils.addData(DataStatisticsUtils.getDatasBean((endTime - startTime) + "", getPageTitle(), startTime + "",Constant.LEAVE));
                }
//                MobclickAgent.onPageEnd(getPageTitle());
            }
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
//                MobclickAgent.onPageStart(getPageTitle());
            }
            return;
        }

        //若当前界面可见,调用友盟开启跳转统计
        if (mVisiable) {
            if (!Kits.Empty.check(getPageTitle())) {
                KLog.e("页面统计开始");
                startTime = System.currentTimeMillis();
//                MobclickAgent.onPageStart(getPageTitle());
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
                    DataStatisticsUtils.addData(DataStatisticsUtils.getDatasBean("", getPageTitle(), startTime + "",Constant.LEAVE));
                }
//                MobclickAgent.onPageEnd(getPageTitle());
            }
        }
    }


}
