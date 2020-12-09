package com.dpad.telematicsclientapp.util.utils;



import com.dpad.telematicsclientapp.mvp.kit.Kits;
import com.dpad.telematicsclientapp.netlibrary.AppConstants;
import com.dpad.telematicsclientapp.netlibrary.MainApplicaton;
import com.dpad.telematicsclientapp.netlibrary.http.EncryptUtils.HttpEncryptUtil;
import com.dpad.telematicsclientapp.netlibrary.http.EncryptUtils.RcReqDto;
import com.dpad.telematicsclientapp.util.MD5;
import com.google.gson.Gson;
import com.socks.library.KLog;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;


import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * ================================================
 * 作    者：booob
 * 版    本：1.0
 * 创建日期：2018-06-12-0012 15:17
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class ParameterUtils {


    /**
     * 根据请求body生成请求头
     *
     * @param parm 请求的body
     * @return
     */
    public static TreeMap<String, String> getHeaser(SortedMap<String, Object> parm) {
        Date date = new Date();
        String currentTimeStamp = date.getTime() + "";
//        parm.put("currentTimeStamp", currentTimeStamp);
        String sign = MD5.GetMD5Code(getSecretStr(parm, currentTimeStamp));
        TreeMap<String, String> map = new TreeMap();
        map.put("Content-Type", "application/json;charset=UTF-8");
//        map.put("Accept-Encoding", "gzip, deflate");
        map.put("Connection", "keep-alive");
        map.put("Accept", "*/*");
        map.put("Cookie", "add cookies here");
        map.put("currentTimeStamp", currentTimeStamp);
        map.put("brandCode", AppConstants.BRANDCODE);
        map.put("sign", sign);
        map.put("appId", AppConstants.APPID);
        map.put("userName", MainApplicaton.LOGINRESULTVO.getUserName());
        map.put("userId", MainApplicaton.LOGINRESULTVO.getUserId());
        map.put("appVersionNo", AppConstants.APPVERSIONNO);
        map.put("deviceNo", AppConstants.getMyUUID());
        map.put("deviceType", AppConstants.DEVICETYPE);
        map.put("token", MainApplicaton.LOGINRESULTVO.getToken());
        map.put("vin", Kits.Empty.check(MainApplicaton.getVin()) ? "" : MainApplicaton.getVin());
        map.put("requestId", "");
        return map;
    }


   /**
     * 根据请求body生成请求头
     *
     * @param parm 请求的body
     * @return
     */
    public static TreeMap<String, String> getHeaser2(SortedMap<String, Object> parm) {
        Date date = new Date();
        String currentTimeStamp = date.getTime() + "";
        String sign = MD5.GetMD5Code(getSecretStr(parm, currentTimeStamp));
        TreeMap<String, String> map = new TreeMap();
//        map.put("Content-Type", "multipart/form-data");
        map.put("Connection", "keep-alive");
        map.put("Accept", "*/*");
        map.put("Cookie", "add cookies here");
        map.put("currentTimeStamp", currentTimeStamp);
        map.put("brandCode", AppConstants.BRANDCODE);
        map.put("sign", sign);
        map.put("appId", AppConstants.APPID);
        map.put("userName", MainApplicaton.LOGINRESULTVO.getUserName());
        map.put("userId", MainApplicaton.LOGINRESULTVO.getUserId());
        map.put("appVersionNo", AppConstants.APPVERSIONNO);
        map.put("deviceNo", AppConstants.getMyUUID());
        map.put("deviceType", AppConstants.DEVICETYPE);
        map.put("token", MainApplicaton.LOGINRESULTVO.getToken());
        map.put("vin", Kits.Empty.check(MainApplicaton.getVin()) ? "" : MainApplicaton.getVin());
        map.put("requestId", "");
        return map;
    }


    /**
     * md5 加密
     *
     * @param parm 需要加密的集合
     * @return sign
     */
    public static String getSecretStr(Map<String, Object> parm, String currentTimeStamp) {
        //step 1 排序
        String secretStr = "";
        if (parm.isEmpty()) {
            return secretStr;
        }
        SortedMap<String, Object> parameters = new TreeMap<>(parm);
        parameters.put("currentTimeStamp", currentTimeStamp);
        Set es = parameters.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            Object v = entry.getValue();
            if (v == null) {
                secretStr += "";
            } else {
                secretStr += v.toString();
            }

        }
        return secretStr;
    }

    /**
     * @param sortedMap 请求的body集合
     * @return
     */
    public static RequestBody getJsonBody(SortedMap sortedMap) {
        String json = "";
        if (sortedMap != null && !sortedMap.isEmpty()) {
            Gson gson = new Gson();
            json = gson.toJson(sortedMap);
        }
        return RequestBody.create(MediaType.parse("application/json"), json);
    }

    /**
     * 获取加密后的json
     *
     * @param sortedMap 请求的body集合
     * @return
     */
    public static RequestBody getSecretJsonBody(SortedMap sortedMap) {
        String json = "";
        RcReqDto reqDto = new RcReqDto();
        if (sortedMap != null && !sortedMap.isEmpty()) {
            Gson gson = new Gson();
            json = gson.toJson(sortedMap);
            try {
                reqDto.setContent(HttpEncryptUtil.appEncrypt( json));
            } catch (Exception e) {
                e.printStackTrace();
            }
            KLog.e(getRcreqDtoString(reqDto));
        } else {//如果是空,不加密
            try {
                reqDto.setContent( json);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        KLog.e(json);
        return RequestBody.create(MediaType.parse("application/json"), getRcreqDtoString(reqDto));
    }

    /**
     * 将recreqdto转为json
     *
     * @param reqDto
     * @return
     */
    public static String getRcreqDtoString(RcReqDto reqDto) {
        Gson gson = new Gson();
        return gson.toJson(reqDto);
    }

    /**
     * @param media 请求的body集合(filepath)
     * @return
     */
    public static MultipartBody.Part[] getFileBody(ArrayList<String> media) {
        MultipartBody.Part[] parts = new MultipartBody.Part[media.size()];
        int cnt = 0;
        for (String m : media) {
            File file = new File(m);
            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("headimg[]", file.getName(), requestFile);
            parts[cnt] = filePart;
            cnt++;
        }
        return parts;
    }

    public static RequestBody getPamaBody(String sortedMap) {
        return RequestBody.create(MediaType.parse("multipart/form-data"),sortedMap);
    }
}
