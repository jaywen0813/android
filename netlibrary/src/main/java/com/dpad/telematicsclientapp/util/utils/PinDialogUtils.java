package com.dpad.telematicsclientapp.util.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;

import com.dpad.crmclientapp.android.MainApplicaton;
import com.dpad.crmclientapp.android.data.http.EncryptUtils.SecretResult;
import com.dpad.crmclientapp.android.data.http.NetInstance;
import com.dpad.crmclientapp.android.modules.newapp.ui.activity.HomeActivity;
import com.dpad.crmclientapp.android.modules.remotecontrol.activity.ConfirmQuestionActivity;
import com.dpad.crmclientapp.android.modules.remotecontrol.bean.Utils;
import com.dpad.crmclientapp.android.util.FingerprintIdentificationUtil;
import com.dpad.crmclientapp.android.util.MD5;
import com.dpad.crmclientapp.android.widget.PinDialog;
import com.dpad.crmclientapp.android.widget.PinInputView;
import com.dpad.telematicsclientapp.netlibrary.MainApplicaton;
import com.dpad.telematicsclientapp.weiget.PinDialog;
import com.socks.library.KLog;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.SortedMap;
import java.util.TreeMap;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.HttpException;

/**
 * ================================================
 * 作    者：wenbody
 * 版    本：1.0
 * 创建日期：2019/7/29 11:04
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class PinDialogUtils {


    private static Activity context;

    private PinDialog pinDialog;

    private boolean isShowIng = false;

    public boolean isShowIng() {
        return isShowIng;
    }

    public void setShowIng(boolean showIng) {
        isShowIng = showIng;
    }

    private CompositeDisposable mCompositeDisposable = new CompositeDisposable();

    private static PinDialogUtils pinDialogUtils = null;

    public static PinDialogUtils getPinDialogUtils(Activity mContext) {
        context = mContext;
        if (pinDialogUtils == null) {
            synchronized (PinDialogUtils.class) {
                if (pinDialogUtils == null) {
                    pinDialogUtils = new PinDialogUtils();
                }
            }

        }

        return pinDialogUtils;
    }

    public void checkFingerPrint() {
        if (Utils.INSTANCE.checkStatus() && !isShowIng) {
            FingerprintIdentificationUtil.Companion.startInit((FragmentActivity) context, new FingerprintIdentificationUtil.Companion.FingerPrintErrorListener() {

                @Override
                public void fingerCancel() {
                    context.startActivity(new Intent(context, HomeActivity.class));
                }


                @Override
                public void fingerFail() {
                    checkPin();
                }

                @Override
                public void fingerSuccess() {
                    MainApplicaton.currentTouchTime = System.currentTimeMillis();
                }

            });
        } else {
            checkPin();
        }
    }

    private void checkPin() {
        if (pinDialog != null && pinDialog.isShowing()) {
            pinDialog.dismiss();
            pinDialog = null;
        }
        isShowIng = true;
        pinDialog = DialogUtil.showPinDialog(context, new PinDialog.OnCloseListener() {
            @Override
            public void onClose(Dialog dialog, boolean confirm) {
                if (!confirm) {
                    context.startActivity(new Intent(context, HomeActivity.class));
                }
            }
        });
        pinDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                isShowIng = false;
            }
        });

        pinDialog.getPinInputView().setOnFullListener(new PinInputView.OnFullListener() {
            @Override
            public void OnFull(String value) {
                SortedMap<String, Object> sortedMap = new TreeMap<>();
                sortedMap.put("pinCode", MD5.GetMD5Code(value));
                try {
                    checkPin(sortedMap);
                } catch (Exception e) {
                    KLog.e(e.toString());
                }
            }
        });

        pinDialog.getPinInputView().setOnForgetListener(new PinInputView.OnForgetListener() {
            @Override
            public void onForget() {//
                //跳转到忘记密码
                Intent intent = new Intent(context, ConfirmQuestionActivity.class);
                intent.putExtra("type", "dialog");
                context.startActivityForResult(intent, Constant.FORGET_PIN_REQUEST);
                pinDialog.dismiss();
            }
        });
    }

    public void addDisposable(Disposable disposable) {
        if (null == mCompositeDisposable) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

    /**
     * 校验pin码是否正确
     *
     * @param sortedMap
     */
    public void checkPin(SortedMap<String, Object> sortedMap) {
        NetInstance.getRemoteControlService().checkPin(ParameterUtils.getHeaser(sortedMap), ParameterUtils.getSecretJsonBody(sortedMap)).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(new Observer<SecretResult>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(SecretResult value) {
                        if (value.getCode().equals("00000")) {
                            if (pinDialog != null) {
                                MainApplicaton.currentTouchTime = System.currentTimeMillis();
                                pinDialog.dismiss();
                            }
                        } else {
                            if (pinDialog != null) {
                                pinDialog.getPinInputView().Error();
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        if (throwable instanceof HttpException || throwable instanceof ConnectException || throwable instanceof SocketTimeoutException) {
                            T.showToastSafeError("服务器开小差了，请稍后再试~");
                        } else {
                            context.startActivity(new Intent(context, HomeActivity.class));
                            KLog.e(throwable.toString());
                        }
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }


}
