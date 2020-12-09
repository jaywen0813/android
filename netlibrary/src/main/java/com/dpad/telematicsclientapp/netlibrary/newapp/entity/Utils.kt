package com.dpad.telematicsclientapp.netlibrary.newapp.entity

import cn.droidlover.xdroidmvp.kit.Kits
import com.dpad.crmclientapp.android.MainApplicaton
import com.dpad.crmclientapp.android.util.PreferenceUtils
import com.dpad.crmclientapp.android.util.utils.Constant
import com.dpad.telematicsclientapp.mvp.kit.Kits
import com.dpad.telematicsclientapp.netlibrary.MainApplicaton
import com.dpad.telematicsclientapp.util.PreferenceUtils
import com.dpad.telematicsclientapp.util.utils.Constant
import com.google.gson.Gson
import com.socks.library.KLog

/**
 * ================================================
 * 作    者：wenbody
 * 版    本：1.0
 * 创建日期：2019/8/20 10:36
 * 描    述：""未设置, 0关闭,1打开,3需要弹出指纹
 * 修订历史：
 * ================================================
 */
object Utils {

    private val gson: Gson = Gson()

    /**
     * 获取保存的指纹开启状态数据
     */

    fun getFingerStatus(): String {
        val s = PreferenceUtils.getInstance(MainApplicaton.getInstance()).getString(Constant.USE_FINGERPRINT_KEY)
        if (Kits.Empty.check(s)) {
            return ""
        }
        val datas = gson.fromJson(s, FingerPrintBean::class.java)
        for (mValue in datas.list) {
            KLog.e("tag", mValue.userId + "------" + mValue.status)
            if (mValue.userId == MainApplicaton.LOGINRESULTVO.userId) {
                return mValue.status
            }
        }
        return ""
    }

    /**
     * 保存指纹开始状态的数据
     */
    fun setFingerStatus(status: String) {
        val fingerPrintBean: FingerPrintBean
        val s = PreferenceUtils.getInstance(MainApplicaton.getInstance()).getString(Constant.USE_FINGERPRINT_KEY)
        if (Kits.Empty.check(s)) {
            fingerPrintBean = FingerPrintBean(mutableListOf(FingerPrintBean.StatusBean(MainApplicaton.LOGINRESULTVO.userId, status)))
            PreferenceUtils.getInstance(MainApplicaton.getInstance()).setString(Constant.USE_FINGERPRINT_KEY, gson.toJson(fingerPrintBean))
            return
        } else {
            fingerPrintBean = gson.fromJson(s, FingerPrintBean::class.java)
            for (mValue in fingerPrintBean.list) {
                if (mValue.userId == MainApplicaton.LOGINRESULTVO.userId) {
                    mValue.status = status
                    PreferenceUtils.getInstance(MainApplicaton.getInstance()).setString(Constant.USE_FINGERPRINT_KEY, gson.toJson(fingerPrintBean))
                    return
                }
            }
            fingerPrintBean.list.add(FingerPrintBean.StatusBean(MainApplicaton.LOGINRESULTVO.userId, status))
            PreferenceUtils.getInstance(MainApplicaton.getInstance()).setString(Constant.USE_FINGERPRINT_KEY, gson.toJson(fingerPrintBean))
        }
    }

    /**
     * 检查指纹是否开始
     */
    fun checkStatus(): Boolean {
        return when (getFingerStatus()) {
            "1" -> true
            else -> false
        }
    }
}