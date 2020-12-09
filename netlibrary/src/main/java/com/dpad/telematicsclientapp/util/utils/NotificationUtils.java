package com.dpad.telematicsclientapp.util.utils;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.dpad.crmclientapp.android.MainApplicaton;
import com.dpad.crmclientapp.android.R;
import com.dpad.crmclientapp.android.base.basecopy.AppManager;
import com.dpad.crmclientapp.android.modules.hdym.WebViewActivity;
import com.dpad.crmclientapp.android.modules.jyfw.activity.JyDetailActivity;
import com.dpad.crmclientapp.android.modules.newapp.ui.activity.SplashActivity;
import com.dpad.crmclientapp.android.modules.xxzx.activity.SysMessageCenterActivity;
import com.dpad.crmclientapp.android.modules.xxzx.bean.ContentBean;
import com.dpad.crmclientapp.android.modules.xxzx.bean.NotifyMessageBean;
import com.dpad.crmclientapp.android.modules.yy.activity.BookingDetailActivity2;
import com.google.gson.Gson;
import com.socks.library.KLog;

import cn.droidlover.xdroidmvp.kit.Kits;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.dpad.crmclientapp.android.util.utils.Constant.MESSAGESETTING_ACTIVE;
import static com.dpad.crmclientapp.android.util.utils.Constant.MESSAGESETTING_DZWL;
import static com.dpad.crmclientapp.android.util.utils.Constant.MESSAGESETTING_ORRDER;
import static com.dpad.crmclientapp.android.util.utils.Constant.MESSAGESETTING_RESCUE;
import static com.dpad.crmclientapp.android.util.utils.Constant.MESSAGESETTING_SYSTEM;
import static com.dpad.crmclientapp.android.util.utils.Constant.MESSAGESETTING_YCKZ;
import static com.dpad.crmclientapp.android.util.utils.Constant.MESSAGESETTING_ZZFW;

