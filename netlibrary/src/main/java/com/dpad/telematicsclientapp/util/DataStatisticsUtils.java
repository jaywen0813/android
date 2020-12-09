package com.dpad.telematicsclientapp.util;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.alibaba.fastjson.JSON;

import com.dpad.telematicsclientapp.mvp.kit.Kits;
import com.dpad.telematicsclientapp.netlibrary.MainApplicaton;
import com.dpad.telematicsclientapp.netlibrary.newapp.entity.DataStatisticsBean;
import com.dpad.telematicsclientapp.util.service.DataStaticsJobIntentService;
import com.dpad.telematicsclientapp.util.utils.Constant;
import com.dpad.telematicsclientapp.util.utils.FileUtil;
import com.google.gson.Gson;
import com.socks.library.KLog;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;



/**
 * ================================================
 * 作    者：booob
 * 版    本：1.0
 * 创建日期：2018-12-04-0004 09:45
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class DataStatisticsUtils {


    /**
     * @param description 时间描述
     * @param event       事件名称
     * @param executeTime 时间开始时间
     * <p>
     * 如果是debug模式,默认关闭埋点数据统计
     * @return
     */

    private static String path = FileUtil.getDir("data_statistics");
    private static String fileName = "event_data";
    private static DataStatisticsBean dataStatisticsBean;

    private static boolean isWriting = false;

    public static boolean hasUpload = false;//已经上传

    /**
     * 获取权限后path会变为非空,需要重新赋值
     */
    public static void setPath() {
        DataStatisticsUtils.path = FileUtil.getDir("data_statistics");
    }

    public static DataStatisticsBean.DatasBean getDatasBean(String description, String event, String executeTime, int type) {

        DataStatisticsBean.DatasBean.Builder builder = new DataStatisticsBean.DatasBean.Builder(event + getEventName(type), executeTime);
        if (!Kits.Empty.check(MainApplicaton.vin)) {
            builder.distinctId(MainApplicaton.vin);
        } else {
            builder.distinctId("");
        }

        if (!TitleToCode.getCodeByTitle(event).equals("-1")) {
            builder.description(TitleToCode.getCodeByTitle(event) + "");
        } else {
            if (!Kits.Empty.check(description)) {
                builder.description(description);
            }
        }
        DataStatisticsBean.DatasBean datasBean = new DataStatisticsBean.DatasBean(builder);
        return datasBean;
    }

    /**
     * 自带Vin的埋点
     * @param description
     * @param event
     * @param executeTime
     * @param vin
     * @param type
     * @return
     */
 public static DataStatisticsBean.DatasBean getDatasBeanWithVin(String description, String event, String executeTime,String vin, int type) {

        DataStatisticsBean.DatasBean.Builder builder = new DataStatisticsBean.DatasBean.Builder(event + getEventName(type), executeTime);
        if (!Kits.Empty.check(vin)) {
            builder.distinctId(vin);
        } else {
            builder.distinctId("");
        }

     if (!TitleToCode.getCodeByTitle(event).equals("-1")) {
            builder.description(TitleToCode.getCodeByTitle(event) + "");
        } else {
            if (!Kits.Empty.check(description)) {
                builder.description(description);
            }
        }
        DataStatisticsBean.DatasBean datasBean = new DataStatisticsBean.DatasBean(builder);
        return datasBean;
    }

    /**
     * 带描述信息的添加数据
     * @param description
     * @param event
     * @param executeTime
     * @param message
     * @param type
     * @return
     */
    public static DataStatisticsBean.DatasBean getDatasBean(String description, String event, String executeTime, String message, int type) {

        DataStatisticsBean.DatasBean.Builder builder = new DataStatisticsBean.DatasBean.Builder(event + getEventName(type), executeTime);
        if (!Kits.Empty.check(MainApplicaton.vin)) {
            builder.distinctId(MainApplicaton.vin);
        } else {
            builder.distinctId("");
        }

        if (!Kits.Empty.check(message)) {
            DataStatisticsBean.DatasBean.PropertiesBean propertiesBean = new DataStatisticsBean.DatasBean.PropertiesBean();
            propertiesBean.setMessage(message);
            builder.properties(JSON.toJSONString(propertiesBean));
        }
        if (!TitleToCode.getCodeByTitle(event).equals("-1")) {
            builder.description(TitleToCode.getCodeByTitle(event) + "");
        } else {
            if (!Kits.Empty.check(description)) {
                builder.description(description);
            }
        }
        DataStatisticsBean.DatasBean datasBean = new DataStatisticsBean.DatasBean(builder);
        return datasBean;
    }

    /**
     * 根据type判断是否进入 离开 和普通事件
     *
     * @param type
     * @return
     */
    private static String getEventName(int type) {
        String s = "";
        switch (type) {
            case Constant.GO_IN:
                s = "进入";
                break;
            case Constant.LEAVE:
                s = "离开";
                break;
            case Constant.NOMAL:
                s = "";
                break;
            default:
                break;
        }
        return s;
    }

    /**
     * @param datasBean
     */
    public static synchronized void addData(DataStatisticsBean.DatasBean datasBean) {
        if (checkStatus()) {
            return;
        }
//        if (isWriting) {//如果正在写数据,暂时不操作数据
//            KLog.e();
//            return;
//        }
        if (!Kits.Empty.check(datasBean)) {
            if (Kits.Empty.check(datasBean.getDescription())) {//如果描述为空则为脏数据,去掉
                KLog.e("description为空 脏数据");
                return;
            }
            if (Kits.Empty.check(dataStatisticsBean) || Kits.Empty.check(dataStatisticsBean.getDatas())) {

//                if (hasUpload) {
//                    dataStatisticsBean = getLocalData();
//                } else {
                dataStatisticsBean = new DataStatisticsBean();
//                }
            }

            if (Kits.Empty.check(dataStatisticsBean)) {//如果dataStatisticsBean为空说明没有文件权限
                KLog.e("没文件权限");
                return;
            }
            if (!Kits.Empty.check(dataStatisticsBean.getDatas())) {
                try {
//                    KLog.e(new Gson().toJson(dataStatisticsBean));
                    dataStatisticsBean.getDatas().add(datasBean);
                } catch (Exception e) {
                    KLog.e(e.toString());
                }
            } else {
                List<DataStatisticsBean.DatasBean> datasBeans = new ArrayList<>();
                datasBeans.add(datasBean);
                dataStatisticsBean.setDatas(datasBeans);
            }
            String s = new Gson().toJson(dataStatisticsBean);
            KLog.e("埋点数据:" + s);

//            String writePath = path + "/" + fileName;
//            writeStringToFile(s, writePath, fileName, false);
        }
    }

    /**
     * 开启服务写数据
     *
     * @param activity
     */
    public static void startServiceWrite(Activity activity) {
//        if (!hasUpload) {//如果没有上传,不写数据
//            return;
//        }
        Intent intent = new Intent();
        intent.putExtra("type", "write");
//        activity.startService(intent);
        DataStaticsJobIntentService.enqueueWork(activity, intent);
    }

    /**
     * 开始上传数据
     *
     * @param activity
     */
    public static void startSendData(Activity activity) {
        try {
            if (activity != null) {
                if (!Kits.Package.isApplicationInBackground(activity)) {
                    Intent intent = new Intent();
                    intent.putExtra("type", "upload");
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                        activity.startForegroundService(intent);
//                    } else {
//                        activity.startService(intent);
//                    }
                    DataStaticsJobIntentService.enqueueWork(activity, intent);
                }
            }
        } catch (Exception e) {
            KLog.e(e.toString());
        }


    }

    public static boolean beginWriteData() {
        isWriting = true;
        if (checkStatus()) {
            return false;
        }

        if (Kits.Empty.check(path)) {//如果path为空,说明没有获取到文件读写权限
            KLog.e("未获取到文件权限");
            isWriting = false;
            return false;
        }
        if (Kits.Empty.check(dataStatisticsBean)) {
            KLog.e("统计数据为空");
            isWriting = false;
            return false;
        }
        DataStatisticsBean myData = getLocalData();
//        KLog.e("本地埋点数据",myData.toString());
        if (Kits.Empty.check(myData.getDatas())) {
            List<DataStatisticsBean.DatasBean> datasBeans = new ArrayList<>();
            myData.setDatas(datasBeans);
        }
        myData.getDatas().addAll(dataStatisticsBean.getDatas());
        String s = new Gson().toJson(myData);


//        KLog.e("写入埋点数据:" + s);

        String writePath = path + "/" + fileName;
        boolean success = Kits.File.writeFile(writePath, s);
        if (success) {
            if (dataStatisticsBean != null) {
                if (dataStatisticsBean.getDatas() != null) {
                    dataStatisticsBean.getDatas().clear();
                }
            } else {
                dataStatisticsBean = new DataStatisticsBean();
            }

            isWriting = false;
            return true;
        }
        isWriting = false;
        return false;
//        writeStringToFile(s, writePath, fileName, false);
    }

    /**
     * 如果是debug模式暂停埋点数据统计
     *
     * @return
     */
    private static boolean checkStatus() {
//        return !BuildConfig.DEBUG;
        return false;
    }

    /**
     * 获取本地保存的数据,如果为空,则新建
     *
     * @return
     */
    public static DataStatisticsBean getLocalData() {
        String s;
        DataStatisticsBean dataStatisticsBean;
        if (Kits.Empty.check(path)) {
            return new DataStatisticsBean();
        }
        try {
//            s = Kits.File.readFile(path + "/" + fileName, fileName);
            s = readFile(path + "/" + fileName);
            if (Kits.Empty.check(s)) {
                dataStatisticsBean = new DataStatisticsBean();
            } else {
                dataStatisticsBean = new Gson().fromJson(s, DataStatisticsBean.class);
//                KLog.e(s);
            }
        } catch (Exception e) {
            KLog.e(e.toString());
            dataStatisticsBean = new DataStatisticsBean();
        }

        return dataStatisticsBean;
    }

    public static void clearData(DataStatisticsBean updateData) {

        DataStatisticsBean myData = getLocalData();
        if (!Kits.Empty.check(dataStatisticsBean) && !Kits.Empty.check(dataStatisticsBean.getDatas())) {
            myData.getDatas().addAll(dataStatisticsBean.getDatas());
        }
        for (int i = 0; i < updateData.getDatas().size(); i++) {
            myData.getDatas().remove(0);
        }

        String s = new Gson().toJson(myData);
        String writePath = path + "/" + fileName;
//        Kits.File.deleteFile(path);
        boolean success = Kits.File.writeFile(writePath, s);
        KLog.e("重新写入成功", success + "11111");
        if (!success) {
            Kits.File.deleteFile(path);
        }
    }

    /**
     * 读取文件
     *
     * @param sFileName/
     * @return
     */
    public static synchronized String readFile(String sFileName) {
        if (TextUtils.isEmpty(sFileName)) {
            return null;
        }

        final StringBuffer sDest = new StringBuffer();
        final File f = new File(sFileName);
        if (!f.exists()) {
            return null;
        }
        try {
            FileInputStream is = new FileInputStream(f);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            try {
                String data = null;
                while ((data = br.readLine()) != null) {
                    sDest.append(data);
                }
            } catch (IOException ioex) {
                KLog.e(ioex);
            } finally {
                is.close();
                is = null;
                br.close();
                br = null;
            }
        } catch (Exception ex) {
            KLog.e(ex);
        } catch (OutOfMemoryError ex) {
            KLog.e(ex);
        }
        return sDest.toString().trim();
    }


}
