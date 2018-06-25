package com.jinke.community.http.main;

import com.jinke.community.bean.ArrearsListBean;
import com.jinke.community.bean.AuthorizedVehicleBean;
import com.jinke.community.bean.AutoBindBean;
import com.jinke.community.bean.BreaKBean;
import com.jinke.community.bean.BrokeNoticeListBean;
import com.jinke.community.bean.BrokenDetailsBean;
import com.jinke.community.bean.BrokenNewsListBean;
import com.jinke.community.bean.BrokenPersonBean;
import com.jinke.community.bean.CityBean;
import com.jinke.community.bean.CommunityBean;
import com.jinke.community.bean.CommunityGPSBean;
import com.jinke.community.bean.CommunityListBean;
import com.jinke.community.bean.CustomerPhoneBean;
import com.jinke.community.bean.DefaultHouseBean;
import com.jinke.community.bean.EmptyObjectBean;
import com.jinke.community.bean.MsgBean;
import com.jinke.community.bean.StateBean;
import com.jinke.community.bean.acachebean.HouseValueBean;
import com.jinke.community.bean.HouseWithHoldBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.KeepPropertyBean;
import com.jinke.community.bean.LifeRecommendBean;
import com.jinke.community.bean.LifeSuperMarketBean;
import com.jinke.community.bean.NoteBean;
import com.jinke.community.bean.NoticeOneBean;
import com.jinke.community.bean.ParkInfoBean;
import com.jinke.community.bean.PayBean;
import com.jinke.community.bean.PaymentDetailsBean;
import com.jinke.community.bean.PaymentRecordBean;
import com.jinke.community.bean.PaymentVehicleDetailsBean;
import com.jinke.community.bean.PostItNoticeDetailBean;
import com.jinke.community.bean.PraiseresultBean;
import com.jinke.community.bean.PrepaidExpensesBean;
import com.jinke.community.bean.PropertyBean;
import com.jinke.community.bean.PropertyProgressBean;
import com.jinke.community.bean.PublicHouseBean;
import com.jinke.community.bean.RegisterLoginBean;
import com.jinke.community.bean.SetDefaultHouseBean;
import com.jinke.community.bean.UrlConfigBean;
import com.jinke.community.bean.UserCarBean;
import com.jinke.community.bean.UserCommunityBean;
import com.jinke.community.bean.UserInfo;
import com.jinke.community.bean.UserLoginBean;
import com.jinke.community.bean.WithHoldBreakBean;
import com.jinke.community.bean.WithHoldOpenBean;
import com.jinke.community.bean.WithholdingBean;
import com.jinke.community.bean.WorkingSortBean;
import com.jinke.community.bean.XiMoDriveListBean;
import com.jinke.community.bean.acachebean.CallCenterBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.bean.acachebean.LifeTopBannerBean;
import com.jinke.community.bean.acachebean.NoticeListBean;
import com.jinke.community.bean.RedCircleGroupBean;
import com.jinke.community.bean.acachebean.WeatherBean;

import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by liukun on 16/icon_cs3/9.
 */
