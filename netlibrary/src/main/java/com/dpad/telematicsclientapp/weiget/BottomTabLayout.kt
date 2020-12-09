package com.dpad.crmclientapp.android.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.LinearLayout

import com.dpad.telematicsclientapp.netlibrary.R
import pl.droidsonroids.gif.GifDrawable
import pl.droidsonroids.gif.GifImageView

/**
 * ================================================
 * 作    者：wenbody
 * 版    本：1.0
 * 创建日期：2019/12/10 13:51
 * 描    述：
 * 修订历史：
 * ================================================
 */
class BottomTabLayout @JvmOverloads constructor(

        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : LinearLayout(context, attrs, defStyleAttr), View.OnClickListener {

    private lateinit var gifTab1: GifImageView
    private lateinit var gifTab2: GifImageView
    private lateinit var gifTab3: GifImageView
    private lateinit var gifTab4: GifImageView
    private lateinit var gifTab5: GifImageView

    private lateinit var tabLl1: LinearLayout
    private lateinit var tabLl2: FrameLayout
    private lateinit var tabLl3: LinearLayout
    private lateinit var tabLl4: LinearLayout
    private lateinit var tabLl5: LinearLayout

    private lateinit var onTabClick: OnTabClick
    private var selectPosition = 0
    private val gifImages = arrayListOf(
            GifDrawable.createFromResource(resources, R.mipmap.tab1),
            GifDrawable.createFromResource(resources, R.mipmap.tab2),
            GifDrawable.createFromResource(resources, R.mipmap.tab3),
            GifDrawable.createFromResource(resources, R.mipmap.tab4),
            GifDrawable.createFromResource(resources, R.mipmap.tab5)
    )

    private val gifUnSelects = arrayListOf<Int>(
            R.mipmap.find_icon,
            R.mipmap.message_icon,
            R.mipmap.home_icon,
            R.mipmap.mall_icon,
            R.mipmap.user_icon
    )
    private var tabViews = arrayListOf<GifImageView>()


    interface OnTabClick {
        fun onTabSelected(position: Int)
        fun needLogin(position: Int): Boolean
    }

    init {
        for (index in gifImages) {
            index!!.loopCount = 1
        }
        initView()

        selectTabImageByPosition(selectPosition)
    }

    @SuppressLint("InflateParams")
    private fun initView() {
        val view = LayoutInflater.from(context).inflate(R.layout.bottom_tab_layout, null)
        val layoutParams =
                LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        view.layoutParams = layoutParams
        gifTab1 = view.findViewById(R.id.tab1)
        gifTab2 = view.findViewById(R.id.tab2)
        gifTab3 = view.findViewById(R.id.tab3)
        gifTab4 = view.findViewById(R.id.tab4)
        gifTab5 = view.findViewById(R.id.tab5)

        tabLl1 = view.findViewById(R.id.tabLl1)
        tabLl2 = view.findViewById(R.id.tabLl2)
        tabLl3 = view.findViewById(R.id.tabLl3)
        tabLl4 = view.findViewById(R.id.tabLl4)
        tabLl5 = view.findViewById(R.id.tabLl5)
        tabViews.add(gifTab1)
        tabViews.add(gifTab2)
        tabViews.add(gifTab3)
        tabViews.add(gifTab4)
        tabViews.add(gifTab5)
        addView(view)
        tabLl1.setOnClickListener(this)
        tabLl2.setOnClickListener(this)
        tabLl3.setOnClickListener(this)
        tabLl4.setOnClickListener(this)
        tabLl5.setOnClickListener(this)

    }

    fun setOnTabClick(onTabClick: OnTabClick) {
        this.onTabClick = onTabClick
    }

    fun selectTab(position: Int) {
        selectPosition = position
        selectTabImageByPosition(selectPosition)
        onTabClick.onTabSelected(selectPosition)
    }

    private fun selectTabImageByPosition(position: Int) {

        for (index in tabViews.indices) {
            if (index == position) {
                tabViews[position].setImageDrawable(gifImages[position])
                gifImages[position]?.reset()
            } else {
                tabViews[index].setImageResource(gifUnSelects[index])
            }
        }
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.tabLl1 -> {
                selectPosition = 0
            }
            R.id.tabLl2 -> {
                selectPosition = 1
            }
            R.id.tabLl3 -> {
                selectPosition = 2
            }
            R.id.tabLl4 -> {
                selectPosition = 3
            }
            R.id.tabLl5 -> {
                selectPosition = 4
            }
        }
        if (onTabClick.needLogin(selectPosition)) {
            onTabClick.onTabSelected(selectPosition)
            selectTabImageByPosition(selectPosition)
        }
    }

}