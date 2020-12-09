package com.dpad.telematicsclientapp.mvp.base;

/**
 *AbsListView 条目事件监听
 */

public abstract class ListItemCallback<T> {

    public void onItemClick(int position, T model, int tag) {}

    public void onItemLongClick(int position, T model, int tag) {}
}
