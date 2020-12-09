package com.dpad.telematicsclientapp.util.utils;

/**
 * @创建者 booobdai.
 * @创建时间 2017/8/15  17:22.
 * @描述 ${存放常量}.
 */
public class Constant {


    //启动更改密码页面的requestcode
    public static int UPDATE_PWD_REQUESTCODE = 11;
    //关闭更改密码页面的requestcode
    public static int UPDATE_PWD_RESULT_CODE = 12;

    //启动修改手机号页面的requestcode
    public static int UPDATE_PHONE_REQUESTCODE = 13;
    //关闭修改页面的requestcode
    public static int UPDATE_PHONE_RESULT_CODE = 14;

    //启动添加爱车的requestcode
    public static int GOTO_MYCAR = 15;

    //启动添加联系人的requestcode
    public static int GOTO_ADD_CONTATS = 16;

    //启动修改手机号页面的 NEW requestcode
    public static int UPDATE_PHONE_NEW_REQUESTCODE = 17;
    /**
     * 维修保养传递值所需的requestCode 以及 resultCode
     */

    //跳转到选择联系人所需的code
    public static final int START_CONTACTSLISTACTIVITY_REQUEST_CODE = 17;
    public static final int START_CONTACTSLISTACTIVITY_RESULT_CODE = 18;

    //跳转到我的网点所需的code
    public static final int START_MAPACTIVTY_REQUEST_CODE = 19;
    public static final int START_MAPACTIVTY_RESULT_CODE = 20;
    public static final String GOTO_MAP_LIST_KEY = "goto_map_list_key";//跳转到网点所需的key
    public static final String BACKTO_MAP_LIST_KEY = "backto_map_list_key";//跳转回预约保养所需的key
    public static final int GOTO_MAP_LIST_VALUE = 6;//跳转到网点所传的value


    //跳转到选择时间所需的code
    public static final int START_PICKDATEACTIVITY_REQUEST_CODE = 21;
    public static final int START_PICKDATEACTIVITY_RESULT_CODE = 22;

    //跳转到我的爱车所需的code
    public static final int START_RESERVATIONCARACTIVITY_REQUEST_CODE = 23;
    public static final int START_RESERVATIONCARACTIVITY_RESULT_CODE = 24;


    //跳转到项目选择所需的code
    public static final int START_RESERVATIONDETAILACTIVITY_REQUEST_CODE = 25;
    public static final int START_RESERVATIONDETAILACTIVITY_RESULT_CODE = 26;

    //选择产品所需的code
    public static final int START_CHANGE_PRODUCT_REQUEST_CODE = 27;
    public static final int START_CHANGE_PRODUCT_RESULT_CODE = 28;

    //跳转到添加爱车的code
    public static final int START_ADD_CAR_REQUEST_CODE = 29;
    public static final int START_ADD_CAR_RESULT_CODE = 30;

    //选中爱车被删除后的resultCode
    public static final int START_RESERVATIONCARACTIVITY_NEEDREFRESH_RESULT_CODE = 31;

    public static final int START_CONTACTSLISTACTIVITY_NEEDREFRESH_RESULT_CODE = 32;

    //
    public static String CONTACTS_NAME_KEY = "contacts_name_key";
    public static String CONTACTS_PHONE_KEY = "contacts_phone_key";
    public static String CONTACTS_PHONE_ID_KEY = "contacts_phone_id_key";

    public static String CAR_VIN_KEY = "car_vin_key";//传vin的key,现在改成了cartype
    public static String CAR_NAME_KEY = "car_name_key";//传车名的key
    public static String CAR_TYPE_KEY = "car_type_key";//车的类型


    public static String PICK_DATE_KEY = "pick_date_key";//传date的key
    public static String PICK_HOUR_KEY = "pick_hour_key";//传小时的key
    public static String PICK_PROMOTIONCONTENT_KEY = "pick_promotioncontent_key";//传优惠信息的key


    public static String PICK_PRODUCT_KEY = "pick_product_key";//传产品的列表的key
    public static String CHANGE_PRODUCT_KEY = "change_product_key";//更换产品所需的key


