package com.dpad.telematicsclientapp.netlibrary.http.EncryptUtils;

import com.alibaba.fastjson.JSONObject;

import java.security.PrivateKey;
import java.security.PublicKey;

import javax.crypto.spec.SecretKeySpec;

/**
 * @Author: WindyHu
 * @Date: 2019/5/16 17:55
 * @Description:
 */

public class HttpEncryptUtil {

    /**
     * 服务器加密响应给APP的内容
     *
     * @param content
     * @return
     */
    public static String appEncrypt(String content) throws Exception {
        //将Base64编码后的APP公钥转换成PublicKey对象
        PublicKey appPublicKey = RSAUtil.string2PublicKey(KeyUtil.SERVER_PUBLIC_KEY);
        //每次都随机生成AES秘钥
        String aesKeyStr = AESUtil.getAESStrKey();
        //将Base64编码后的AES秘钥转换成SecretKey对象
        SecretKeySpec aesKey = AESUtil.loadKeyAES(aesKeyStr);
        //用APP公钥加密AES秘钥
        byte[] encryptAesKey = RSAUtil.publicEncrypt(aesKeyStr.getBytes(), appPublicKey);
        //用AES秘钥加密响应内容
//        byte[] encryptContent = AESUtil.encryptAES(content, aesKeyStr);
        String encryptStr = AESUtil.aesEncryptString(content, aesKeyStr);
        JSONObject result = new JSONObject();
        result.put("ak", RSAUtil.byte2Base64(encryptAesKey).replaceAll("\r\n", ""));
//        result.put("ct", RSAUtil.byte2Base64(encryptContent).replaceAll("\r\n", ""));
        result.put("ct", encryptStr.replaceAll("\r\n", ""));
        return result.toString();
    }

    /**
     * 服务器解密APP的请求内容
     *
     * @param content
     * @return
     */
    public static String appDecrypt(String content) {
        JSONObject result = JSONObject.parseObject(content);
        String encryptAesKeyStr = (String) result.get("ak");
        String encryptContent = (String) result.get("ct");
        JSONObject result2 = new JSONObject();
        try {
            //将Base64编码后的Server私钥转换成PrivateKey对象
            PrivateKey serverPrivateKey = RSAUtil.string2PrivateKey(KeyUtil.APP_PRIVATE_KEY_STR);
            //用Server私钥解密AES秘钥
            byte[] aesKeyBytes = RSAUtil.privateDecrypt(RSAUtil.base642Byte(encryptAesKeyStr), serverPrivateKey);
            //用AES秘钥解密APP公钥
//            SecretKeySpec aesKey = AESUtil.loadKeyAES(new String(aesKeyBytes, "utf-8"));
            //用AES秘钥解密请求内容
//            byte[] request = AESUtil.decryptAES(AESUtil.base642Byte(encryptContent), aesKey);
//            java.util.Base64.Encoder encoder = java.util.Base64.getEncoder();
            String request = AESUtil.aesDecryptString(encryptContent, AESUtil.byte2Base64(aesKeyBytes));
            result2.put("ak", new String(aesKeyBytes));
            result2.put("ct", request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result2.toString();
    }


    /**
     * 获取到返回的json
     *
     * @param secretResult
     * @return
     * @throws Exception
     */
    public static String getResponse(SecretResult secretResult) throws Exception {
        return appDecrypt(secretResult.getResult());
    }
}
