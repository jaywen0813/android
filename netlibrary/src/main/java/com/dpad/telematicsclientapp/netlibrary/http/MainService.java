package com.dpad.telematicsclientapp.netlibrary.http;

import com.dpad.crmclientapp.android.data.entity.CuscResult;
import com.dpad.crmclientapp.android.data.http.EncryptUtils.SecretResult;
import com.dpad.crmclientapp.android.modules.cddd.bean.CDDDEntity;
import com.dpad.crmclientapp.android.modules.certification.bean.QueryBean;
import com.dpad.crmclientapp.android.modules.ckbg.bean.CKBGNetEntity;
import com.dpad.crmclientapp.android.modules.czdh.model.entity.AddShouCang;
import com.dpad.crmclientapp.android.modules.czdh.model.entity.POISendResVO;
import com.dpad.crmclientapp.android.modules.czdh.model.entity.ShowShoucang;
import com.dpad.crmclientapp.android.modules.dzwl.model.entity.AdddzwlVO;
import com.dpad.crmclientapp.android.modules.dzwl.model.entity.DzwlVO;
import com.dpad.crmclientapp.android.modules.dzwl.model.entity.ShuoMingVO;
import com.dpad.crmclientapp.android.modules.internet_of_vehicles.bean.DriveBean;
import com.dpad.crmclientapp.android.modules.internet_of_vehicles.bean.HomeCarDataBean;
import com.dpad.crmclientapp.android.modules.internet_of_vehicles.bean.PostDriveBean;
import com.dpad.crmclientapp.android.modules.internet_of_vehicles.bean.TCarBean;
import com.dpad.crmclientapp.android.modules.internet_of_vehicles.bean.UserBean;
import com.dpad.crmclientapp.android.modules.internet_of_vehicles.bean.XTSListEntity;
import com.dpad.crmclientapp.android.modules.invoice.bean.AddInvoiceBean;
import com.dpad.crmclientapp.android.modules.invoice.bean.InvoiceDetailBean;
import com.dpad.crmclientapp.android.modules.invoice.bean.InvoiceHistoryBean;
import com.dpad.crmclientapp.android.modules.invoice.bean.InvoiceProductDetailBean;
import com.dpad.crmclientapp.android.modules.invoice.bean.InvoiceRuleBean;
import com.dpad.crmclientapp.android.modules.invoice.bean.OrderBean;
import com.dpad.crmclientapp.android.modules.invoice.bean.SendEmailBean;
import com.dpad.crmclientapp.android.modules.jyfw.model.entity.JJJYDetailEntity;
import com.dpad.crmclientapp.android.modules.jyfw.model.entity.JYDListEntity;
import com.dpad.crmclientapp.android.modules.jyfw.model.entity.ShengChengJYDEntity;
import com.dpad.crmclientapp.android.modules.llcx.model.entity.LlcxVO2;
import com.dpad.crmclientapp.android.modules.llcx.model.entity.WifiDetailVO;
import com.dpad.crmclientapp.android.modules.llcx.model.entity.YinYueDetailVO;
import com.dpad.crmclientapp.android.modules.llcx.model.entity.YuLeDetailVO;
import com.dpad.crmclientapp.android.modules.nearby.bean.AddCollectionBean;
import com.dpad.crmclientapp.android.modules.nearby.bean.CollectionBean;
import com.dpad.crmclientapp.android.modules.nearby.bean.QueryCollectionBean;
import com.dpad.crmclientapp.android.modules.nearby.bean.vehLocationVo;
import com.dpad.crmclientapp.android.modules.newapp.bean.AgreeMentBean;
import com.dpad.crmclientapp.android.modules.newapp.bean.FunctionBean;
import com.dpad.crmclientapp.android.modules.newapp.bean.HomeBean;
import com.dpad.crmclientapp.android.modules.newapp.bean.LoginBean;
import com.dpad.crmclientapp.android.modules.newapp.bean.SplashBean2;
import com.dpad.crmclientapp.android.modules.newapp.bean.TagBean;
import com.dpad.crmclientapp.android.modules.newapp.bean.UserCenterVo;
import com.dpad.crmclientapp.android.modules.newapp.bean.VcodeBean;
import com.dpad.crmclientapp.android.modules.paysdk.weixin.PayInfoback;
import com.dpad.crmclientapp.android.modules.sz.bean.NewAboutBean;
import com.dpad.crmclientapp.android.modules.sz.bean.SettingMsgBean;
import com.dpad.crmclientapp.android.modules.t_activation.model.entity.AddErShouCar;
import com.dpad.crmclientapp.android.modules.t_activation.model.entity.CardItem2;
import com.dpad.crmclientapp.android.modules.t_activation.model.entity.CardItem3;
import com.dpad.crmclientapp.android.modules.t_activation.model.entity.CardItem4;
import com.dpad.crmclientapp.android.modules.t_activation.model.entity.FaPiaoEntity;
import com.dpad.crmclientapp.android.modules.t_activation.model.entity.IsAgree;
import com.dpad.crmclientapp.android.modules.t_activation.model.entity.LookFpEntity;
import com.dpad.crmclientapp.android.modules.t_activation.model.entity.RenGongEntity;
import com.dpad.crmclientapp.android.modules.t_activation.model.entity.TlistEntity;
import com.dpad.crmclientapp.android.modules.t_activation.model.entity.Tsm;
import com.dpad.crmclientapp.android.modules.t_nearby.bean.SendToCarBean;
import com.dpad.crmclientapp.android.modules.t_nearby.bean.TCollectionBean;
import com.dpad.crmclientapp.android.modules.tsfx.bean.AddShouCangEntity;
import com.dpad.crmclientapp.android.modules.tsfx.bean.DeleteShouCangEntity;
import com.dpad.crmclientapp.android.modules.tsfx.bean.JXSEntity;
import com.dpad.crmclientapp.android.modules.tsfx.bean.ShouCangListEntity;
import com.dpad.crmclientapp.android.modules.tsfx.bean.TSFXEntity;
import com.dpad.crmclientapp.android.modules.tsfx.bean.TSFXPhone;
import com.dpad.crmclientapp.android.modules.wdcx.model.entity.SiteDetailDto;
import com.dpad.crmclientapp.android.modules.wdcx.model.entity.WdcxEntity2;
import com.dpad.crmclientapp.android.modules.wxby.kt_bean.DatesListBean;
import com.dpad.crmclientapp.android.modules.wxby.kt_bean.OrderSuccessBean;
import com.dpad.crmclientapp.android.modules.wxby.kt_bean.ReservationCarBean;
import com.dpad.crmclientapp.android.modules.wxby.kt_bean.ReservationKtBean;
import com.dpad.crmclientapp.android.modules.wxjdcx.model.entity.WXJDCXDetailInfo2;
import com.dpad.crmclientapp.android.modules.wxjdcx.model.entity.WXJDCXVideoEntity;
import com.dpad.crmclientapp.android.modules.xcjl.model.entity.DrivingDto;
import com.dpad.crmclientapp.android.modules.xcjl.model.entity.GUIJIEntity;
import com.dpad.crmclientapp.android.modules.xxzx.bean.MsgStatus;
import com.dpad.crmclientapp.android.modules.xxzx.bean.OutMessageBean;
import com.dpad.crmclientapp.android.modules.xxzx.bean.TextMessageBean;
import com.dpad.crmclientapp.android.modules.yckz.bean.YckzZipEntity;
import com.dpad.crmclientapp.android.modules.yy.model.entity.BookDetailEntity;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * ================================================
 * 作    者：booob
 * 版    本：1.0
 * 创建日期：2018-07-17-0017 上午 09:55
 * 描    述：
 * 修订历史：
 * ================================================
 */
