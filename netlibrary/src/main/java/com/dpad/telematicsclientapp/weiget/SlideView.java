package com.dpad.telematicsclientapp.weiget;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.DecelerateInterpolator;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Scroller;

/**
 * Created by Administrator on 2016/7/20.
 * 可滑动LinearLayout
 */
public class SlideView extends LinearLayout
{

    private float mLastY = -1;
    private float xDistance, yDistance, xLast, yLast;
    private AdapterView<?> mAdapterView;
    private ScrollView mScrollView;
    private View scrollContent;
    private final int SCROLL_DURATION = 200; // 滚动回时间
    private Scroller mScroller;
    private boolean isIntercept;

    public SlideView(Context context)
    {
        super(context);
        init(context);
    }

    public SlideView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(context);
    }

    public SlideView(Context context, AttributeSet attrs, int defStyleAttr)
    {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context)
    {
        mScroller = new Scroller(context, new DecelerateInterpolator());
    }

    @Override
    protected void onFinishInflate()
    {
        super.onFinishInflate();
        traversalView(this);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev)
    {
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                xDistance = yDistance = 0f;
                xLast = ev.getX();
                yLast = ev.getY();
                mLastY = ev.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                final float curX = ev.getX();
                final float curY = ev.getY();
                xDistance += Math.abs(curX - xLast);
                yDistance += Math.abs(curY - yLast);
                xLast = curX;
                yLast = curY;
                if (xDistance > yDistance)
                    isIntercept = true;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev)
    {
        int scrollY = getScrollY();
        switch (ev.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                isIntercept = false;
                mLastY = ev.getRawY();
                if (!mScroller.isFinished())
                    mScroller.forceFinished(true);
                break;
            case MotionEvent.ACTION_MOVE:
                if (isIntercept) break;
                final float deltaY = ev.getRawY() - mLastY;
                mLastY = ev.getRawY();
                float speedY = Math.abs(scrollY / 80);//计算加速度
                if (speedY < 3)
                    speedY = 3;
                int sbY = -(int) (deltaY / speedY);
                if (mScrollView != null && scrollContent != null)
                {// 对于ScrollView
                    // 主要判断不得移出屏幕之外，必须在屏幕可见范围移动
                    if (scrollY <= 0 && mScrollView.getScrollY() == 0)
                    {//顶部活动且不能低于0
                        if (scrollContent.getMeasuredHeight() > getHeight() + mScrollView.getScrollY() && sbY + scrollY > 0)
                            sbY = -scrollY;
                        scrollBy(0, sbY);
                    } else if (scrollY >= 0 && scrollContent.getMeasuredHeight() <= getHeight() + mScrollView.getScrollY())
                    {//底部活动且不能高于0
                        if (sbY + scrollY < 0)
                            sbY = -scrollY;
                        scrollBy(0, sbY);
                    }
                } else if (mAdapterView != null && mAdapterView.getChildAt(0) != null && mAdapterView.getChildAt(mAdapterView.getChildCount() - 1) != null)
                {
                    // 主要判断不得移出屏幕之外，必须在屏幕可见范围移动
                    if (scrollY <= 0 && mAdapterView.getFirstVisiblePosition() == 0 && (mAdapterView.getChildAt(0).getTop() == 0 || mAdapterView.getChildAt(0).getTop() - mAdapterView.getPaddingTop() == 0))
                    {//顶部活动且不能低于0
                        if (mAdapterView.getChildAt(mAdapterView.getChildCount() - 1).getBottom() > getHeight() && sbY + scrollY > 0)
                            sbY = -scrollY;
                        scrollBy(0, sbY);
                    } else if (scrollY >= 0 && mAdapterView.getChildAt(mAdapterView.getChildCount() - 1).getBottom() <= getHeight() && mAdapterView.getLastVisiblePosition() == mAdapterView.getCount() - 1)
                    {//底部活动且不能高于0
                        if (sbY + scrollY < 0)
                            sbY = -scrollY;
                        scrollBy(0, sbY);
                    }
                } else
                {
                    scrollBy(0, sbY);
                }

                break;
            case MotionEvent.ACTION_UP:
                if (scrollY != 0)
                {
                    mScroller.startScroll(0, scrollY, 0, -scrollY, SCROLL_DURATION);
                    invalidate();
                }
                break;
        }
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        return true;
    }

    @Override
    public void computeScroll()
    {
        if (mScroller.computeScrollOffset())
        {
            scrollTo(0, mScroller.getCurrY());
            invalidate();
        }
        super.computeScroll();
    }

    @Override
    public boolean performClick()
    {
        return super.performClick();
    }

    /**
     * 遍历所有view,判断子控件中的第一个是否含有可滑动控件
     *
     * @param viewGroup
     */
    public void traversalView(ViewGroup viewGroup)
    {
        int count = viewGroup.getChildCount();
        if (mScrollView != null || mAdapterView != null) return;
        for (int i = 0; i < count; i++)
        {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ScrollView)
            {
                mScrollView = (ScrollView) view;
                scrollContent = mScrollView.getChildAt(0);
                //当弹性还未恢复时，禁止子控件自己滑动
                mScrollView.setOnTouchListener(new OnTouchListener() {
                    @Override
                    public boolean onTouch(View v, MotionEvent event) {
                        if (SlideView.this.getScrollY() != 0)
                            return true;
                        return false;
                    }
                });
            } else if (view instanceof AdapterView<?>)
            {
                mAdapterView = (AdapterView<?>) view;
                mAdapterView.setOnTouchListener(new OnTouchListener()
                {//当弹性还未恢复时，禁止子控件自己滑动
                    @Override
                    public boolean onTouch(View v, MotionEvent event)
                    {
                        if (getScrollY() != 0)
                            return true;
                        return false;
                    }
                });
            } else if (view instanceof ViewGroup)
            {
                traversalView((ViewGroup) view);
            }
        }
    }

}
