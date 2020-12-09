package com.dpad.telematicsclientapp.util.service;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.JobIntentService;


import com.dpad.telematicsclientapp.mvp.kit.Kits;
import com.dpad.telematicsclientapp.netlibrary.http.NetInstance;
import com.dpad.telematicsclientapp.netlibrary.newapp.entity.DataStatisticsBean;
import com.dpad.telematicsclientapp.netlibrary.newapp.entity.PostDriveBean;
import com.dpad.telematicsclientapp.util.DataStatisticsUtils;
import com.dpad.telematicsclientapp.util.utils.ParameterUtils;
import com.socks.library.KLog;

import java.util.SortedMap;
import java.util.TreeMap;


import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * ================================================
 * 作    者：wenbody
 * 版    本：1.0
 * 创建日期：2019/10/29 14:34
 * 描    述: 原来使用dataStaticsService,由于Android8.0以上后台服务在通知栏会显示"**程序正在运行",更换为jobIntentService
 * 修订历史：
 * ================================================
 */
public class DataStaticsJobIntentService extends JobIntentService {

    private CompositeDisposable mCompositeDisposable;

    static final int JOB_ID = 1000;

    /**
     * Convenience method for enqueuing work in to this service.
     */
    public static void enqueueWork(Context context, Intent work) {
        enqueueWork(context, DataStaticsJobIntentService.class, JOB_ID, work);
    }

    @Override
    protected void onHandleWork(@NonNull Intent intent) {
        String type = "";
        try {
            type = intent.getStringExtra("type");
        } catch (Exception e) {
            KLog.e(e.toString());
        }
        if (Kits.Empty.check(type)) {
            KLog.e("数据异常");
        } else {
            if (type.equals("write")) {
                Observable<Boolean> observable = Observable.create(new ObservableOnSubscribe<Boolean>() {
                    @Override
                    public void subscribe(ObservableEmitter<Boolean> e) throws Exception {
                        e.onNext(DataStatisticsUtils.beginWriteData());
                    }
                });

                Observer<Boolean> observer = new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean value) {
                        if (value) {
                            KLog.e("写入成功");
                        } else {
                            KLog.e("写入失败");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e("写入失败:" + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        KLog.e("写入成功");
                    }
                };
                observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(observer);

            } else {//上传
                DataStatisticsBean dataStatisticsBean = DataStatisticsUtils.getLocalData();
                if (!Kits.Empty.check(dataStatisticsBean) && !Kits.Empty.check(dataStatisticsBean.getDatas()) && dataStatisticsBean.getDatas().size() > 0) {
                    sendData(dataStatisticsBean);
                } else {
                    DataStatisticsUtils.hasUpload = true;
                }
            }
        }
    }


    private void addDisposable(Disposable disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = new CompositeDisposable();
        } else {
            mCompositeDisposable.clear();
        }
        mCompositeDisposable.add(disposable);
    }



    /**
     * 清除本地数据
     */
    private void clearLoaclData(DataStatisticsBean dataStatisticsBean) {
        KLog.e("清除统计数据");
        DataStatisticsUtils.clearData(dataStatisticsBean);
//        DataStatisticsUtils.getLocalData().getDatas().removeAll(dataStatisticsBean.getDatas());
//        dataStatisticsBean.getDatas().clear();
    }

    private void sendData(DataStatisticsBean dataStatisticsBean) {
        KLog.e("开始上传埋点数据");
        SortedMap<String, Object> sortedMap = new TreeMap<>();
        sortedMap.put("datas", dataStatisticsBean.getDatas());
        NetInstance.getDataEventsService().sendData(ParameterUtils.getHeaser(sortedMap), ParameterUtils.getJsonBody(sortedMap)).subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<PostDriveBean>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                        addDisposable(d);
                    }

                    @Override
                    public void onNext(PostDriveBean value) {
                        if (value.getCode().equals("00000")) {
                            KLog.e("上传成功");
                            clearLoaclData(dataStatisticsBean);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e(e.toString());
                    }

                    @Override
                    public void onComplete() {
                        DataStatisticsUtils.hasUpload = true;
                    }
                });
    }


}
