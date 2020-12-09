package com.dpad.telematicsclientapp.netlibrary.base.basecopy;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;


import com.dpad.telematicsclientapp.mvp.mvp.XPresent;
import com.dpad.telematicsclientapp.netlibrary.R;
import com.dpad.telematicsclientapp.util.utils.DialogUtil;
import com.dpad.telematicsclientapp.util.utils.KeyboardUtil;
import com.dpad.telematicsclientapp.util.utils.UIUtils;
import com.dpad.telematicsclientapp.weiget.CustomLoadMoreView;
import com.socks.library.KLog;

import butterknife.BindView;
import rx.subscriptions.CompositeSubscription;

/**
 * @创建者 booobdai.
 * @创建时间 2017/8/9  9:10.
 * @描述 ${列表Activity 基类}.
 */
public abstract class BaseListActivity<XPI extends XPresent> extends BaseCopyActivity<XPI> implements SwipeRefreshLayout
        .OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    private static final String TAG = BaseListActivity.class.getSimpleName();

    public boolean isNeedOverideBack = false;
    //分页 每页item数量
    protected static final int PAGE_SIZE = 20;
    //列表Adapter
    protected BaseQuickAdapter adapter;
    //页码, 初始为1
    protected int page = 1;
    //分页 index
    protected int firstIndex = 0;
    //是否是刷新 默认进来是刷新 true
    protected boolean isRefresh = true;
//    @BindView(R.id.back_layout)
    public LinearLayout backLayout;
//    @BindView(R.id.tv_layer_head)
    public TextView tvLayerHead;
    //    @BindView(R.id.navigation_user)
//    public ImageView navigationUser;
//    @BindView(R.id.navigation_user_layout)
    public LinearLayout navigationUserLayout;
//    @BindView(R.id.farm_input_save)
    public TextView farmInputSave;
//    @BindView(R.id.rv_list)
    public RecyclerView rvList;
//    @BindView(R.id.srl_list)
    public SwipeRefreshLayout srlList;
    public TextView mTvEmpty;
    //    private ProgressBar progressBar;
    public ImageView mEmptyIm;
    public TextView tvDescribe;//描述

    private String description = "";

    @Override
    protected void initPageData() {

    }

    @Override
    public void bindEvent() {
        super.bindEvent();
        //初始化键盘自动隐藏(点击EditText 外部区域)
        KeyboardUtil.init(context);
        backLayout=findViewById(R.id.back_layout);
        tvLayerHead=findViewById(R.id.tv_layer_head);
        navigationUserLayout=findViewById(R.id.navigation_user_layout);
        farmInputSave=findViewById(R.id.farm_input_save);
        rvList=findViewById(R.id.rv_list);
        srlList=findViewById(R.id.srl_list);

        if (!isNeedOverideBack) {
            backLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        srlList.setColorSchemeColors(UIUtils.getColor(R.color.colorPrimary));
        srlList.setOnRefreshListener(this);
        rvList.setLayoutManager(getLayoutManager());
        adapter = getRecyclerAdapter();
        rvList.setAdapter(adapter);
        adapter.bindToRecyclerView(rvList);
        if (canLoadMore()) {
            adapter.setOnLoadMoreListener(this);
        }
        if (getRecycleViewDivider() != null) {
            rvList.addItemDecoration(getRecycleViewDivider());
        }
        if (getEmptyView() != null) {
            adapter.setEmptyView(getEmptyView());
        }
        adapter.setLoadMoreView(new CustomLoadMoreView());
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_list;
    }

    @Override
    protected XPI newPresenter(CompositeSubscription subscriptions) {
        return null;
    }

    /**
     * 是否支持加载更多 (如需修改, 重写此方法)
     *
     * @return
     */
    protected boolean canLoadMore() {
        return true;
    }

    /**
     * 获取分割线 (默认横向分割线2px灰色, 如需修改 重新此方法)
     *
     * @return
     */
    protected RecyclerView.ItemDecoration getRecycleViewDivider() {
        return null;
//        return

    }

    /**
     * 获取LayoutManager( 默认 线性 纵向, 如需修改重写此方法)
     *
     * @return
     */
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    protected View getEmptyView() {
        View emptyView = LayoutInflater.from(context).inflate(R.layout.layout_empty, null);
        mTvEmpty = (TextView) emptyView.findViewById(R.id.tv_empty);
        mEmptyIm = emptyView.findViewById(R.id.empty_im);
        tvDescribe = emptyView.findViewById(R.id.tv_describe);
        tvDescribe.setText(description);
//        progressBar=emptyView.findViewById(R.id.empty_prog);
        return emptyView;
    }

    /**
     * 添加默认footview
     */
    protected void addDefaultFootView() {
        View footView = LayoutInflater.from(context).inflate(R.layout.foot_base_layout, null);
        adapter.addFooterView(footView);
    }

    @Override
    public void loading(String text) {
        if (!srlList.isRefreshing()) {
            srlList.setRefreshing(true);
        }
        if (mTvEmpty != null) {
            mTvEmpty.setText(R.string.base_load_loading);
            mTvEmpty.setVisibility(View.VISIBLE);
            mEmptyIm.setVisibility(View.GONE);
            tvDescribe.setVisibility(View.GONE);
        }
    }

    @Override
    public void loadingComplete() {
        srlList.setRefreshing(false);
        //如果当前正在加载更多
        if (!isRefresh && adapter.isLoading()) {
            adapter.loadMoreComplete();
        }
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
        if (mTvEmpty != null) {
            mTvEmpty.setText(UIUtils.getString(R.string.base_load_empty));
            mTvEmpty.setVisibility(View.INVISIBLE);
//            progressBar.setVisibility(View.INVISIBLE);
            mEmptyIm.setVisibility(View.VISIBLE);
            tvDescribe.setVisibility(View.VISIBLE);
            tvDescribe.setText(description);
        }
    }


    public void onloadSucceed() {
        page++;
        firstIndex += PAGE_SIZE;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void onLoadFail() {
        if (isRefresh && adapter.getData().size() == 0) {
            //刷新失败, 且原数据为空则显示加载错误的布局
            KLog.e(TAG, "onLoadFail refresh Fail");
            //            showError();
            if (mTvEmpty != null) {
                mTvEmpty.setText(UIUtils.getString(R.string.base_load_error_and_retry));
                mTvEmpty.setVisibility(View.VISIBLE);
//                progressBar.setVisibility(View.INVISIBLE);
                mEmptyIm.setVisibility(View.INVISIBLE);
                tvDescribe.setVisibility(View.INVISIBLE);
            }
        } else {
            //loadmore 失败
            adapter.loadMoreFail();
            KLog.e(TAG, "onLoadFail loadMore Fail");
        }
    }

    /**
     * 获取adapter
     */
    protected abstract BaseQuickAdapter getRecyclerAdapter();

    /**
     * 刷新列表
     */
    protected abstract void refresh();

    /**
     * 加载更多
     */
    protected abstract void loadMore();

    @Override
    public void onRefresh() {
        page = 1;
        firstIndex = 0;
        isRefresh = true;
        refresh();
    }

    @Override
    public void onLoadMoreRequested() {
        isRefresh = false;
        loadMore();
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }


    /**
     * 显示通用的加载中dialog
     *
     * @param text 显示的文本
     */
    public void loadingWithText(String text) {
        if (mLoadingDialog == null) {
            mLoadingDialog = DialogUtil.loading(this, text);
        } else {
            mLoadingDialog.show();
        }
    }
}
