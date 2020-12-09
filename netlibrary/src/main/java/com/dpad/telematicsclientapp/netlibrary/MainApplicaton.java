package com.dpad.telematicsclientapp.netlibrary;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.multidex.MultiDex;

import com.amap.api.maps.model.LatLng;
import com.amap.api.navi.services.search.model.LatLonPoint;








import com.dpad.telematicsclientapp.mvp.kit.Kits;
import com.dpad.telematicsclientapp.netlibrary.base.basecopy.AppManager;
import com.dpad.telematicsclientapp.netlibrary.newapp.entity.LoginResultVO;
import com.dpad.telematicsclientapp.util.DataStatisticsUtils;
import com.dpad.telematicsclientapp.util.PreferenceUtils;
import com.dpad.telematicsclientapp.util.utils.Constant;
import com.dpad.telematicsclientapp.util.utils.NetUtil;
import com.dpad.telematicsclientapp.util.utils.PinDialogUtils;
import com.socks.library.KLog;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;




/**
 * Created by vigss on 2018/3/13.
 */

public class MainApplicaton extends Application {
    private static MainApplicaton mainApplicaton;
    public static LoginResultVO LOGINRESULTVO = new LoginResultVO();
    public static String ZSWD_TAG = "1";

    public static MainApplicaton getInstance() {
        return mainApplicaton;
    }

    public static HashMap<String, String> urlMap = new HashMap<>();

    public static Context context;
    private static Thread mainThread;
    private static Handler mainThreadHandler;
    public static boolean isDownIng = false;//正在下载

    public static String APP_ID = "wxf52b888d7d85a95f";
    private LatLonPoint latLonPoint;//存储搜索的时候的经纬度的
    private String searchCity;//存储搜索的时候的城市名
    private LatLng seatchLatlng;//存储搜索的结果 然后进行搜周边的时候的中心点的经纬度


    public LatLng getSeatchLatlng() {
        return seatchLatlng;
    }

    public void setSeatchLatlng(LatLng seatchLatlng) {
        this.seatchLatlng = seatchLatlng;
    }

    public String getSearchCity() {
        return searchCity;
    }

    public void setSearchCity(String searchCity) {
        this.searchCity = searchCity;
    }

    public LatLonPoint getLatLonPoint() {
        return latLonPoint;
    }

    public void setLatLonPoint(LatLonPoint latLonPoint) {
        this.latLonPoint = latLonPoint;
    }

    //用户是否已登录
    public static boolean sIsLogin;

    public static boolean isTcar;//是否有T车

    public static String vin = "";//vin


    public static String saleSubmodelId = "";//车型

    public static String plateNumber = "";//车牌号

    public static String collectionId;//收藏id

    public static String clientId = "";

    public static String city = ""; //当没有开启定位的时候，用户选择切换的城市，目前默认武汉

    public static String citycode = "";//当没有开启定位的时候，用户选择切换的城市cityCode，目前默认武汉

    public static String userVehType = "0";//用户和车辆类型（0：游客1:电动T车2：混动T车3：燃油T车4：非T车）

    public static String burialPointUserType = "1";//1、潜客,2、非T车主,3、T车主,4、远控车主

    public static boolean checkDelete;//行程记录用来记录是否勾选

    public static boolean checkGuiJi;//关闭轨迹的时候，用来记录是否勾选

    public static String carTypeId = "";//下载完成以后，用来记录当前下载完成的是哪个车型，避免第二次重复下载

    public static String downUrl="";//下载完成以后，用来记录是哪个url，当相同的url的时候，不用再下载

    /**
     * activity 的个数
     */
    private int activityCount = 0;

    /**
     * app 是否在后台
     */
    private boolean isBackground = false;
    /**
     * 是否需要显示PIN弹框
     */
    private static boolean needShowPinDialog = false;

    /**
     * 是否是体验App
     */
    private static boolean isTryApp = false;
    /**
     * 当前的触摸时间,如果触摸时间大于
     */
    public static long currentTouchTime = 0;

    public static void setNeedShowPinDialog(boolean needShowPinDialog) {
        MainApplicaton.needShowPinDialog = needShowPinDialog;
    }

    public static void setIsTryApp(boolean isRryApp) {
        MainApplicaton.isTryApp = isRryApp;
    }

    public static boolean isNeedShowPinDialog() {
        return needShowPinDialog;
    }


    public static int pageType = 0;//页面分类（0：首页，1：用户中心，2：车况报告，3地图维保预约）


    public static String getDownUrl() {
        if (Kits.Empty.check(downUrl)) {
            downUrl = PreferenceUtils.getInstance(MainApplicaton.getContext()).getString("downUrl");
        }
        return downUrl;
    }

    public static void setDownUrl(String downUrl) {
        if (Kits.Empty.check(downUrl)) {
            return;
        }
        MainApplicaton.downUrl = downUrl;
        PreferenceUtils.getInstance(MainApplicaton.getContext()).setString("downUrl", downUrl);
    }

