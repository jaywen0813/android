package com.dpad.telematicsclientapp.mvp.event;

/**
 * 事件订阅接口
 */

public interface IBus {

    void register(Object object);

    void unregister(Object object);

    void post(IEvent event);

    void postSticky(IEvent event);

    interface IEvent {
        int getTag();
    }

}
