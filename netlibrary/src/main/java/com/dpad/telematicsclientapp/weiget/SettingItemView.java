package com.dpad.telematicsclientapp.weiget;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.dpad.telematicsclientapp.netlibrary.R;
import com.dpad.telematicsclientapp.util.utils.UIUtils;


/**
 * @创建者 booobdai.
 * @创建时间 2017/8/11  11:36.
 * @描述 ${用户信息 设置等itemView}.
 */
public class SettingItemView extends RelativeLayout {


    /**
     * 标题
     */
    private TextView mTvSetting;
    /**
     * 描述
     */
    private TextView mTvSettingDetail;
    /**
     * 左边图标
     */
    private ImageView mIvSettingLeftIcon;
    /**
     * 右边图标
     */
    private ImageView mIvSettingRightIcon;


    public SettingItemView(Context context) {
        this(context, null);
    }

    public SettingItemView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SettingItemView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    /**
     * @param context
     * @param attrs
     */
    private void initView(Context context, AttributeSet attrs) {
        TypedArray attributes = context.obtainStyledAttributes(attrs, R.styleable.SettingItemView);
        View rootView = LayoutInflater.from(context).inflate(R.layout.layout_setting, null);
        mTvSetting = (TextView) rootView.findViewById(R.id.tv_setting);
        mTvSettingDetail = (TextView) rootView.findViewById(R.id.tv_setting_detail);
        mIvSettingLeftIcon = (ImageView) rootView.findViewById(R.id.iv_setting_left_icon);
        mIvSettingRightIcon = (ImageView) rootView.findViewById(R.id.iv_setting_right_icon);
        if (attributes != null) {
            initLeftIcon(attributes);
            initRightIcon(attributes);
            initTitleText(attributes);
            initDetailText(attributes);
            mTvSetting.setTextColor(getTitleTextColor(attributes));
            mTvSettingDetail.setTextColor(getDetailTextColor(attributes));
            attributes.recycle();
        }
        addView(rootView);
    }

    /**
     * 描述文字颜色
     *
     * @param attributes
     * @return
     */
    private int getDetailTextColor(TypedArray attributes) {
        int color = 0X000000;
        int textColorStyleable = attributes.getResourceId(R.styleable.SettingItemView_siv_detail_text_color, 0);
        if (textColorStyleable > 0) {
            return attributes.getResources().getColor(textColorStyleable);
        } else {
            return attributes.getColor(R.styleable.SettingItemView_siv_detail_text_color, color);
        }
    }

    /**
     * 标题文字颜色
     *
     * @param attributes
     * @return
     */
    private int getTitleTextColor(TypedArray attributes) {
        int color = 0X000000;
        int textColorStyleable = attributes.getResourceId(R.styleable.SettingItemView_siv_title_text_color, 0);
        if (textColorStyleable > 0) {
            return attributes.getResources().getColor(textColorStyleable);
        } else {
            return attributes.getColor(R.styleable.SettingItemView_siv_title_text_color, color);
        }
    }


    /**
     * 描述文字
     *
     * @param attributes
     */
    private void initDetailText(TypedArray attributes) {
        String detailText = attributes.getString(R.styleable.SettingItemView_detaile_text);
        if (!TextUtils.isEmpty(detailText)) {
            mTvSettingDetail.setText(detailText);
        }
    }

    /**
     * 标题
     *
     * @param attributes
     */
    private void initTitleText(TypedArray attributes) {
        String text = attributes.getString(R.styleable.SettingItemView_text);
        if (!TextUtils.isEmpty(text)) {
            mTvSetting.setText(text);
        }
    }

    /**
     * 右边图标
     *
     * @param attributes
     */
    private void initRightIcon(TypedArray attributes) {
        boolean rightIconGone = attributes.getBoolean(R.styleable.SettingItemView_right_icon_gone, false);
        boolean rightIconVisible = attributes.getBoolean(R.styleable.SettingItemView_right_icon_visible, true);
        if (rightIconGone) {
            mIvSettingRightIcon.setVisibility(GONE);
            return;
        }
        mIvSettingRightIcon.setVisibility(rightIconVisible ? VISIBLE : INVISIBLE);
        int rightIcon = attributes.getResourceId(R.styleable.SettingItemView_right_icon, -1);
        if (rightIcon != -1) {
            mIvSettingRightIcon.setImageResource(rightIcon);
        }
    }

    /**
     * 左边图标
     *
     * @param attributes
     */
    private void initLeftIcon(TypedArray attributes) {
        //获取是否要显示左边图标
        boolean leftIconVisible = attributes.getBoolean(R.styleable.SettingItemView_left_icon_visible, true);
        mIvSettingLeftIcon.setVisibility(leftIconVisible ? VISIBLE : GONE);
        //设置左边图标
        int leftIcon = attributes.getResourceId(R.styleable.SettingItemView_left_icon, -1);
        if (leftIcon != -1) {
            mIvSettingLeftIcon.setImageResource(leftIcon);
        }
    }


    public TextView getTvSetting() {
        return mTvSetting;
    }

    public TextView getTvSettingDetail() {
        return mTvSettingDetail;
    }

    public ImageView getIvSettingLeftIcon() {
        return mIvSettingLeftIcon;
    }

    public ImageView getIvSettingRightIcon() {
        return mIvSettingRightIcon;
    }

    public void setTitleText(String title) {
        if (TextUtils.isEmpty(title)) {
            mTvSetting.setText("");
        } else {
            mTvSetting.setText(title);
        }
    }

    public void setTitleText(int resId) {
        setTitleText(UIUtils.getString(resId));
    }

    public void setDetailText(String detail) {
        if (TextUtils.isEmpty(detail)) {
            mTvSettingDetail.setText("");
        } else {
            mTvSettingDetail.setText(detail);
        }
    }

    public void setDetailText(int resId) {
        setDetailText(UIUtils.getString(resId));
    }

    public String getDetailText() {
        return mTvSettingDetail.getText().toString().trim();
    }
}
