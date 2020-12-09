package com.dpad.telematicsclientapp.netlibrary.newapp.entity

import com.google.gson.annotations.SerializedName


/**
 * ================================================
 * 作    者：wenbody
 * 版    本：1.0
 * 创建日期：2019/12/18 10:15
 * 描    述：
 * 修订历史：
 * ================================================
 */
data class LoginBean(
        @SerializedName("code")
        val code: String,
        @SerializedName("message")
        val message: String,
        @SerializedName("result")
        val result: Result,
        @SerializedName("total")
        val total: Int
) {
    data class Result(
            @SerializedName("authorityCode")
            val authorityCode: String,
            @SerializedName("initPassword")
            val initPassword: String,
            @SerializedName("isAgree")
            val isAgree: String,
            @SerializedName("isTServer")
            val isTServer: String,
            @SerializedName("positionName")
            val positionName: String,
            @SerializedName("token")
            val token: String,
            @SerializedName("userId")
            val userId: String,
            @SerializedName("userName")
            val userName: String,
            @SerializedName("userType")
            val userType: String,
            @SerializedName("burialPointUserType")
            val burialPointUserType: String,
            @SerializedName("userVehType")
            val userVehType: String,
            @SerializedName("vin")
            val vin: String,
            @SerializedName("saleSubmodeId")
            val saleSubmodeId: String?,
      @SerializedName("nickName")
            val nickName: String,
      @SerializedName("picUrl")
            val picUrl: String


    )
}