    public static String getCarTypeId() {
        if (Kits.Empty.check(carTypeId)) {
            carTypeId = PreferenceUtils.getInstance(MainApplicaton.getContext()).getString("carTypeId");
        }
        return carTypeId;
    }

    public static void setCarTypeId(String carTypeId) {
        if (Kits.Empty.check(carTypeId)) {
            return;
        }
        MainApplicaton.carTypeId = carTypeId;
        PreferenceUtils.getInstance(MainApplicaton.getContext()).setString("carTypeId", carTypeId);
    }


    public static String getCitycode() {
        if (Kits.Empty.check(citycode)) {
            citycode = PreferenceUtils.getInstance(MainApplicaton.getContext()).getString("citycode");
        }
        return citycode;
    }

    public static void setCitycode(String citycode) {
        if (Kits.Empty.check(citycode)) {
            return;
        }
        MainApplicaton.citycode = citycode;
        PreferenceUtils.getInstance(MainApplicaton.getContext()).setString("citycode", citycode);
    }

    public static boolean isCheckDelete() {
        if (Kits.Empty.check(checkDelete)) {
            checkDelete = PreferenceUtils.getInstance(MainApplicaton.getContext()).getBoolean("checkDelete");
        }
        return checkDelete;
    }

    public static void setCheckDelete(boolean checkDelete) {
        if (Kits.Empty.check(checkDelete)) {
            return;
        }
        MainApplicaton.checkDelete = checkDelete;
        PreferenceUtils.getInstance(MainApplicaton.getContext()).setBoolean("checkDelete", checkDelete);
    }

    public static boolean isCheckGuiJi() {
        if (Kits.Empty.check(checkGuiJi)) {
            checkGuiJi = PreferenceUtils.getInstance(MainApplicaton.getContext()).getBoolean("checkGuiJi");
        }
        return checkGuiJi;
    }

    public static void setCheckGuiJi(boolean checkGuiJi) {
        if (Kits.Empty.check(checkGuiJi)) {
            return;
        }
        MainApplicaton.checkGuiJi = checkGuiJi;
        PreferenceUtils.getInstance(MainApplicaton.getContext()).setBoolean("checkGuiJi", checkGuiJi);
    }


    public static String getCity() {
        if (Kits.Empty.check(city)) {
            city = PreferenceUtils.getInstance(MainApplicaton.getContext()).getString("city");
        }
        return city;
    }

    public static void setCity(String city) {
        if (Kits.Empty.check(city)) {
            return;
        }
        MainApplicaton.city = city;
        PreferenceUtils.getInstance(MainApplicaton.getContext()).setString("city", city);
    }


    public static String getClientId() {
        if (clientId.isEmpty()) {
            String s = PreferenceUtils.getInstance(MainApplicaton.getContext()).getString(Constant.KEY_CLIENTID);
            clientId = Kits.Empty.check(s) ? clientId : s;//如果缓存中clientId为空,则取clientId
        }
        return clientId;
    }

    public static void setClientId(String clientId) {
        MainApplicaton.clientId = clientId;
        PreferenceUtils.getInstance(MainApplicaton.getContext()).setString(Constant.KEY_CLIENTID, clientId);
    }

    public static String getPlateNumber() {
        return plateNumber;
    }

    public static void setPlateNumber(String plateNumber) {
        MainApplicaton.plateNumber = plateNumber;
    }

    public static String getVin() {
        if (Kits.Empty.check(vin)) {
            String s = PreferenceUtils.getInstance(MainApplicaton.getContext()).getString(Constant.KEY_VIN);
            vin = Kits.Empty.check(s) ? "" : s;
        }
        return vin;
    }

    public static void setVin(String vin) {
        MainApplicaton.vin = vin;
//        if (Kits.Empty.check(vin)) {
//            return;
//        }
        PreferenceUtils.getInstance(MainApplicaton.getContext()).setString(Constant.KEY_VIN, vin);
    }

    public static String getSaleSubmodelId() {
        if (Kits.Empty.check(saleSubmodelId)) {
            String s = PreferenceUtils.getInstance(MainApplicaton.getContext()).getString(Constant.KEY_SALESUBMODELID);
            saleSubmodelId = Kits.Empty.check(s) ? "" : s;
        }
//        Log.e("AAAAA","get"+saleSubmodelId);
        return saleSubmodelId;
    }

    public static void setSaleSubmodelId(String saleSubmodelId) {

//        Log.e("AAAAA","set"+saleSubmodelId);
//        MainApplicaton.saleSubmodelId = "1pwta4";
//        MainApplicaton.saleSubmodelId="";
        MainApplicaton.saleSubmodelId = saleSubmodelId;
        PreferenceUtils.getInstance(MainApplicaton.getContext()).setString(Constant.KEY_SALESUBMODELID, saleSubmodelId);
    }