public interface AppService {
    @FormUrlEncoded
    @POST("loginResgister/userLogin")
    Observable<HttpResult<UserLoginBean>> getUserLogin(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("public/sendCaptcha")
    Observable<HttpResult<UserLoginBean>> getVerificationPhone(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("loginResgister/register")
    Observable<HttpResult<RegisterLoginBean>> getRegisterData(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("house/autoBindHouse")
    Observable<HttpResult<AutoBindBean>> getAutoBindHouseData(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("community/getCityList")
    Observable<HttpResult<CityBean>> getCityListData(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("community/getCommunityList")
    Observable<HttpResult<CommunityListBean>> getCommunityListData(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("community/getCommunityListByGps")
    Observable<HttpResult<CommunityGPSBean>> getCommunityListByGpsData(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("community/queryCommunity")
    Observable<HttpResult<CommunityGPSBean>> getQueryCommunityData(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("house/addHouse")
    Observable<HttpResult<UserLoginBean>> getAddHouseData(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("public/checkCaptcha")
    Observable<HttpResult<UserLoginBean>> getCheckCaptchaData(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("house/getHouseList")
    Observable<HttpResult<HouseListBean>> getHouseListData(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("public/getPublicHouseList")
    Observable<HttpResult<PublicHouseBean>> getPublicHouseListData(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("house/setDeafultHouse")
    Observable<HttpResult<SetDefaultHouseBean>> getDefaultHouseData(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("house/saveHouseGrant")
    Observable<HttpResult<HouseListBean>> getSaveGrantUserData(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("public/getUserInfo")
    Observable<HttpResult<UserInfo>> getUserInfo(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("house/setBindDoorGuard")
    Observable<HttpResult<XiMoDriveListBean>> getBandingControl(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("public/getDefaultHouseInfo")
    Observable<HttpResult<DefaultHouseBean>> getDefaultHouseInfo(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("brokeNews/getBrokeNewsList")
    Observable<HttpResult<BrokenNewsListBean>> getBrokeNewsList(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("postIt/getPostItList")
    Observable<HttpResult<BrokenNewsListBean>> getPostItList(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("brokeNews/getBrokeNewsDetail")
    Observable<HttpResult<BrokenDetailsBean>> getBrokeNewsDetail(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("postIt/getPostItDetail")
    Observable<HttpResult<BrokenDetailsBean>> getPostItDetail(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("postIt/addPostIt")
    Observable<HttpResult<BrokenDetailsBean>> getPropertyAdd(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("public/getCallCenter")
    Observable<HttpResult<CallCenterBean>> getCallCenter(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("notice/getNoticeList")
    Observable<HttpResult<NoticeListBean>> getNoticeList(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("fangGuGu/getHouseValue")
    Observable<HttpResult<HouseValueBean>> getHouseValue(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("weather/getWeather")
    Observable<HttpResult<WeatherBean>> getWeather(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("notice/getBrokeNewsNoticeList")
    Observable<HttpResult<BrokeNoticeListBean>> getBrokeNewsNoticeList(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("notice/getPostItNoticeList")
    Observable<HttpResult<BrokeNoticeListBean>> getPostItNoticeList(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("userCenter/editUser")
    Observable<HttpResult<EmptyObjectBean>> getEditUserData(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("userCenter/getConfig")
    Observable<HttpResult<UrlConfigBean>> getUrlConfig(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("userCenter/addSuggest")
    Observable<HttpResult<EmptyObjectBean>> getAddSuggestData(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("userCenter/getCustomerPhone")
    Observable<HttpResult<CustomerPhoneBean>> getCustomerPhone(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("life/getTopAdvertising")
    Observable<HttpResult<LifeTopBannerBean>> getTopAdvertising(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("life/getTopNavigationOne")
    Observable<HttpResult<LifeTopBannerBean>> getTopNavigationOne(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("life/getTopNavigationTwo")
    Observable<HttpResult<LifeTopBannerBean>> getTopNavigationTwo(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("life/getRanking")
    Observable<HttpResult<LifeRecommendBean>> getRanking(@FieldMap Map<String, String> map, @Header("body_val") String sign);


    @FormUrlEncoded
    @POST("life/getBottomActivity")
    Observable<HttpResult<LifeTopBannerBean>> getBottomActivity(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("circle/sort")
    Observable<HttpResult<WorkingSortBean>> getWorkingSort(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("circle/update")
    Observable<HttpResult<WorkingSortBean>> getWorkingUpdate(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("circle/getAdvertising")
    Observable<HttpResult<LifeTopBannerBean>> getAdvertising(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("notice/getPostItNoticeDetail")
    Observable<HttpResult<PostItNoticeDetailBean>> getPostItNoticeDetail(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("payment/getArrearsList")
    Observable<HttpResult<ArrearsListBean>> getArrearsList(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("payment/getSignStatus")
    Observable<HttpResult<WithholdingBean>> getSignStatus(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("payment/getUnSign")
    Observable<HttpResult<WithholdingBean>> getUnSign(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("payment/doPayment")
    Observable<HttpResult<PayBean>> getDoPayment(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("payment/getPaymentRecordList")
    Observable<HttpResult<PaymentRecordBean>> getPaymentRecordList(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("payment/getPaymentRecordDetail")
    Observable<HttpResult<PaymentDetailsBean>> getPaymentRecordDetail(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("payment/getPrePayDetail")
    Observable<HttpResult<PaymentDetailsBean>> getPrePayDetail(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("payment/getPrePayList")
    Observable<HttpResult<PrepaidExpensesBean>> getPrePayList(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("userCenter/getPoint")
    Observable<HttpResult<PrepaidExpensesBean>> getPointData(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("payment/doPrePay")
    Observable<HttpResult<PayBean>> getDoPay(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("notice/getNoticeOne/")
    Observable<HttpResult<NoticeOneBean>> getNoticeOne(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("car/getCarLicensePlate")
    Observable<HttpResult<UserCarBean>> getMyCar(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("car/lockCar")
    Observable<HttpResult<EmptyObjectBean>> banVehicle(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("car/unlockCar")
    Observable<HttpResult<EmptyObjectBean>> unBanVehicle(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("car/getUserReservation")
    Observable<HttpResult<AuthorizedVehicleBean>> getAuthorizedVehicle(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("car/cancelReservation")
    Observable<HttpResult<EmptyObjectBean>> deleteAuthorized(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("car/getUserCarSpace")
    Observable<HttpResult<ParkInfoBean>> getParkInfo(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("car/getParking")
    Observable<HttpResult<ParkInfoBean>> getParkingInfo(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("car/setReservation")
    Observable<HttpResult<EmptyObjectBean>> addAuthorize(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("payment/doCarPay")
    Observable<HttpResult<PayBean>> getPayment(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("payment/getCarPaymentDetail")
    Observable<HttpResult<PaymentVehicleDetailsBean>> getPaymentVehicleDetail(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("car/getUserCarSpace")
    Observable<HttpResult<ParkInfoBean>> getParking(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("life/getSmartSupermarket")
    Observable<HttpResult<LifeSuperMarketBean>> getSuperMarket(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("house/getHouseList")
    Observable<HttpResult<HouseWithHoldBean>> getHouseWithHold(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("payment/getUnSign")
    Observable<HttpResult<WithHoldBreakBean>> withholdBreak(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("notice/getBrokeNewsNoticeList")
    Observable<HttpResult<BreaKBean>> getBreakList(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("brokeNews/jmOrderVote")
    Observable<HttpResult<PraiseresultBean>> praise(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("notice/getBrokeNewsNoticeList")
    Observable<HttpResult<NoticeListBean>> getStageBrokeList(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("brokeNews/getBrokeNewsByUserId")
    Observable<HttpResult<BrokenPersonBean>> getBrokePerson(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("payment/alisign")
    Observable<HttpResult<WithHoldOpenBean>> withHoldOpen(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("payment/getAgreementPayList")
    Observable<HttpResult<PaymentRecordBean>> getWithHoldRecorder(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("postIt/addPostItComments")
    Observable<HttpResult<EmptyObjectBean>> addPostItComments(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("postIt/getKeepPostItList")
    Observable<HttpResult<PropertyBean>> getKeepPostItList(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("postIt/getKeepPostItDetail")
    Observable<HttpResult<KeepPropertyBean>> getKeeperDetail(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("postIt/getHandleList")
    Observable<HttpResult<PropertyProgressBean>> getProgress(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("house/getUserCommunity")
    Observable<HttpResult<UserCommunityBean>> getCommunity(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("analytics/addAnaly")
    Observable<HttpResult<EmptyObjectBean>> addAnaly(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("lable/getLabelList")
    Observable<HttpResult<NoteBean>> getNote(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("lable/saveLabelUser")
    Observable<HttpResult<EmptyObjectBean>> setNote(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("news/getUserNews")
    Observable<HttpResult<RedCircleGroupBean>> getRedCircle(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("news/updateUserNews")
    Observable<HttpResult<EmptyObjectBean>> upDateCicle(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("privilege/getCommunityConfig")
    Observable<HttpResult<CommunityBean>> getConfig(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("houseTransfer/getUserCertified")
    Observable<HttpResult<StateBean>> getState(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("open/qrcode")
    Observable<HttpResult<String>> getQrCode(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("message/list")
    Observable<HttpResult<MsgBean>> getMsg(@FieldMap Map<String, String> map, @Header("body_val") String sign);

    @FormUrlEncoded
    @POST("message/updateIsRead")
    Observable<HttpResult<EmptyObjectBean>> upDateMsg(@FieldMap Map<String, String> map, @Header("body_val") String sign);
}
