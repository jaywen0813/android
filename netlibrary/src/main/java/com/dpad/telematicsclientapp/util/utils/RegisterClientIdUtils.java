package com.dpad.telematicsclientapp.util.utils;

import android.util.Log;


import com.dpad.telematicsclientapp.library.util.RxSchedulerUtils;
import com.dpad.telematicsclientapp.mvp.kit.Kits;
import com.dpad.telematicsclientapp.netlibrary.AppConstants;
import com.dpad.telematicsclientapp.netlibrary.MainApplicaton;
import com.dpad.telematicsclientapp.netlibrary.entity.CuscResult;
import com.dpad.telematicsclientapp.netlibrary.newapp.repository.BingdingClientIdRepository;
import com.socks.library.KLog;

import java.util.SortedMap;
import java.util.TreeMap;


import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * ================================================
 * 作    者：booob
 * 版    本：1.0
 * 创建日期：2018-06-04-0004 20:03
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class RegisterClientIdUtils {

    public static void bindingClient(String clientid) {
        CompositeSubscription subscriptions = new CompositeSubscription();
        SortedMap<String, String> paramsMap = new TreeMap<>();
        String userName = MainApplicaton.LOGINRESULTVO.getUserName();
        String cid = clientid;
        String brandCode = AppConstants.BRANDCODE;
        String osType = "00";//00安卓,01 ios
        String appVersionNo = Kits.Package.getVersionCode(MainApplicaton.getContext()) + "";
        paramsMap.put("userName", userName);
        paramsMap.put("cid", cid);
        paramsMap.put("brandCode", brandCode);
        paramsMap.put("osType", osType);
        paramsMap.put("appVersionNo", appVersionNo);
        Subscription subscription = BingdingClientIdRepository.getInstance().StringService(paramsMap).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).compose(RxSchedulerUtils.normalSchedulersTransformer()).subscribe(new Observer<CuscResult<String>>() {
                    @Override
                    public void onNext(CuscResult<String> vo) {
                        if (vo.getCode().equals("00000")) {
                            KLog.e("clientId注册成功");
                        }
                    }

                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        KLog.e("clientId注册失败" + e.toString());
                    }
                });
        subscriptions.add(subscription);
    }
}
