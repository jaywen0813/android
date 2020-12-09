package com.dpad.telematicsclientapp.util;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.NumberKeyListener;
import android.util.Base64;
import android.widget.EditText;
import android.widget.Toast;


import com.dpad.crmclientapp.android.MainApplicaton;
import com.dpad.crmclientapp.android.modules.newapp.bean.SplashBean2;
import com.dpad.crmclientapp.android.util.utils.T;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToolKit {
    private static ProgressDialog mProgressDialogDownload;

    private ToolKit() {
    }

    public static void showToast(String msg) {
//        Toast.makeText(MainApplicaton.mContext, msg, Toast.LENGTH_SHORT).show();
        T.showToastSafe(msg);
    }

    public static void showToast(String msg, int duration) {
//        Toast.makeText(MainApplicaton.mContext, msg, duration).show();
        T.showToastSafe(msg);
    }

    public static void showProgressTip(Context context, String title, String msg) {
        if (mProgressDialogDownload == null) {
            mProgressDialogDownload = new ProgressDialog(context);
        }
//		if (ToolKit.isEmpty(title)) {
//			mProgressDialogDownload.setTitle("下载");
//		} else {
//			mProgressDialogDownload.setTitle(title);
//		}
        mProgressDialogDownload.setMessage("当前进行的是:" + msg);
        mProgressDialogDownload.show();
    }

    public static void cancelProgressTip() {
        if (mProgressDialogDownload != null) {
            mProgressDialogDownload.dismiss();
            mProgressDialogDownload = null;
        }
    }

    /** 判断是否有网络连接 */
    public static boolean isNetworkConnected(Context context) {
        boolean temp = false;
        ConnectivityManager connect = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo infos = connect.getActiveNetworkInfo();
        if (infos != null) {
            temp = infos.isAvailable();
        }
        return temp;
    }

    /** 判断当前wifi网络是否可用 */
    public static boolean isWifiConnected(Context context) {
        boolean temp = false;
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager
                .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        if (info != null) {
            temp = info.isAvailable();
        }
        return temp;
    }

    /** 判断当前移动网络是否可用 */
    public static boolean isMobileConnected(Context context) {
        boolean temp = false;
        ConnectivityManager manager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = manager
                .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        if (info != null) {
            temp = info.isAvailable();
        }
        return temp;
    }

    /** 获取当前apk的版本名称 */
    public static String currentAPKVersionName(Context context) {
        String appVersionName = null;
        PackageManager pm = context.getPackageManager();
        try {
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            appVersionName = pi.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appVersionName;
    }

    /** 获取当前apk的版本号 */
    public static int currentAPKVersionCode(Context context) {
        int code = 0;
        if (context != null) {
            PackageManager pm = context.getPackageManager();
            try {
                PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
                code = pi.versionCode;
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return code;
    }

    /** 获取包名 */
    public static String getPackageName(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return pi.packageName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /** 获取应用名 */
    public static String getAppName(Context context) {
        try {
            PackageInfo pi = context.getPackageManager().getPackageInfo(
                    context.getPackageName(), 0);
            return pi.applicationInfo.loadLabel(context.getPackageManager())
                    .toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 验证电话号码
     *
     * @param phone
     * @return \d正则错误可以替换[0-9] 验证手机号和固定电话
     *         ^(0?1[358]\d{9})|((0(10|2[1-3]|[3-9]\d{2}))?[1-9]\d{6,7})$
     *         只验证手机号码 ^((1[0-9][0-9])|(15[^4,\\D])|(18[0-9]))\\d{8}$
     */
    public static boolean validatePhone(String phone) {
        Pattern p = Pattern
                .compile("^(0?1[358][0-9]{9})|((0(10|2[1-3]|[3-9][0-9]{2}))?[1-9][0-9]{6,7})$");
        Matcher matcher = p.matcher(phone);
        return matcher.matches();
    }

    /**
     * 验证输入的是不是数字
     *
     * @param number
     * @return ^[1-9][0-9]*$ 只能输入数字 ^[1-9][0-9]{1,8}([.][0-9]{1,5})?$ 第一位不能是数字
     *         小数点前面最多10位 小数点后面的最多5位
     */
    public static boolean validateIsNumber(String number) {
        Pattern p = Pattern.compile("^[0-9]{1,8}([.][0-9]{1,2})?$");
        Matcher matcher = p.matcher(number);
        return matcher.matches();
    }

    /**
     * 判断输入的名字必须是中文也可以是英文字母 且长度2为到50位
     *
     * @param name
     * @return
     */
    public static boolean validateIsName(String name) {
        Pattern p = Pattern.compile("^([\u4e00-\u9fa5]){2,50}$");
        Matcher matcher = p.matcher(name);
        return matcher.matches();
    }

    /**
     * 判断输入的地址是否合法 这里的地址只能是中文
     *
     * @param address
     * @return
     */
    public static boolean validateIsAddress(String address) {
        Pattern p = Pattern.compile("^([\u4e00-\u9fa5]){2,50}$");
        Matcher matcher = p.matcher(address);
        return matcher.matches();
    }

    /**
     * 判断输入的报案号是否合法
     *
     * @param tastnum
     * @return
     */
    public static boolean validateIsTastNumber(String tastnum) {
        Pattern p = Pattern.compile("^([a-zA-Z0-9]*){2,24}$");
        Matcher matcher = p.matcher(tastnum);
        return matcher.matches();
    }

    /**
     * 获取imsi手机卡
     *
     * @param context
     * @return
     */
//    public static String getImsi(Context context) {
//        TelephonyManager mTelephonyMgr = (TelephonyManager) context
//                .getSystemService(Context.TELEPHONY_SERVICE);
//        return mTelephonyMgr.getSubscriberId();
//    }

    /**
     * 获取imei手机信息
     *
     * @param number
     * @return
     */
//    public static String getImei(Context context) {
//        TelephonyManager mTelephonyMgr = (TelephonyManager) context .getSystemService(Context.TELEPHONY_SERVICE);
//        return mTelephonyMgr.getDeviceId();
//    }

    /***
     * 全量数字和字母
     *
     * @return
     */
    public static char[] getNumberAndEnglish() {
        char[] numberChars = { '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'g', 'k',
                'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w',
                'x', 'y', 'z', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O',
                'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X',
                'C', 'V', 'B', 'N', 'M' };
        return numberChars;
    }

    /**
     * 特殊字符
     * @return
     */
    public static String inputProhibition() {
        String digits = "/\\:*?<>|\"\n\t~-+=——！#￥%^&();',[]{}`!@。，.$（）《》~！·……、】【‘；‘、。，？“：”"
                + "▲△▽○◇□☆▷◁♤♡♢♧♣♦♥♠◀▶★■◆●▼☼☀▪∷※•☾☽♀♂‥░▒…☹☺◆■◐◑▁▓▏▂"
                + "☒☑★▶√×▃▎▍▄✘✔◀♠☜☚▅▆▇█㏘☛☟☝㏂♩♭♯♪♫♮‖♬§¶卍〼卐◎¤▬〓۞℡℗®©㏇™";
        return digits;
    }

    /**
     * 防止edittext输入输入特殊符号
     *
     * @return
     */
    public static void inputProhibition(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            String tmp = "";
            String digits = ToolKit.inputProhibition();

            @Override
            public void onTextChanged(CharSequence s, int arg1, int arg2,
                                      int arg3) {
                // 不能输入特殊符号
                String str = s.toString();
                if (str.equals(tmp)) {
                    return;
                }
                StringBuffer sb = new StringBuffer();
                for (int i = 0; i < str.length(); i++) {
                    if (digits.indexOf(str.charAt(i)) < 0) {
                        sb.append(str.charAt(i));
                    }
                }
                tmp = sb.toString();
                editText.setText(tmp);
            }

            @Override
            public void beforeTextChanged(CharSequence s, int arg1, int arg2,
                                          int arg3) {
                tmp = s.toString();
            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });
    }

    /**
     * 监听光标时刻在最后面
     *
     * @return
     */
    public static void setInputSelection(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int arg1, int arg2,
                                      int arg3) {
                if (s.length() >= 1) {
                    editText.setSelection(editText.length());
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

        });
    }

    /**
     * 数字和逗号
     *
     * @return
     */
    public static char[] inputNumber() {
        char[] numberChars = { '1', '2', '3', '4', '5', '6', '7', '8', '9',
                '0', '.' };
        return numberChars;
    }

    /**
     * 只准输入数字和逗号
     *
     * @return
     */
    public static void inputNumber(EditText editText) {
        editText.setKeyListener(new NumberKeyListener() {

            @Override
            public int getInputType() {
                return 0;
            }

            @Override
            protected char[] getAcceptedChars() {
                char[] numberChars = ToolKit.inputNumber();
                return numberChars;
            }
        });
    }

    /**
     * 监听edittext小数点后面只能输如两位
     *
     * @return
     */
    public static void inputNumberTwo(final EditText editText) {
        editText.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence s, int arg1, int arg2, int arg3) {
                /**控制只能输入一个小数点*/
                if (editText.getText().toString().indexOf(".") >= 0) {
                    if (editText.getText().toString().indexOf(".", editText.getText().toString().indexOf(".") + 1) > 0) {
                        editText.setText(editText.getText().toString().substring(0, editText.getText().toString().length() - 1));
                        editText.setSelection(editText.getText().toString().length());
                    }

                }
                /**只能输入小数点后面两位*/
                if (s.toString().contains(".")) {
                    if (s.length() - 1 - s.toString().indexOf(".") > 2) {
                        s = s.toString().subSequence(0,
                                s.toString().indexOf(".") + 3);
                        editText.setText(s);
                        editText.setSelection(s.length());
                    }
                }
                if (s.toString().trim().substring(0).equals(".")) {
                    s = "0" + s;
                    editText.setText(s);
                    editText.setSelection(2);
                }

                if (s.toString().startsWith("0")
                        && s.toString().trim().length() > 1) {
                    if (!s.toString().substring(1, 2).equals(".")) {
                        editText.setText(s.subSequence(0, 1));
                        editText.setSelection(1);
                        return;
                    }
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int arg1, int arg2,
                                          int arg3) {
            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }
        });
    }

    public static boolean isIP(String addr) {
        if (addr.length() < 7 || addr.length() > 15 || "".equals(addr)) {
            return false;
        }
        /**
         * 判断IP格式和范围
         */
        String rexp = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";

        Pattern pat = Pattern.compile(rexp);

        Matcher mat = pat.matcher(addr);

        boolean ipAddress = mat.find();

        return ipAddress;
    }
    /**
     *只准输入数字和英文
     * @return
     */
    public static void inputNumberAndLetter(EditText editText) {
        editText.setKeyListener(new NumberKeyListener() {

            @Override
            public int getInputType() {
                return 0;
            }

            @Override
            protected char[] getAcceptedChars() {
                char[] numberChars = ToolKit.getNumberAndEnglish();
                return numberChars;
            }
        });
    }

    /**
     * time 转 String
     * @param s
     * @return
     */
    public static String stampToDate(String s){
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");
        long lt = new Long(s);
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 官网密码加密
     * @param str
     * @return
     */
    public  static String websiteBase64(String str){
        String base_str = Base64.encodeToString(str.getBytes(), Base64.DEFAULT).replaceAll("\r\n", "").replaceAll("\r", "").replaceAll("\n", "");
        if(base_str.toCharArray().length>1){
            String headStr = base_str.toCharArray()[0]+"";
            String bottomStr = base_str.toCharArray()[base_str.toCharArray().length-1]+"";
            StringBuilder sb = new StringBuilder(base_str);
            sb.replace(0,1,bottomStr);
            sb.replace(base_str.toCharArray().length-1,base_str.toCharArray().length,headStr);
            base_str = sb.toString();
        }
        return base_str;
    }
    public static String getImageUrl(SplashBean2 bean , int y, int x){
        String res ="";
        DecimalFormat df=new DecimalFormat("0.00");
        float kgb = (float)y/x;
        if(kgb>2){
            res = getSplashBean2(bean,5);
        }else if(kgb>1.96&&kgb<2){
            res = getSplashBean2(bean,3);
        }else{
            res = getSplashBean2(bean,4);
        }
        return res;
    }
    public static String getSplashBean2(SplashBean2 bean,int orderbyId){
        String imageUrl = "";
        for(SplashBean2.Result result :bean.getResult()){
            if(orderbyId==result.getOrderBy()){
                imageUrl = result.getPicUrl();
            }
        }
        return imageUrl;
    }
}
