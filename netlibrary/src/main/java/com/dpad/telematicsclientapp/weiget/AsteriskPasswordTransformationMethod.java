package com.dpad.telematicsclientapp.weiget;

import android.text.method.PasswordTransformationMethod;
import android.view.View;

/**
 * ================================================
 * 作    者：booob
 * 版    本：1.0
 * 创建日期：2019-07-02-0002 09:22
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class AsteriskPasswordTransformationMethod extends PasswordTransformationMethod {
    @Override
    public CharSequence getTransformation(CharSequence source, View view) {
        return new PasswordCharSequence(source);
    }

    private class PasswordCharSequence implements CharSequence {
        private CharSequence mSource;

        public PasswordCharSequence(CharSequence source) {
            mSource = source; // Store char sequence
        }

        @Override
        public char charAt(int index) {
            return '*'; // This is the important part
        }


        @Override
        public int length() {
            return mSource.length(); // Return default
        }

        @Override
        public CharSequence subSequence(int start, int end) {
            return mSource.subSequence(start, end); // Return default
        }
    }
}
