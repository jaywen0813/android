package com.dpad.telematicsclientapp.netlibrary.http.EncryptUtils.base64;

import java.nio.charset.Charset;

/**
 * ================================================
 * 作    者：booob
 * 版    本：1.0
 * 创建日期：2019-06-04-0004 13:40
 * 描    述：
 * 修订历史：
 * ================================================
 */

public class Charsets {
    /** @deprecated */
    @Deprecated
    public static final Charset ISO_8859_1 = Charset.forName("ISO-8859-1");
    /** @deprecated */
    @Deprecated
    public static final Charset US_ASCII = Charset.forName("US-ASCII");
    /** @deprecated */
    @Deprecated
    public static final Charset UTF_16 = Charset.forName("UTF-16");
    /** @deprecated */
    @Deprecated
    public static final Charset UTF_16BE = Charset.forName("UTF-16BE");
    /** @deprecated */
    @Deprecated
    public static final Charset UTF_16LE = Charset.forName("UTF-16LE");
    /** @deprecated */
    @Deprecated
    public static final Charset UTF_8 = Charset.forName("UTF-8");

    public Charsets() {
    }

    public static Charset toCharset(Charset charset) {
        return charset == null ? Charset.defaultCharset() : charset;
    }

    public static Charset toCharset(String charset) {
        return charset == null ? Charset.defaultCharset() : Charset.forName(charset);
    }
}

