package com.dpad.telematicsclientapp.netlibrary.http.EncryptUtils.base64;




import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;

/**
 * ================================================
 * 作    者：booob
 * 版    本：1.0
 * 创建日期：2019-06-04-0004 13:38
 * 描    述：
 * 修订历史：
 * ================================================
 */

public class StringUtils {
    public StringUtils() {
    }

    public static boolean equals(CharSequence cs1, CharSequence cs2) {
        if (cs1 == cs2) {
            return true;
        } else if (cs1 != null && cs2 != null) {
            return cs1 instanceof String && cs2 instanceof String ? cs1.equals(cs2) : CharSequenceUtils.regionMatches(cs1, false, 0, cs2, 0, Math.max(cs1.length(), cs2.length()));
        } else {
            return false;
        }
    }

    private static byte[] getBytes(String string, Charset charset) {
        return string == null ? null : string.getBytes(charset);
    }

    public static byte[] getBytesIso8859_1(String string) {
        return getBytes(string, Charsets.ISO_8859_1);
    }

    public static byte[] getBytesUnchecked(String string, String charsetName) {
        if (string == null) {
            return null;
        } else {
            try {
                return string.getBytes(charsetName);
            } catch (UnsupportedEncodingException var3) {
                throw newIllegalStateException(charsetName, var3);
            }
        }
    }

    public static byte[] getBytesUsAscii(String string) {
        return getBytes(string, Charsets.US_ASCII);
    }

    public static byte[] getBytesUtf16(String string) {
        return getBytes(string, Charsets.UTF_16);
    }

    public static byte[] getBytesUtf16Be(String string) {
        return getBytes(string, Charsets.UTF_16BE);
    }

    public static byte[] getBytesUtf16Le(String string) {
        return getBytes(string, Charsets.UTF_16LE);
    }

    public static byte[] getBytesUtf8(String string) {
        return getBytes(string, Charsets.UTF_8);
    }

    private static IllegalStateException newIllegalStateException(String charsetName, UnsupportedEncodingException e) {
        return new IllegalStateException(charsetName + ": " + e);
    }

    private static String newString(byte[] bytes, Charset charset) {
        return bytes == null ? null : new String(bytes, charset);
    }

    public static String newString(byte[] bytes, String charsetName) {
        if (bytes == null) {
            return null;
        } else {
            try {
                return new String(bytes, charsetName);
            } catch (UnsupportedEncodingException var3) {
                throw newIllegalStateException(charsetName, var3);
            }
        }
    }

    public static String newStringIso8859_1(byte[] bytes) {
        return new String(bytes, Charsets.ISO_8859_1);
    }

    public static String newStringUsAscii(byte[] bytes) {
        return new String(bytes, Charsets.US_ASCII);
    }

    public static String newStringUtf16(byte[] bytes) {
        return new String(bytes, Charsets.UTF_16);
    }

    public static String newStringUtf16Be(byte[] bytes) {
        return new String(bytes, Charsets.UTF_16BE);
    }

    public static String newStringUtf16Le(byte[] bytes) {
        return new String(bytes, Charsets.UTF_16LE);
    }

    public static String newStringUtf8(byte[] bytes) {
        return newString(bytes, Charsets.UTF_8);
    }
}

