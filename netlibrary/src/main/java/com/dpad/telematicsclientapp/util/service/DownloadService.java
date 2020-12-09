package com.dpad.telematicsclientapp.util.service;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.FileProvider;

import com.daimajia.numberprogressbar.NumberProgressBar;
import com.dpad.telematicsclientapp.netlibrary.MainApplicaton;
import com.dpad.telematicsclientapp.netlibrary.R;
import com.dpad.telematicsclientapp.netlibrary.base.basecopy.AppManager;
import com.dpad.telematicsclientapp.util.utils.DialogUtil;
import com.dpad.telematicsclientapp.util.utils.FileUtil;
import com.dpad.telematicsclientapp.util.utils.T;
import com.dpad.telematicsclientapp.util.utils.UIUtils;
import com.dpad.telematicsclientapp.weiget.DownDialog;
import com.socks.library.KLog;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @创建者 booobdai.
 * @创建时间 2017/10/27  13:44.
 * @描述 ${下载服务}.
 */
public class DownloadService extends Service {

    private static final String TAG = DownloadService.class.getSimpleName();

    public static final String INTENT_DOWNLOAD_URL = "intent_download_url";
    public static final String INTENT_DOWNLOAD_FILE_NAME = "intent_download_file_name";
    public static final String INTENT_DOWNLOAD_FORCEUPDATE = "intent_download_forceupdate";

    private NotificationManager mNotificationManager;
    //    private NotificationCompat mNotification;
    private NotificationCompat.Builder mBuilder;
    private String mDownloadUrl;
    private String mFileName;
    private boolean forceUpdate;
    private DownloadTask downloadTask;
    private NumberProgressBar numberProgressBar;
    private DownDialog downDialog;

    @Override
    public void onCreate() {
        KLog.e(TAG, "DownloadService onCreate()");

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            mNotificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
            NotificationChannel channel = new NotificationChannel("channel_id", "channel_name", NotificationManager.IMPORTANCE_LOW);
            channel.setSound(null, null);
            channel.enableVibration(false);
            if (mNotificationManager != null) {
                mNotificationManager.createNotificationChannel(channel);
            }
        } else {
            mNotificationManager = (NotificationManager) getSystemService(Service.NOTIFICATION_SERVICE);
        }
        if (mNotificationManager != null) {
            mNotificationManager.cancelAll();
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        KLog.e(TAG, "DownloadService onStartCommand()");
        if (intent != null) {
            mDownloadUrl = intent.getStringExtra(INTENT_DOWNLOAD_URL);
            mFileName = intent.getStringExtra(INTENT_DOWNLOAD_FILE_NAME);
            forceUpdate = intent.getBooleanExtra(INTENT_DOWNLOAD_FORCEUPDATE, false);

            if (downloadTask == null) {
                if (forceUpdate) {
                    downDialog = DialogUtil.showDownDialog(AppManager.getInstance().getCurrentActivity(), new DownDialog.OnCloseListener() {
                        @Override
                        public void onClose(Dialog dialog, boolean confirm) {
                            mNotificationManager.cancelAll();
                            downloadTask.cancel(true);
                            MainApplicaton.isDownIng = false;
                            AppManager.getInstance().exitApp(AppManager.getInstance().getCurrentActivity());

                        }
                    });
                    numberProgressBar = downDialog.getNumberProgressBar();
                } else {
                    T.showToastSafe("开始下载");
                }
                MainApplicaton.isDownIng = true;
                mNotificationManager.cancel(11);//取消掉安装的notifycation(可能二次下载)
                downloadTask = new DownloadTask();
                downloadTask.execute();
            }

        }
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 异步下载任务
     */
    public class DownloadTask extends AsyncTask<String, Integer, Integer> {

        @SuppressLint("InlinedApi")
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                mBuilder = new NotificationCompat.Builder(DownloadService.this, "channel_id")
                        .setSmallIcon(R.mipmap.desktop_launcher)
                        .setContentTitle(UIUtils.getString(R.string.app_name))
                        .setPriority(Notification.PRIORITY_MAX)
                        .setContentText(UIUtils.getString(R.string.download_downloading))
                        .setAutoCancel(true);
//                        .setContentInfo("0/100");
//                mNotification = mBuilder.build();
            } else {
                mBuilder = new NotificationCompat.Builder(DownloadService.this)
                        .setSmallIcon(R.mipmap.desktop_launcher)
                        .setContentTitle(UIUtils.getString(R.string.app_name))
                        .setAutoCancel(true)
                        .setPriority(Notification.PRIORITY_MAX)
                        .setContentText(UIUtils.getString(R.string.download_downloading))
                        .setContentInfo("0%");
//                mNotification = mBuilder.build();
            }

            mNotificationManager.notify(10, mBuilder.build());
        }

