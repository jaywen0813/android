package com.dpad.telematicsclientapp.mvp.cache;

/**
 * 缓存接口
 */

public interface ICache {
    void put(String key, Object value);

    Object get(String key);

    void remove(String key);

    boolean contains(String key);

    void clear();

}
