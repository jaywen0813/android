package com.dpad.telematicsclientapp.netlibrary.db;

import android.content.Context;

import java.util.List;

public class DataBeanUtil {
    /**
     *
     * @param userType 用户类型
     * @param code 埋点编码
     * @param status type 为1 传 in / out     type 为2传 可不传
     * @param type 传1 表示页面流转  2表示功能方法
     * @return
     */
    public static DataBean getBean(Context context,String userType, String code, String status, String type) {
        List<IndexBean> list =  new IndexDao(context).queryByCode("code");
        DataBean db_bean = new DataBean();
        if(list!=null&&list.size()>0){
            db_bean.setDescription(code);
            db_bean.setDistinctId("");
            db_bean.setUserType(userType);
            db_bean.setEvent(list.get(0).getDescription());
            db_bean.setSourceType("");
        }
        return db_bean;
    }
}