    public static String USERINFO_REALNAME = "userinfo_realname";
    public static String USERINFO_PHONE = "userinfo_phone";
    public static String USERINFO_NICKNAME = "userinfo_nickname";
    public static String USERINFO_SEX = "userinfo_sex";
    public static String USERINFO_BIRTHDAY = "userinfo_birthday";
    public static String USERINFO_PROVINCE = "userinfo_province";
    public static String USERINFO_CITY = "userinfo_city";
    public static String USERINFO_PIC_URL = "userinfo_pic_url";
    public static String USERINFO_ADDRESS = "userinfo_address";
    public static String USERINFO_AUTOGRAPH = "userinfo_autograph";
    public static String USERINFO_TAG = "userinfo_tag";


    /**
     * 推送相关
     */

    // notify channel Id
    public static String NOTIFICATION_CHANNEL_ID = "notification_channel_id";
    // notify channel name
    public static String NOTIFICATION_CHANNEL_NAME = "notification_channel_name";
    // notify channel 的描述
    public static String NOTIFICATION_CHANNEL_DESCRIPTION = "notification_channel_description";

    //clientId的key
    public static String KEY_CLIENTID = "KEY_CLIENTID";

    public static String KEY_VIN = "key_vin";
    public static String KEY_SALESUBMODELID = "key_salesubmodelid";
    public static String KEY_USERTYPE = "key_usertype";
    public static String KEY_BURIALPOINTUSERTYPE = "key_burialPointUserType";
    public static String KEY_USERVEHTYPE = "key_uservehtype";
    public static String KEY_AGREEMENT = "key_agreement";//是否同意协议
    public static String KEY_VERSIONCODE = "key_versioncode";//app版本号


    //存储设备号的key
    public static String DEVICEID_KEY = "deviceId_key";
    //登陆账户
    public static String LOGIN_NAME = "login_name";

    public static String LOGIN_NICKNAME = "login_nickname";
    public static String LOGIN_USERPIC = "login_userpic";


    /**
     * 事件总线相关
     */
    public static class EventBus {


        /**
         * 用户登录状态变更的通知事件
         */
        public static final int TAG_USER_LOGIN_STATE_CHANGE = 0;

    }

    /**
     * 友盟统计的事件id
     */
    public static String MOBUSERREGIST = "mobUserRegist";//用户注册
    public static String MOBRESCUEORDERTRACK = "mobRescueOrderTrack";//救援订单提交
    public static String MOBRESERVATIONORDERTRACK = "mobReservationOrderTrack";//预约保养提交
    public static String MOBUSERLOGIN = "mobUserLogin";//登录事件
    public static String MOBACTIVE = "mobActive";//活动事件

    public static String LOGIN_TYPE = "login_type";//登录统计的map里面key
    public static String LOGIN_USERNAME = "LOGIN_USERNAME";//登录统计的map里面用户名key
    public static String LOGIN_NOMAL = "login_nomal";//普通登录
    public static String LOGIN_TOKEN = "login_token";//token登录
    public static String LOGIN_WEBSITE = "login_website";//官网登录


    //    消息中心的常量
    public final static String MESSAGESETTING_SYSTEM = "0";//系统消息
    public final static String MESSAGESETTING_ORRDER = "1";//预约消息
    public final static String MESSAGESETTING_RESCUE = "2";//救援消息
    public final static String MESSAGESETTING_ACTIVE = "3";//活动消息
    public final static String MESSAGESETTING_DZWL = "4";//电子围栏
    public final static String MESSAGESETTING_ZZFW = "5";//增值服务
    public final static String MESSAGESETTING_YCKZ = "6";//远程控制


    //关于我们手动增加的字段
    public final static int textType = 0;
    public final static int emailType = 1;
    public final static int phoneType = 2;
    public final static int checkVersionType = 3;


    //获取权限跳转的requestcode
    public static final int STARTACTIVITY_PREMISSION = 201;


    public static final String MAP_HISTORY_LIST_KEY = "map_history_list_key";//sharepreference保存历史数据存取的key
    public static final String MAP_T_HISTORY_LIST_KEY = "map_t_history_list_key";//sharepreference保存t车历史数据存取的key

    //跳转到搜索requestcode
    public static final int STARTACTIVITY_SEARCH_REQUESTCODE = 202;
    //跳转到搜索resultcode
    public static final int STARTACTIVITY_SEARCH_RESULTCODE = 203;

    //消息中心跳转的requestcode
    public static final int START_ACTIVITY_REQUESTCODE = 204;

