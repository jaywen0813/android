package com.dpad.telematicsclientapp.mvp.mvp;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Presenter 基类
 */

public abstract class XPresent<V extends IView> implements IPresent<V> {
    private V v;
    private CompositeDisposable mCompositeDisposable;
    @Override
    public void attachV(V view) {
        v = view;
    }

    @Override
    public void detachV() {
        if (null != mCompositeDisposable) {
            mCompositeDisposable.dispose();
            mCompositeDisposable.clear();
        }
        v = null;
    }

    protected V getV() {
        if (v == null) {
            throw new IllegalStateException("v can not be null");
        }
        return v;
    }
    public void addDisposable(Disposable disposable) {
        if (null == mCompositeDisposable) {
            mCompositeDisposable = new CompositeDisposable();
        }
        mCompositeDisposable.add(disposable);
    }

}
