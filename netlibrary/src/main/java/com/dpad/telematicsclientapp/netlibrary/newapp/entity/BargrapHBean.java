package com.dpad.telematicsclientapp.netlibrary.newapp.entity;

import java.io.Serializable;
import java.util.List;

/**
 * ================================================
 * 作    者：booob
 * 版    本：1.0
 * 创建日期：2018-11-07-0007 15:32
 * 描    述：
 * 修订历史：
 * ================================================
 */
public class BargrapHBean implements Serializable {

    private boolean needReDraw = false;

    private List<Data> dataList;

    private int currentItem;

    public int getCurrentItem() {
        return currentItem;
    }

    public void setCurrentItem(int currentItem) {
        this.currentItem = currentItem;
    }

    public boolean isNeedReDraw() {
        return needReDraw;
    }

    public void setNeedReDraw(boolean needReDraw) {
        this.needReDraw = needReDraw;
    }

    public List<Data> getDataList() {
        return dataList;
    }

    public void setDataList(List<Data> dataList) {
        this.dataList = dataList;
    }

    public static class Data implements Serializable {
        private String precentage;//百分比

        private float data;//所占比例

        private String mile;//公里数

        public String getMile() {
            return mile;
        }

        public void setMile(String mile) {
            this.mile = mile;
        }

        public String getPrecentage() {
            return precentage;
        }

        public void setPrecentage(String precentage) {
            this.precentage = precentage;
        }

        public float getData() {
            return data;
        }

        public void setData(float data) {
            this.data = data;
        }
    }
}