    //跳转到地图收藏的requestcode
    public static final int MAP_COLLECTION_REQUESTCODE = 205;
    //跳转到地图收藏的resultcode
    public static final int MAP_COLLECTION_RESULTCODE = 206;
    //跳转到地图收藏的key
    public static final String MAP_COLLECTION_KEY = "map_collection_key";

    //车联网第一个fragment传值的key
    public static final String CAR_HOME_FRAGMENT_KEY = "car_home_fragment_key";


    //sp存取vin的ke
    public static String SP_VIN_KEY = "sp_vin_key";


    //实名认证成功
    public static int CERTIFICATION_SUCCESS = 19;


    //跳转到地图T车收藏的requestcode
    public static final int T_MAP_COLLECTION_REQUESTCODE = 215;
    //跳转到地图T车收藏的resultcode
    public static final int T_MAP_COLLECTION_RESULTCODE = 216;

    //跳到T车 搜索
    public static final int T_MAP_SEARCH_REQUESTCODE = 217;
    public static final int T_MAP_SEARCH_RESULTCODE = 218;


    public static final String COLLECTION_HOME = "1";
    public static final String COLLECTION_COMPANY = "2";
    public static final String COLLECTION_NOMAL = "3";


    public static final int ORDER_TOP_ITEM = 0;//订单的顶部
    public static final int ORDER_MIDDLE_ITEM = 1;//订单的中间
    public static final int ORDER_BOTTOM_ITEM = 2;//订单的底部
    public static final int ORDER_FOOT_ITEM = 3;//订单详情的foot


    public static final int LLCX_REQUEST_CODE = 222;//流量查询request code
    public static final int LLCX_RESULT_CODE = 223;//流量查询result  code

    public static final int ADD_INVOICE_REQUEST_CODE = 224;//添加发票request code
    public static final int ADD_INVOICE_RESULT_CODE = 225;//添加发票result  code


    public static final int GO_IN = 1;//进入
    public static final int LEAVE = 2;//离开
    public static final int NOMAL = 0;//普通


    public static final int INVOICE_DETAIL_REQUESTCODE = 226;//发票详情的requestcode

    public static final String USE_FINGERPRINT_KEY = "use_fingerprint_key";//是否使用手势识别
    public static final String USERID_KEY = "userid_key";//用户Id

    public static final int SETTING_FINGERPRINT_REQUEST_CODE = 227;//设置PIN码的request code
    public static final int SETTING_FINGERPRINT_RESULT_CODE = 228;//设置PIN码的request code

    public static final int SETTING_FINGERPRINT_REQUEST = 229;//跳转到系统设置的requestcode


    public static final int BIND_DEVICE_REQUEST = 230;//绑定设备 resultcode
    public static final int BIND_DEVICE_SUCCESS_RESULT = 231;//绑定设备requestcode 成功
    public static final int BIND_DEVICE_FAIL_RESULT = 232;//绑定设备requestcode 失败


    public static final int FORGET_PIN_REQUEST = 233;//忘记PIN码的请求码
    public static final int FORGET_PIN_SUCCESS_RESULT = 234;//忘记PIN码的 resultCode

    /**
     * 身份认证的requestCode
     */
    public static final int CONFIRM_PERSON_REQUEST = 235;
    /**
     * 身份认证的resultCode
     */
    public static final int CONFIRM_PERSON_RESULT = 236;

    /**
     * 预约请求码
     */
    public static int RESERVATION_REQUESTCODE = 237;
    /**
     * 预约成功
     */
    public static int RESERVATION_SUCCESS = 238;


    /**
     * 间隔时长20分钟,超过20分钟需要弹框,20分钟常量
     */
    public static final long BREAKTIME = 20 * 60 * 1000;

    public static final String EDIT_FUNCTION_KEY = "EDIT_FUNCTION_KEY";


    public static final int EDIT_REQUEST_CODE = 239;
    public static final int EDIT_SUCCESS = 240;

    public static final String USERVEHTYPE_KEY = "USERVEHTYPE";


    //跳转到登录页传值的key
    public static String LOGIN_KEY = "login_key";

    //跳转到远控传值的key
    public static String REMOTECONTROL_KEY = "remoteControl_key";

    /**
     * 跳转到远控的typekey
     */
    public static String REMOTECONTROL_TYPE_KEY = "REMOTECONTROL_TYPE_KEY";

    public static String WEBVIEWKEY = "webviewkey";
    public static String WEBVIEW_TITLE_KEY = "webview_title_key";
}
