package com.dpad.telematicsclientapp.netlibrary.base.basecopy;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;

import com.dpad.telematicsclientapp.mvp.mvp.XPresent;
import com.dpad.telematicsclientapp.netlibrary.R;
import com.dpad.telematicsclientapp.util.utils.UIUtils;
import com.dpad.telematicsclientapp.weiget.CustomLoadMoreView;
import com.dpad.telematicsclientapp.weiget.RecyclerViewDivider;
import com.socks.library.KLog;

import butterknife.BindView;
import rx.subscriptions.CompositeSubscription;

/**
 * @创建者 booobdai.
 * @创建时间 2017/8/11  17:56.
 * @描述 ${列表Fragment 基类}.
 */
public abstract class BaseListFragment<XPI extends XPresent> extends BaseCopyFragment<XPI> implements SwipeRefreshLayout
        .OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener, BaseQuickAdapter.OnItemClickListener {

    private static final String TAG = BaseListFragment.class.getSimpleName();
//    @BindView(R.id.rv_list)
    protected RecyclerView mRvList;
//    @BindView(R.id.srl_list)
    protected SwipeRefreshLayout mSrlList;

    //分页 每页item数量
    protected static final int PAGE_SIZE = 20;
    //列表Adapter
    protected BaseQuickAdapter adapter;
    //页码, 初始为1
    protected int page = 1;
    //是否是刷新 默认进来是刷新 true
    protected boolean isRefresh = true;
    protected TextView mTvEmpty;
    protected ImageView mEmptyIm;
    public TextView tvDescribe;//描述
    private String description = "";

    @Override
    protected void initPageData() {

    }

    @Override
    public void bindEvent() {
        super.bindEvent();

        mRvList=context.findViewById(R.id.rv_list);
        mSrlList=context.findViewById(R.id.srl_list);


        mSrlList.setColorSchemeColors(UIUtils.getColor(R.color.colorPrimary));
        mSrlList.setOnRefreshListener(this);
        mRvList.setLayoutManager(getLayoutManager());
        adapter = getRecyclerAdapter();
        mRvList.setAdapter(adapter);
        adapter.bindToRecyclerView(mRvList);
        if (canLoadMore()) {
            adapter.setOnLoadMoreListener(this);
        }
        if (getRecycleViewDivider() != null) {
            mRvList.addItemDecoration(getRecycleViewDivider());
        }
        View emptyView = getEmptyView();
        mTvEmpty = (TextView) emptyView.findViewById(R.id.tv_empty);
        mEmptyIm = emptyView.findViewById(R.id.empty_im);
        adapter.setEmptyView(emptyView);
        adapter.setLoadMoreView(new CustomLoadMoreView());
        adapter.setOnItemClickListener(this);
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_list;
    }

    @Override
    protected XPI newPresenter(CompositeSubscription subscriptions) {
        return null;
    }

    @Override
    public void onRefresh() {
        page = 1;
        isRefresh = true;
        refresh();
    }

    @Override
    public void onLoadMoreRequested() {
        isRefresh = false;
        loadMore();
    }

    public void onLoadSucceed() {
        page++;
    }

    public void onLoadFail() {
        if (isRefresh && adapter.getData().size() == 0) {
            //刷新失败, 且原数据为空则显示加载错误的布局
            KLog.e(TAG, "onLoadFail refresh Fail");
            mTvEmpty.setText(UIUtils.getString(R.string.base_load_error_and_retry));
            mTvEmpty.setVisibility(View.VISIBLE);
            mEmptyIm.setVisibility(View.INVISIBLE);
            tvDescribe.setVisibility(View.INVISIBLE);
        } else {
            //loadmore 失败
            adapter.loadMoreFail();
            KLog.e(TAG, "onLoadFail loadMore Fail");
        }
    }

    public void setDescription(String description) {
        this.description = description;
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
        return new RecyclerViewDivider(context, LinearLayoutManager.HORIZONTAL);
    }

    /**
     * 获取LayoutManager( 默认 线性 纵向, 如需修改重写此方法)
     *
     * @return
     */
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
    }

    /**
     * 获取空布局, 用来自定义空布局样式
     *
     * @return
     */
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
        View footView = layoutInflater.inflate(R.layout.foot_base_layout, null);
        adapter.addFooterView(footView);
    }

    @Override
    public void loading(String text) {
        if (!mSrlList.isRefreshing()) {
            mSrlList.setRefreshing(true);
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
        mSrlList.setRefreshing(false);
        //如果当前正在加载更多
        if (!isRefresh && adapter.isLoading()) {
            adapter.loadMoreComplete();
        }
        if (mTvEmpty != null) {
            mTvEmpty.setText(UIUtils.getString(R.string.base_load_empty));
            mTvEmpty.setVisibility(View.INVISIBLE);
            mEmptyIm.setVisibility(View.VISIBLE);
            tvDescribe.setVisibility(View.VISIBLE);
            tvDescribe.setText(description);
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
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {

    }
}