    public static String getBurialPointUserType() {
        if (Kits.Empty.check(burialPointUserType)) {
            String s = PreferenceUtils.getInstance(MainApplicaton.getContext()).getString(Constant.KEY_BURIALPOINTUSERTYPE);
            burialPointUserType = Kits.Empty.check(s) ? "" : s;
        }
        return burialPointUserType;
    }

    public static void setBurialPointUserType(String burialPointUserType) {
        if (Kits.Empty.check(burialPointUserType)) {
            return;
        }
        MainApplicaton.burialPointUserType = burialPointUserType;
        PreferenceUtils.getInstance(MainApplicaton.getContext()).setString(Constant.KEY_BURIALPOINTUSERTYPE, burialPointUserType);
    }

    public static String getUserVehType() {
        if (Kits.Empty.check(userVehType)) {
            String s = PreferenceUtils.getInstance(MainApplicaton.getContext()).getString(Constant.KEY_USERVEHTYPE);
            userVehType = Kits.Empty.check(s) ? "" : s;
        }
        return userVehType;
    }

    public static void setUserVehType(String userVehType) {
        if (Kits.Empty.check(userVehType)) {
            MainApplicaton.userVehType = "0";
            return;
        }
        MainApplicaton.userVehType = userVehType;
        PreferenceUtils.getInstance(MainApplicaton.getContext()).setString(Constant.KEY_USERVEHTYPE, userVehType);
    }


    public static int mNetWorkState;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    //wx3f17c5621b352f6c
    {
        PlatformConfig.setWeixin("wx1b637b48768c4f6a", "f6d9a7bdd45fe445fe376614fcf38d91");
//        PlatformConfig.setQQZone("1104974508", "zXUssOQB7jBvnbWx");//原始的非tapp
        PlatformConfig.setQQZone("1108339242", "n58uLRdWGN00jkRi");//t app的key
    }


    @Override
    public void onCreate() {
        super.onCreate();
        mainApplicaton = this;
        context = getApplicationContext();
        mainThreadHandler = new Handler();
        mainThread = Thread.currentThread();
        //存储activity的集合
//        mActivityList = new ArrayList<>();

        registerActivityLifecycleCallbacks(activityLifecycleCallbacks);
        initThirdService();
//        closeAndroidPDialog();

    }


    public void initThirdService() {
//        initActivityLifecycle();
        UMConfigure.setLogEnabled(true);
        UMConfigure.init(this, "5c99951a3fc1958391000778"
                , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
//        UMConfigure.setLogEnabled(BuildConfig.DEBUG);

//        MobclickAgent.openActivityDurationTrack(false);//禁止默认的页面统计方式
        //初始化Klog
        KLog.init(BuildConfig.DEBUG);
        UMShareAPI.get(context);

        /**
         *  @params appContext
         *  @params siteid: 企业id，即企业唯一标识。格式示例：kf_9979【必填】  siteid:sn_1000
         *  @params sdkkey: 企业key，即小能通行密钥【必填】
         *  @return int  0 表示初始化成功, 其他值请查看错误码
         */
//        Ntalker.getBaseInstance().initSDK(context, "sn_1000", "alsdlakjs");

    }

    private void closeAndroidPDialog() {//去掉在Android P上的提醒弹窗 （Detected problems with API compatibility(visit g.co/dev/appcompat for more info)
        try {
            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
            declaredConstructor.setAccessible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            Class cls = Class.forName("android.app.ActivityThread");
            Method declaredMethod = cls.getDeclaredMethod("currentActivityThread");
            declaredMethod.setAccessible(true);
            Object activityThread = declaredMethod.invoke(null);
            Field mHiddenApiWarningShown = cls.getDeclaredField("mHiddenApiWarningShown");
            mHiddenApiWarningShown.setAccessible(true);
            mHiddenApiWarningShown.setBoolean(activityThread, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Context getContext() {
        return context;
    }

    public static Thread getMainThread() {
        return mainThread;
    }


    public static Handler getMainThreadHandler() {
        return mainThreadHandler;
    }

    public void initData() {
        mNetWorkState = NetUtil.getNetworkState(this);
    }

    ActivityLifecycleCallbacks activityLifecycleCallbacks = new ActivityLifecycleCallbacks() {
        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityStarted(Activity activity) {
            if (isBackground && needShowPinDialog && !isTryApp) {
                PinDialogUtils.getPinDialogUtils(AppManager.getInstance().getCurrentActivity()).checkFingerPrint();
            }
            isBackground = false;
            activityCount++;
        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            activityCount--;
            if (activityCount == 0) {
                isBackground = true;
                currentTouchTime = Long.MAX_VALUE;
                DataStatisticsUtils.startServiceWrite(AppManager.getInstance().getCurrentActivity());
            }

        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    };

}
