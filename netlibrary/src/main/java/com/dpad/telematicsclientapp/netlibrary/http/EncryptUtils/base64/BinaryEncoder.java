package com.dpad.telematicsclientapp.netlibrary.http.EncryptUtils.base64;

/**
 * ================================================
 * 作    者：booob
 * 版    本：1.0
 * 创建日期：2019-06-04-0004 13:46
 * 描    述：
 * 修订历史：
 * ================================================
 */
public interface BinaryEncoder extends Encoder {
    byte[] encode(byte[] var1) throws EncoderException;
}