/**
 * ================================================
 * 作    者：booob
 * 版    本：1.0
 * 创建日期：2019-02-14-0014 11:00
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class NotificationUtils {


    /**
     * 创建通知的id,可能收到多条消息
     */
    private int notifyId = 1;

    private static NotificationUtils notificationUtils;

    public static NotificationUtils getInstance() {
        if (notificationUtils == null) {
            notificationUtils = new NotificationUtils();
        }

        return notificationUtils;
    }


    public synchronized void sendMessage(String msg) {
        notifyId++;
        KLog.e(msg + "ManiApplication-------推送消息" + "---notifyId" + notifyId);
        try {
            NotifyMessageBean notifyMessageBean = new Gson().fromJson(msg, NotifyMessageBean.class);
            Intent intent = new Intent(AppManager.getInstance().getCurrentActivity(), SplashActivity.class);
            sendNotification(R.mipmap.desktop_launcher, intent, notifyMessageBean);
        } catch (Exception e) {
            KLog.e(e.toString() + "-----ManiApplication");
        }

    }

    /**
     * 发送消息
     *
     * @param drawRes
     * @param intent
     * @param notifyMessageBean
     */
    private void sendNotification(@DrawableRes int drawRes, @Nullable Intent intent, NotifyMessageBean notifyMessageBean) {

        NotificationManager notificationManager = (NotificationManager) MainApplicaton.getContext().getSystemService(NOTIFICATION_SERVICE);
        if (notificationManager == null) {
            return;
        }
        NotificationCompat.Builder builder;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = notificationManager.getNotificationChannel(Constant.NOTIFICATION_CHANNEL_ID);
            if (channel == null) {
                channel = new NotificationChannel(Constant.NOTIFICATION_CHANNEL_ID
                        , Constant.NOTIFICATION_CHANNEL_NAME
                        , NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription(Constant.NOTIFICATION_CHANNEL_DESCRIPTION);
                channel.enableLights(true);
                channel.enableVibration(true);
                channel.setShowBadge(true);
                channel.canBypassDnd();
                channel.setLightColor(Color.GREEN);
                notificationManager.createNotificationChannel(channel);
            }
            builder = new NotificationCompat.Builder(AppManager.getInstance().getCurrentActivity(), Constant.NOTIFICATION_CHANNEL_ID);
        } else {
            builder = new NotificationCompat.Builder(AppManager.getInstance().getCurrentActivity());
            builder.setPriority(Notification.PRIORITY_HIGH);
        }
        Gson gson = new Gson();
        ContentBean contentBean = gson.fromJson(notifyMessageBean.getContent(), ContentBean.class);
        if (Kits.Empty.check(contentBean)) {
            return;
        } else {
            switch (contentBean.getType()) {
                //系统消息
                case MESSAGESETTING_SYSTEM:
                    //电子围栏
                case MESSAGESETTING_DZWL:
                    //增值服务
                case MESSAGESETTING_ZZFW:
                    //远控提醒
                case MESSAGESETTING_YCKZ:

                    intent = new Intent(MainApplicaton.getContext(), SysMessageCenterActivity.class);
                    intent.putExtra("type", contentBean.getType());
//                    intent.putExtra("needGoHome",true);
//                    if (!SystemUtil.isAppIsInBackground(AppManager.getInstance().getCurrentActivity())) {
//                        KLog.e("在后台");
//                        intent = new Intent(Intent.ACTION_MAIN);
//                        intent.addCategory(Intent.CATEGORY_LAUNCHER);
//                        intent.setClass(AppManager.getInstance().getCurrentActivity(), SysMessageCenterActivity.class);
//                        intent.putExtra("type", contentBean.getType());
//                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
//                    } else {
//                        KLog.e("在前台");
//                        intent = new Intent(AppManager.getInstance().getCurrentActivity(), WelcomeActivity.class);
//                    }
                    break;
                //预约消息
                case MESSAGESETTING_ORRDER:
                    //如果已登录
//                    if (MainApplicaton.sIsLogin) {
                    intent = new Intent(MainApplicaton.getContext(), BookingDetailActivity2.class);
                    intent.putExtra("BespeakInfoHistoryVO", contentBean.getId());
//                    } else {//未登录
//                        intent = new Intent(AppManager.getInstance().getCurrentActivity(), LoginActivity.class);
//                    }
                    break;
                //救援消息
                case MESSAGESETTING_RESCUE:
                    //已登录
//                    if (MainApplicaton.sIsLogin) {
                    intent = new Intent(MainApplicaton.getContext(), JyDetailActivity.class);
                    intent.putExtra("id", contentBean.getId());
                    //未登录
//                    } else {
//                        intent = new Intent(AppManager.getInstance().getCurrentActivity(), LoginActivity.class);
//                    }
                    break;
                //活动消息
                case MESSAGESETTING_ACTIVE:
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("url_tag", contentBean.getLink());
                    intent = new Intent(MainApplicaton.getContext(), WebViewActivity.class);
                    intent.putExtra("bundle", bundle);
                    break;
                //远控返回的
                case "rc":
                    Intent intent1 = new Intent("android.intent.action.RC");
                    intent1.putExtra("type", contentBean.getType());
                    intent1.putExtra("title", contentBean.getTitle());
                    intent1.putExtra("content", contentBean.getContent());
                    intent1.putExtra("requestId", contentBean.getRequestId());
                    intent1.putExtra("callBackType",contentBean.getCallBackType());
                    Log.e("远控测试推送返回11111",contentBean.getRequestId());
                    LocalBroadcastManager.getInstance(MainApplicaton.getContext()).sendBroadcast(intent1);
                    return;
                default:
                    intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_LAUNCHER);
                    intent.setClass(MainApplicaton.getContext(), SplashActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                    break;
            }
        }
        PendingIntent pendingIntent;
        pendingIntent = PendingIntent.getActivity(MainApplicaton.getContext(), notifyId, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        //设置默认的声音和震动
        builder.setDefaults(Notification.DEFAULT_ALL)
                .setSmallIcon(R.mipmap.desktop_launcher)
                .setAutoCancel(true)
                .setTicker(notifyMessageBean.getTitle())
                .setWhen(System.currentTimeMillis())
                .setLargeIcon(BitmapFactory.decodeResource(MainApplicaton.getContext().getResources(), drawRes))
                .setContentTitle(notifyMessageBean.getTitle())
                .setContentText(contentBean.getContent()).
                setContentIntent(pendingIntent);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            builder.setPriority(NotificationManager.IMPORTANCE_HIGH);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            builder.setColor(MainApplicaton.getContext().getResources().getColor(R.color.colorPrimary));
        }
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT_WATCH) {
//            builder.setGroupSummary(true);
//            builder.setGroup(Constant.NOTIFY_GROUP);
//        }
        Notification notification = builder.build();
//        if (null == intent) {
        notification.flags |= Notification.FLAG_AUTO_CANCEL;
//        }
        notificationManager.notify(notifyId, notification);
    }
}