        @Override
        protected Integer doInBackground(String... params) {
            HttpURLConnection con = null;
            InputStream is = null;
            OutputStream os = null;
            try {
                URL url = new URL(mDownloadUrl);
                con = (HttpURLConnection) url.openConnection();
                con.setConnectTimeout(5 * 1000);  //设置超时时间
                if (con.getResponseCode() == 200) { //判断是否连接成功
                    int fileLength = con.getContentLength();
                    is = con.getInputStream();    //获取输入
                    os = new FileOutputStream(new File(FileUtil.getDownloadDir(), mFileName));
                    byte[] buffer = new byte[1024 * 1024 * 10];
                    long total = 0;
                    int count;
                    int pro1 = 0;
                    int pro2 = 0;
                    while ((count = is.read(buffer)) != -1) {
                        total += count;
                        if (fileLength > 0)
                            pro1 = (int) (total * 100 / fileLength);  //传递进度（注意顺序）
                        if (pro1 != pro2)
                            publishProgress(pro2 = pro1);
                        os.write(buffer, 0, count);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                    if (os != null) {
                        os.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (con != null) {
                    con.disconnect();
                }
            }
            return 1;
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            if (result == 1) {
                if (downDialog != null && downDialog.isShowing()) {
                    downDialog.dismiss();
                }
                T.showToastSafe(R.string.download_succeed);
                MainApplicaton.isDownIng = false;
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            KLog.d(TAG, "onProgressUpdate " + values[0]);
            super.onProgressUpdate(values);

            if (numberProgressBar != null) {
                numberProgressBar.setProgress(values[0]);
            }
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
                mBuilder.setProgress(100, values[0], false)
                        .setContentInfo(values[0] + "%");
//            mNotification = mBuilder.build();
                mNotificationManager.notify(10, mBuilder.build());
            } else {
                mBuilder.setContentText("正在下载" + (values[0] + "%"));
                mNotificationManager.notify(10, mBuilder.build());
            }
            if (values[0] == 100) {
                //下载完成后点击安装
                File file = new File(FileUtil.getDownloadDir(), mFileName);
                T.showToastSafeOk("下载完成");
                KLog.e(FileUtil.getDownloadDir() + "文件下载目录");
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                //适配7.0安装apk
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    //参数1 上下文, 参数2 Provider主机地址 和配置文件中保持一致   参数3  共享的文件
                    Uri apkUri = FileProvider.getUriForFile(DownloadService.this, "com.dpad.telematicsclientapp.android.fileprovider", file);
                    //添加这一句表示对目标应用临时授权该Uri所代表的文件
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                } else {
                    intent.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
                }


                PendingIntent pendingIntent = PendingIntent.getActivity(DownloadService.this, 0, intent, PendingIntent
                        .FLAG_UPDATE_CURRENT);
                mBuilder.setContentTitle(UIUtils.getString(R.string.app_name))
                        .setContentText(UIUtils.getString(R.string.download_succeed_install))
                        .setContentIntent(pendingIntent);
                mNotificationManager.cancel(10);
                mNotificationManager.notify(11, mBuilder.build());
                if (forceUpdate) {
                    startActivity(intent);
                    AppManager.getInstance().lightExitApp(MainApplicaton.getContext());
                }
                downloadTask = null;
            }
        }
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        MainApplicaton.isDownIng = false;
    }
}