public interface MainService {
    /**
     * 地图收藏列表
     *
     * @return
     */
    @POST("maintenance/app-select-map")
    Observable<CollectionBean> getMapList(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    /**
     * 取消收藏
     *
     * @return
     */
    @POST("maintenance/delete-app-map")
    Observable<CollectionBean> cancelCollection(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    /**
     * 添加收藏
     *
     * @return
     */
    @POST("maintenance/insert-app-map")
    Observable<AddCollectionBean> addCollection(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


    //核对是否有收藏过
    @POST("maintenance/poi-valid-iscollected")
    Observable<QueryCollectionBean> QueryCollection(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


    /**
     * 车联网首页的数据
     *
     * @param headerMap
     * @param requestBody
     * @return
     */
    @POST("change/veh-contion")
    Observable<HomeCarDataBean> getCarData(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


    /**
     * 车联网获取T车列表
     *
     * @param headerMap
     * @param requestBody
     * @return
     */
    @POST("veh/veh-Tlist")
    Observable<TCarBean> getTcarList(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


    /**
     * 获取爱车列表
     *
     * @param headerMap
     * @param requestBody
     * @return
     */
    @POST("users/query-veh-userid")
    Observable<TCarBean> getCarList(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    /**
     * 实名认证
     *
     * @param headerMap
     * @param requestBody
     * @return
     */
    @POST("authentication/realName-submit")
    Observable<TCarBean> realNameCertification(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //行车记录
    @POST("DrivingRecord/driving-line")
    Observable<DrivingDto> getXcjlListData(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //行程记录里面点的轨迹
    @POST("travelPath/environmentDrivingDto")
    Observable<GUIJIEntity> getXcjlGuiJi(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //开关行程轨迹
    @POST("DrivingRecord/trajectory-swith")
    Observable<SecretResult> getOpenOrClose(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //删除行程轨迹
    @POST("DrivingRecord/del-trips")
    Observable<SecretResult> getdelete(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    /**
     *
     * 环保驾驶数据
     *
     * @param headerMap
     * @param requestBody
     * @return
     */
    @POST("Environment/driving-search")
    Observable<DriveBean> getDriveData(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


    //定位到车
    @POST("veh/find-my-car")
    Observable<vehLocationVo> dingweicar(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //列表页下发到车
    @POST("Collect/send-poicollect")
    Observable<POISendResVO> sendcar(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    /**
     * T车地图收藏列表
     *
     * @return
     */
    @POST("Collect/find-poicollect")
    Observable<TCollectionBean> getTMapList(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    /**
     * 取消 T车 收藏
     *
     * @return
     */
    @POST("Collect/delete-poicollect")
    Observable<CollectionBean> cancelTCollection(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    /**
     * 添加 T车 收藏
     *
     * @return
     */
    @POST("Collect/add-poicollect")
    Observable<AddCollectionBean> addTCollection(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    /**
     * /**
     * 发送到车
     *
     * @return
     */
    @POST("Collect/send-poicollect")
    Observable<SendToCarBean> sendToCar(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


    /**
     * 实名认证进度
     *
     * @return
     */

    @POST("authentication/realName-query")
    Observable<QueryBean> realNameQuery(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //列表收藏
    @POST("Collect/add-poicollect")
    Observable<AddShouCang> addshoucang(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


    //判断是否收藏
    @POST("Collect/findall-poicollect")
    Observable<ShowShoucang> showshoucang(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //维修进度详情
    @POST("site-order/select-by-code")
    Observable<WXJDCXDetailInfo2> wxjdcxdetail(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //维修视频
    @POST("site-order/get-vedioUrl")
    Observable<WXJDCXVideoEntity> wxjdcxvideo(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //T车激活首页
    @POST("vehicle/TwithContract-TList")
    Observable<CardItem2> tjihuo(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //T车激活首页   2019.02.15版
    @POST("vehicle/TwithContract-TList")
    Observable<CardItem3> tjihuo3(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //T激活首页   2020年9月7日 16:51:17  包含二手车
    @POST("usedCar/TwithContract-TList")
    Observable<CardItem4> tjihuo4(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //T激活两行列表，拨打电话页面的
    @POST("Vehicle/getTNode-byVin")
    Observable<TlistEntity> tList(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //T激活发票和合同上传页面
    @POST("update/T-Node")
    Observable<FaPiaoEntity> fapiaohetong(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //T激活发票和合同查看详情
    @POST("contracts/find-img")
    Observable<LookFpEntity> lookfp(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //个人中心
    @POST("users/user-with-otherinfo")
    Observable<UserBean> getUserData(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //流量查询首页
    @POST("flow/traffic-query")
    Observable<LlcxVO2> llcx(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //音乐流量详情列表
    @POST("remain/getApnOneMusicDataByPage")
    Observable<YinYueDetailVO> yinyuedetail(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


    //广播流量详情列表
    @POST("flow/recreation-query")
    Observable<YuLeDetailVO> yuledetail(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //wifi流量详情列表
    @POST("flow/wifi-query")
    Observable<WifiDetailVO> wifidetail(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //环保驾驶分享
    @POST("share/add-info")
    Observable<PostDriveBean> postSharePic(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //环保驾驶和充电页面的小贴士
    @POST("upcode/selectby-codetype")
    Observable<XTSListEntity> getxiaotieshi(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //数据埋点
    @POST("logs/app/send-msg")
    Observable<PostDriveBean> sendData(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


    //订单列表
    @POST("getgoods/getgoods-bytoken")
    Observable<OrderBean> getInvoiceList(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //产品详情
    @POST("getInvoice/getInvoice-byInvoice")
    Observable<InvoiceProductDetailBean> getInvoiceProduct(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


    //开票历史
    @POST("invoice/get-invoice-list")
    Observable<InvoiceHistoryBean> getOrderList(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //新增发票
    @POST("invoice/add-invoice")
    Observable<AddInvoiceBean> addInvoice(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //发票详情
    @POST("invoice/get-invoice-list")
    Observable<InvoiceDetailBean> getInvoiceDetail(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //发送邮件
    @POST("invoice/send-email")
    Observable<SendEmailBean> sendToEmail(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //获取发票规则
    @POST("upcode/selectby-codetype")
    Observable<InvoiceRuleBean> getInvoiceRule(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


    //电子围栏首页列表获取
    @POST("geofence/getGeofences")
    Observable<DzwlVO> dzwl(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //电子围栏列表删除
    @POST("geofence/deleteGeofences")
    Observable<DzwlVO> dzwldelete(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //新增电子围栏
    @POST("geofence/createOrUpdateGeofence")
    Observable<AdddzwlVO> adddzwl(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //修改电子围栏
    @POST("geofence/createOrUpdateGeofence")
    Observable<AdddzwlVO> updatedzwl(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //电子围栏说明   二手车服务协议 也是这个接口，  只是入参不一样
    @POST("upcode/selectby-codetype")
    Observable<ShuoMingVO> dzwlsm(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //新增二手车申请单
    @POST("secondHandCar/addApplication")
    Observable<AddErShouCar> ershou(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //OCR转人工审核
    @POST("secondHandCar/modifyOcrStatus")
    Observable<RenGongEntity> rengong(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //T服务协议阅读 同意按钮
    @POST("app-users/t-protocolupdate")
    Observable<IsAgree> isagree(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //T服务协议内容
    @POST("tservice/query-protocol")
    Observable<Tsm> txieyism(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //网点列表查询接口
    @POST("sites/city-code-site-selection")
    Observable<WdcxEntity2> load(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //获取顾问列表的接口
    @POST("sites/query-site-only")
    Observable<CuscResult<SiteDetailDto>> loadguwen(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //设置专属顾问
    @POST("users/update-photo")
    Observable<CuscResult<String>> updateguwen(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //App用户各个分类下最新的一条消息
    @POST("msg/select-new-messageinfo")
    Observable<OutMessageBean> getNewMessageData(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //获取消息列表
    @POST("msg/select-messageinfo")
    Observable<TextMessageBean> getMessageData(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //删除历史消息或者更新阅读状态
    @POST("msg/update-messageinfo")
    Observable<TextMessageBean> updateMessageState(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //查询设置的状态的集合
    @POST("msg/select-set-status")
    Observable<MsgStatus> getSettingStatus(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //开启关闭消息推送
    @POST("msg/setting")
    Observable<SettingMsgBean> setMessageStatus(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


    //鸣笛闪灯
    @POST("app-rc/remoteControlTurnLightPlusHorn")
    Observable<SecretResult> remoteControlTurnLightPlusHorn(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //3.2.	Ntsp远程控制-车门
    @POST("app-rc/remoteControlDoors")
    Observable<SecretResult> remoteControlDoors(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //3.3.	NTSP远程控制立即开关空调
    @POST("app-rc/remoteControlAir")
//    @POST("app-rc/ocr/test")
    Observable<SecretResult> remoteControlAir(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //3.4.	Ntsp远程控制-立即开启充电
    @POST("app-rc/remoteControlCharging")
    Observable<SecretResult> remoteControlCharging(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //3.5.	Ntsp远程控制-远程操作结果反馈接口
    @POST("app-rc/remoteControl")
    Observable<SecretResult> remoteControlResult(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //3.6.	验证智能设备是否绑定
    @POST("app-rc/validate/bind")
    Observable<SecretResult> confirmDeviceBinded(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //3.7.	绑定设备
    @POST("app-rc/bind/device")
    Observable<SecretResult> bindDevice(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //3.8.	智能设备列表查询
    @POST("app-rc/query/device")
    Observable<SecretResult> queryDevice(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //3.9.	解除绑定
    @POST("app-rc/untying/device")
    Observable<SecretResult> untyingDevice(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //3.10.	发送验证码
    @POST("app-rc/send/sms")
    Observable<SecretResult> sendVcode(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //3.11.	验证验证码是否正确
    @POST("app-rc/verification/code")
    Observable<SecretResult> checkVcode(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //3.12.	PIN码设置
    @POST("app-rc/install/pin")
    Observable<SecretResult> setPin(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //3.13.	验证PIN码是否正确
    @POST("app-rc/verification/pin")
    Observable<SecretResult> checkPin(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //3.14.	修改PIN码
    @POST("app-rc/update/pin")
    Observable<SecretResult> updatePin(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //3.15.	用户问题答案保存接口
    @POST("secret-settings/saveOrUpdate")
    Observable<SecretResult> saveQuestion(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //3.17.	用户问题答案查询接口
    @POST("secret-settings/findByUserId")
    Observable<SecretResult> findByUserId(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //3.18.	密码问题校验接口
    @POST("secret-settings/checkAnswer")
    Observable<SecretResult> checkAnswer(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //3.19.	密码问题查询接口
    @POST("secret-settings/getQuestion")
    Observable<SecretResult> getQuestion(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //3.21.	密码问题修改接口
    @POST("secret-settings/updateQuestion")
    Observable<SecretResult> updaeQuestion(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //3.22.	密码问题删除接口
    @POST("secret-settings/deleteQuestion")
    Observable<SecretResult> deleteQuestion(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //3.23.	车主绑定关系更换接口
    @POST("userVehicle/updateUserVehicle")
    Observable<SecretResult> updateUserVehicle(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //3.24.	T车
    @POST("app-rc/veh/veh-Tlist")
    Observable<SecretResult> getVehTlist(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //3.25.	设置PIN码与安全问题
    @POST("app-rc/pin/safe-problem")
    Observable<SecretResult> setPinAndQuestion(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //远程控制首页，车辆快照
    @POST("app-rc/vehSnapShot")
    Observable<SecretResult> setRemoteControlCar(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //远控动态显示充电入口的
    @POST("app-rc/charging-flag")
    Observable<SecretResult> setCharging(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //3.25.	用户是否设置PIN码
    @POST("app-rc/select/userPin")
    Observable<SecretResult> checkHasPin(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //查询预约空调的列表
    @POST("app-rc/appointAirList")
    Observable<SecretResult> chaxunList(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


    //上传身份证照片
    @Multipart
    @POST("app-rc/ocr/id-card")
    Observable<SecretResult> upLoadPic(@HeaderMap Map<String, String> headerMap, @Body RequestBody Body);

    //上传身份证照片
    @Multipart
    @POST("app-rc/ocr/id-card")
    Observable<SecretResult> upLoadPic(@HeaderMap Map<String, String> headerMap, @PartMap Map<String, RequestBody> maps);

    //添加预约空调
    @POST("app-rc/remoteControlAirForTiming")
    Observable<SecretResult> addAir(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //修改预约空调
    @POST("app-rc/remoteControlAirForTiming")
    Observable<SecretResult> updateAir(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //删除预约空调
    @POST("app-rc/remoteControlAirForTiming")
    Observable<SecretResult> deleteAir(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


    //实时刷新车辆状态
    @POST("app-rc/remoteControlVehinfo")
    Observable<SecretResult> setMyCarMessage(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //预约充电
    @POST("app-rc/remoteControlChargingForTiming")
    Observable<SecretResult> chongdian(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


    //车况报告
    @POST("vehicle/health-report")
    Observable<CKBGNetEntity> getCKBGNet(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


    //预约单详情
    @POST("bespeakInfo/by-id")
    Observable<BookDetailEntity> yydxq(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //预约单取消
    @POST("app-appointments/logic/logicdelete-bespeak")
    Observable<CuscResult<String>> qxyy(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //预约单删除
    @POST("app-appointments/logic/logicdelete-bespeak")
    Observable<CuscResult<String>> scyy(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


    /**
     * 维保预约页面获取网点列表的集合
     *
     * @param headerMap
     * @param requestBody
     * @return
     */
    @POST("sites/city-code-site-selection")
    Observable<ReservationKtBean> getSiteList(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    /**
     * 保养首页数据获取
     *
     * @param headerMap
     * @param requestBody
     * @return
     */
    @POST("appointments/select/select-res-time")
    Observable<ReservationCarBean> getLoveCar(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


    @POST("app-appointments/insert/insert-bespeak")
    Observable<OrderSuccessBean> commitProductOrder(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    /**
     * 获取预约时间数据
     *
     * @param headerMap
     * @param requestBody
     * @return
     */
    @POST("appointments/select/select-time-res")
    Observable<DatesListBean> getDatesList(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


    //新能源APP 探索发现-经销商接口
    @POST("sites/discovery-site-selection")
    Observable<JXSEntity> loadjxs(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //新能源APP 紧急救援部分 2020年1月22日 12:39:34
    @POST("sites/city-code-site-selection")
    Observable<JJJYDetailEntity> loadjjjy(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //新能源APP 紧急救援，打电话生成救援单部分 2020年1月22日 13:39:34
    @POST("rescues/telephone-rescueinfo")
    Observable<ShengChengJYDEntity> shengchengjyd(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);
    //新能源APP  紧急救援里面 查询救援单列表  2020年1月22日 14:25:12
    @POST("rescues/query-rescue")
    Observable<JYDListEntity> jydlist(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);
    /**
     * 登录
     *
     * @param headerMap
     * @param requestBody
     * @return
     */
    @POST("login/user-login")
    Observable<LoginBean> login(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


    /**
     * 重置密码
     *
     * @param headerMap
     * @param requestBody
     * @return
     */
    @POST("users/reset-password")
    Observable<LoginBean> reSetPsw(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    /**
     * 修改密码获取验证码
     *
     * @param headerMap
     * @param requestBody
     * @return
     */
    @POST("users/find-password-sendsms")
    Observable<VcodeBean> getVcode(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    /**
     * 登录获取验证码
     *
     * @param headerMap
     * @param requestBody
     * @return
     */
    @POST("users/send-login-sms")
    Observable<VcodeBean> getLoginCode(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    /**
     * 获取首页数据
     *
     * @param headerMap
     * @param requestBody
     * @return
     */
    @POST("index/show-multiple")
    Observable<HomeBean> getHomeData(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

 /**
     * 获取首页数据
     *
     * @param headerMap
     * @param requestBody
     * @return
     */
    @POST("index/show-noToken")
    Observable<HomeBean> getHomeDataWithNoToken(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


    /**
     * 获取功能列表的数据
     *
     * @param headerMap
     * @param requestBody
     * @return
     */
    @POST("fun/selectByUserId")
    Observable<FunctionBean> getFunctionList(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    /**
     * 增加功能列表
     *
     * @param headerMap
     * @param requestBody
     * @return
     */
    @POST("fun/insertOrUpdate")
    Observable<FunctionBean> addFunction(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //探索发现 查询收藏 列表接口 2019年12月27日 15:39:13
    @POST("maintenance/select-user-map")
    Observable<TSFXEntity> tsfxmap(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //探索发现 添加收藏
    @POST("maintenance/insert-app-map")
    Observable<AddShouCangEntity> NevAddShouCang(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


    //探索发现 修改收藏
    @POST("maintenance/update-app-map")
    Observable<AddShouCangEntity> NevupdateShouCang(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //探索发现  删除收藏
    @POST("maintenance/delete-app-map")
    Observable<DeleteShouCangEntity> NevcancelTCollection(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //设置家和公司的新接口   2020年3月13日 17:30:08
    @POST("maintenance/update-family-company")
    Observable<AddShouCangEntity> setHomeOrCompany(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);
    /**
     * token登录
     *
     * @param headerMap
     * @param requestBody
     * @return
     */
    @POST("login/user-login")
    Observable<LoginBean> tokenLogin(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


    /**
     * 启动页活动列表
     *
     * @param headerMap
     * @param requestBody
     * @return
     */
    @POST("splash/select-by-city-time")
    Observable<SplashBean2> getSplashActivity(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


    //探索发现 收藏页面的查询收藏 列表接口 2019年12月30日 15:39:13
    @POST("maintenance/select-user-map")
    Observable<ShouCangListEntity> tsfxshoucang(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //探索发现首页  查询常用电话的接口
    @POST("nev/select-phone")
    Observable<TSFXPhone> tsfxphone(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


    /**
     * 获取个人中心数据
     *
     * @param headerMap
     * @param requestBody
     * @return
     */
    @POST("app-users/timeline")
    Observable<UserCenterVo> getUserCenter(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    /**
     * 获取个人中心数据
     *
     * @param headerMap
     * @param requestBody
     * @return
     */
    @POST("users/update-photo")
    Observable<FunctionBean> updateUserInfo(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


      /**
     * 获取个人中心数据
     *
     * @param headerMap
     * @param requestBody
     * @return
     */
    @POST("app-users/label")
    Observable<TagBean> getTagList(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


    //爱车昵称
    @POST("vehicle/update-plateNumber")
    Observable<SecretResult> getACNC(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


    //充电订单
    @POST("charge/get-records")
    Observable<CDDDEntity> getcdddListData(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //远控下载zip包
    @POST("appfile/select-file")
    Observable<YckzZipEntity> picDownload(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


    /**
     * 获取用户协议
     *
     * @param headerMap
     * @param requestBody
     * @return
     */
    @POST("maintenance/select-servcer-up-codelist")
    Observable<AgreeMentBean> getAgreeMent(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    /**
     * 关于我们
     *
     * @param headerMap
     * @param requestBody
     * @return
     */
    @POST("maintenance/t-select-up-codelist")
    Observable<NewAboutBean> getAboutData(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);
    /**
     * 上传经纬度
     *
     * @param headerMap
     * @param requestBody
     * @return
     */
    @POST("siteInfo/getGdStatus")
    Observable<SecretResult> getGdStatus(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    @POST("pay/wx-pay")
    Observable<PayInfoback> getwxpay(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);

    //二手车用户上传个人信息
    @POST("usedCar/addCarUse")
    Observable<SecretResult> addCarUse(@HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);
    //补充资料上传
    @POST("secondHandCar/insertAdditon")
    Observable<SecretResult> insertAdditon(@HeaderMap Map<String, String> headerMap, @Part MultipartBody.Part[] parts, @Part("applyId") RequestBody applyId, @Part("remarks") RequestBody remarks);

}
