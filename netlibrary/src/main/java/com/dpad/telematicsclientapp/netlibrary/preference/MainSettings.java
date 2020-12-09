package com.dpad.telematicsclientapp.netlibrary.preference;

/**
 * Created by vigss on 2018/3/20.
 */

public enum MainSettings {

    SETTINGS__ID("settings__id", ""),
    SETTINGS__USERNAME("username", "");
    private final String mId;
    private final Object mDefaultValue;

    MainSettings(String id, Object defaultValue) {
        this.mId = id;
        this.mDefaultValue = defaultValue;
    }
    public String getId() {
        return this.mId;
    }

    public Object getDefaultValue() {
        return this.mDefaultValue;
    }
}
