package com.dpad.telematicsclientapp.netlibrary.http.EncryptUtils;



//import android.util.Base64;


import com.dpad.telematicsclientapp.netlibrary.http.EncryptUtils.base64.Base64;
import com.socks.library.KLog;

import java.io.IOException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;


/**
 * @Author: WindyHu
 * @Date: 2019/5/16 17:16
 * @Description:
 */
public class RSAUtil {
    /**
     * 生成秘钥对
     * @return
     */
    public static KeyPair getKeyPair() throws Exception {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        keyPairGenerator.initialize(2048);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        return keyPair;
    }

    /**
     * 获取公钥(Base64编码)
     * @param keyPair
     * @return
     */
    public static String getPublicKey(KeyPair keyPair){
        PublicKey publicKey = keyPair.getPublic();
        byte[] bytes = publicKey.getEncoded();
        return byte2Base64(bytes);
    }

    /**
     * 获取私钥(Base64编码)
     * @param keyPair
     * @return
     */
    public static String getPrivateKey(KeyPair keyPair){
        PrivateKey privateKey = keyPair.getPrivate();
        byte[] bytes = privateKey.getEncoded();
        return byte2Base64(bytes);
    }

    /**
     * 将Base64编码后的公钥转换成PublicKey对象
     * @param pubStr
     * @return
     */
    public static PublicKey string2PublicKey(String pubStr) throws Exception{
        byte[] keyBytes = base642Byte(pubStr);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PublicKey publicKey = keyFactory.generatePublic(keySpec);
        return publicKey;
    }


    /**
     * 将Base64编码后的私钥转换成PrivateKey对象
     * @param priStr
     * @return
     */
    public static PrivateKey string2PrivateKey(String priStr) throws Exception{
        byte[] keyBytes = base642Byte(priStr);
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        PrivateKey privateKey = keyFactory.generatePrivate(keySpec);
        return privateKey;
    }


    /**
     * 公钥加密
     * @param content
     * @param publicKey
     * @return
     */
    public static byte[] publicEncrypt(byte[] content, PublicKey publicKey) throws Exception{
//        Cipher cipher = Cipher.getInstance("RSA");
        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }


    /**
     * 私钥解密
     * @param content
     * @param privateKey
     * @return
     */
    public static byte[] privateDecrypt(byte[] content, PrivateKey privateKey) throws Exception{
//        Cipher cipher = Cipher.getInstance("RSA");
        Cipher cipher = Cipher.getInstance("RSA/None/PKCS1Padding");
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] bytes = cipher.doFinal(content);
        return bytes;
    }

    /**
     * 字节数组转Base64编码
     *
     * @param bytes
     * @return
     */
    public static String byte2Base64(byte[] bytes) {
        KLog.e(Base64.encodeBase64String(bytes));
        return Base64.encodeBase64String(bytes);
    }


    /**
     * Base64编码转字节数组
     *
     * @param base64Key
     * @return
     * @throws IOException
     */
    public static byte[] base642Byte(String base64Key) throws IOException {
        return Base64.decodeBase64(base64Key);
    }

//    /**
//     * 字节数组转Base64编码
//     *
//     * @param bytes
//     * @return
//     */
//    public static String byte2Base64(byte[] bytes) {
//        return Base64.encodeToString(bytes,Base64.DEFAULT);
//    }
//
//
//    /**
//     * Base64编码转字节数组
//     *
//     * @param base64Key
//     * @return
//     * @throws IOException
//     */
//    public static byte[] base642Byte(String base64Key) throws IOException {
//        return Base64.decode(base64Key,Base64.DEFAULT);
//    }

}
