package com.dpad.telematicsclientapp.weiget;

import com.chad.library.adapter.base.loadmore.LoadMoreView;
import com.dpad.telematicsclientapp.netlibrary.R;


public class CustomLoadMoreView extends LoadMoreView {

    @Override
    public int getLayoutId() {
        return R.layout.view_load_more;
    }


    @Override
    public boolean isLoadEndGone() {
        return false;
    }

    @Override
    protected int getLoadingViewId() {
        return R.id.load_more_loading_view;
    }

    @Override
    protected int getLoadFailViewId() {
        return R.id.load_more_load_fail_view;
    }


    @Override
    protected int getLoadEndViewId() {
        return R.id.load_more_load_end_view;
    }


}
