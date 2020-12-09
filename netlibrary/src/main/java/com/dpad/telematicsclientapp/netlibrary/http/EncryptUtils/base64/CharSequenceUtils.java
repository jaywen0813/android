package com.dpad.telematicsclientapp.netlibrary.http.EncryptUtils.base64;

/**
 * ================================================
 * 作    者：booob
 * 版    本：1.0
 * 创建日期：2019-06-04-0004 13:39
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class CharSequenceUtils {
    public CharSequenceUtils() {
    }

    static boolean regionMatches(CharSequence cs, boolean ignoreCase, int thisStart, CharSequence substring, int start, int length) {
        if (cs instanceof String && substring instanceof String) {
            return ((String)cs).regionMatches(ignoreCase, thisStart, (String)substring, start, length);
        } else {
            int index1 = thisStart;
            int index2 = start;
            int var8 = length;

            while(var8-- > 0) {
                char c1 = cs.charAt(index1++);
                char c2 = substring.charAt(index2++);
                if (c1 != c2) {
                    if (!ignoreCase) {
                        return false;
                    }

                    if (Character.toUpperCase(c1) != Character.toUpperCase(c2) && Character.toLowerCase(c1) != Character.toLowerCase(c2)) {
                        return false;
                    }
                }
            }

            return true;
        }
    }
}
