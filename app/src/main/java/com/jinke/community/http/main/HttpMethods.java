package com.jinke.community.http.main;

import com.jinke.community.application.MyApplication;
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
import com.jinke.community.bean.HouseWithHoldBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.KeepPropertyBean;
import com.jinke.community.bean.LifeRecommendBean;
import com.jinke.community.bean.LifeSuperMarketBean;
import com.jinke.community.bean.MsgBean;
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
import com.jinke.community.bean.RedCircleGroupBean;
import com.jinke.community.bean.RegisterLoginBean;
import com.jinke.community.bean.SetDefaultHouseBean;
import com.jinke.community.bean.StateBean;
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
import com.jinke.community.bean.acachebean.HouseValueBean;
import com.jinke.community.bean.acachebean.LifeTopBannerBean;
import com.jinke.community.bean.acachebean.NoticeListBean;
import com.jinke.community.bean.acachebean.WeatherBean;
import com.jinke.community.utils.AndroidUtils;
import com.lidroid.xutils.http.RequestParams;

import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by liukun on 16/icon_cs3/9.
 */
public class HttpMethods {
    public Retrofit retrofit;
    public AppService service;
    //  public static final String BASE_URL = "https://tq-service.com/app/Home/";//试点环境
    public static String BASE_URL = "http://api-development.tq-service.com/v2/tqapp/jk_community/uc/";//测试服务器
//         public static String BASE_URL = "https://api.tq-service.com/v3/tqapp/jk_community/uc/";//正式服务器
//  public static final String BASE_URL = "http://10.15.208.195:8090/jk_community/uc/";//向本地
    //上传文件图片地址
    public static final String BASE_FILE_UPLOAD = "http://www.tq-service.com/oss/OssController/uploadImage/";
    private static final int DEFAULT_TIMEOUT = 120;
    private OkHttpClient.Builder builder;

    private static class SingletonHolder {
        private static final HttpMethods INSTANCE = new HttpMethods();
    }

    public static HttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public AppService getService() {
        if (service == null)
            new Throwable("AppService is null ");
        return service;
    }

    //构造方法私有
    private HttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.addInterceptor(new LogInterceptor());
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request().newBuilder()
                        .addHeader("machine", AndroidUtils.getUnId())
                        .build();
                return chain.proceed(request);
            }
        });
        builder.addInterceptor(new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY));

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        service = retrofit.create(AppService.class);

    }

    public <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    //Map转RequestParams并计算签名
    public static RequestParams MapToParams(Map map) {
        RequestParams params = new RequestParams();
        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> entry = it.next();
            params.addBodyParameter(entry.getKey(), entry.getValue());
        }
        params.addHeader("body_val", MyApplication.creatSign(map));
        return params;
    }

    /**
     * 获取支付参数
     *
     * @param subscriber
     * @param map
     */
    public void getUserLogin(Subscriber<HttpResult<UserLoginBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getUserLogin(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 手机号验证
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getVerificationPhone(Subscriber<HttpResult<UserLoginBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getVerificationPhone(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 注册
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getRegisterData(Subscriber<HttpResult<RegisterLoginBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getRegisterData(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 自动绑定房屋
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getAutoBindHouseData(Subscriber<HttpResult<AutoBindBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getAutoBindHouseData(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取城市列表
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getCityListData(Subscriber<HttpResult<CityBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getCityListData(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取城市列表
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getCommunityListData(Subscriber<HttpResult<CommunityListBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getCommunityListData(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 根据经纬度获取小区列表
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getCommunityListByGps(Subscriber<HttpResult<CommunityGPSBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getCommunityListByGpsData(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 根据根据关键字查询小区列表
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getQueryCommunityData(Subscriber<HttpResult<CommunityGPSBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getQueryCommunityData(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 添加房屋绑定
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getAddHouseData(Subscriber<HttpResult<UserLoginBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getAddHouseData(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 校验验证码
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getCheckCaptchaData(Subscriber<HttpResult<UserLoginBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getCheckCaptchaData(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取房屋列表
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getHouseListData(Subscriber<HttpResult<HouseListBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getHouseListData(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 公共房屋列表
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getPublicHouseListData(Subscriber<HttpResult<PublicHouseBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getPublicHouseListData(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 设置默认房屋
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getDefaultHouseData(Subscriber<HttpResult<SetDefaultHouseBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getDefaultHouseData(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 添加房屋授权
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getSaveGrantUserData(Subscriber<HttpResult<HouseListBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getSaveGrantUserData(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取用户信息
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getUserInfoData(Subscriber<HttpResult<UserInfo>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getUserInfo(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 绑定门禁
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getBandingControl(Subscriber<HttpResult<XiMoDriveListBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getBandingControl(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取默认房屋列表
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getDefaultHouseInfo(Subscriber<HttpResult<DefaultHouseBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getDefaultHouseInfo(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 爆料列表
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getBrokeNewsListData(Subscriber<HttpResult<BrokenNewsListBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getBrokeNewsList(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 报事列表
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getPostItList(Subscriber<HttpResult<BrokenNewsListBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getPostItList(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 爆料详情
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getBrokeNewsDetail(Subscriber<HttpResult<BrokenDetailsBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getBrokeNewsDetail(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 报事详情页面
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getPostItDetail(Subscriber<HttpResult<BrokenDetailsBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getPostItDetail(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 添加报事
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getPropertyAdd(Subscriber<HttpResult<BrokenDetailsBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getPropertyAdd(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 添加报事
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getCallCenter(Subscriber<HttpResult<CallCenterBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getCallCenter(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 添加报事
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getNoticeList(Subscriber<HttpResult<NoticeListBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getNoticeList(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 房屋估价
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getHouseValue(Subscriber<HttpResult<HouseValueBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getHouseValue(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 房屋估价
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getWeather(Subscriber<HttpResult<WeatherBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getWeather(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 添加报事
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getBrokeNewsNoticeList(Subscriber<HttpResult<BrokeNoticeListBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getBrokeNewsNoticeList(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 添加报事
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getPostItNoticeList(Subscriber<HttpResult<BrokeNoticeListBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getPostItNoticeList(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 更新用户信息
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getEditUserData(Subscriber<HttpResult<EmptyObjectBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getEditUserData(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 添加报事
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getUrlConfig(Subscriber<HttpResult<UrlConfigBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getUrlConfig(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 添加报事
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getAddSuggestData(Subscriber<HttpResult<EmptyObjectBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getAddSuggestData(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 添加报事
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getCustomerPhone(Subscriber<HttpResult<CustomerPhoneBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getCustomerPhone(map, sign);
        toSubscribe(observable, subscriber);
    }


    /**
     * 添加报事
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getTopAdvertising(Subscriber<HttpResult<LifeTopBannerBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getTopAdvertising(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取导航栏一列表
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getTopNavigationOne(Subscriber<HttpResult<LifeTopBannerBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getTopNavigationOne(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取导航栏一列表
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getTopNavigationTwo(Subscriber<HttpResult<LifeTopBannerBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getTopNavigationTwo(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取金榜题名的列表
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getRanking(Subscriber<HttpResult<LifeRecommendBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getRanking(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取底部活动列表
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getBottomActivity(Subscriber<HttpResult<LifeTopBannerBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getBottomActivity(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取底部活动列表
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getWorkingSort(Subscriber<HttpResult<WorkingSortBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getWorkingSort(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取底部活动列表
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getWorkingUpdate(Subscriber<HttpResult<WorkingSortBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getWorkingUpdate(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取圈子顶部广告列表
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getAdvertising(Subscriber<HttpResult<LifeTopBannerBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getAdvertising(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 物业公告明细
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getPostItNoticeDetail(Subscriber<HttpResult<PostItNoticeDetailBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getPostItNoticeDetail(map, sign);
        toSubscribe(observable, subscriber);
    }


    /**
     * 欠费列表
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getArrearsList(Subscriber<HttpResult<ArrearsListBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getArrearsList(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 查询代扣签约状态接口
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getSignStatus(Subscriber<HttpResult<WithholdingBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getSignStatus(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 查询代扣签约状态接口
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getUnSign(Subscriber<HttpResult<WithholdingBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getUnSign(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 立即缴费
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getDoPayment(Subscriber<HttpResult<PayBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getDoPayment(map, sign);
        toSubscribe(observable, subscriber);
    }


    /**
     * 缴费记录列表
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getPaymentRecordList(Subscriber<HttpResult<PaymentRecordBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getPaymentRecordList(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 缴费明细
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getPaymentRecordDetail(Subscriber<HttpResult<PaymentDetailsBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getPaymentRecordDetail(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 预存明细
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getPrePayDetail(Subscriber<HttpResult<PaymentDetailsBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getPrePayDetail(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 预存余额列表
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getPrePayList(Subscriber<HttpResult<PrepaidExpensesBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getPrePayList(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 预存余额列表
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getPointData(Subscriber<HttpResult<PrepaidExpensesBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getPointData(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 预存余额列表
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getDoPay(Subscriber<HttpResult<PayBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getDoPay(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 动态列表
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getNoticeOne(Subscriber<HttpResult<NoticeOneBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getNoticeOne(map, sign);
        toSubscribe(observable, subscriber);
    }


    /**
     * 获取用户车辆信息
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getMyCar(Subscriber<HttpResult<UserCarBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getMyCar(map, sign);
        toSubscribe(observable, subscriber);
    }


    /**
     * 绑定用户车辆
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void banVehicle(Subscriber<HttpResult<EmptyObjectBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.banVehicle(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 解绑用户车辆
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void unBanVehicle(Subscriber<HttpResult<EmptyObjectBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.unBanVehicle(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取授权车辆信息
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getAuthorizedVehicle(Subscriber<HttpResult<AuthorizedVehicleBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getAuthorizedVehicle(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 取消授权
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void deleteAuthorized(Subscriber<HttpResult<EmptyObjectBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.deleteAuthorized(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 停车场信息
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getParkInfo(Subscriber<HttpResult<ParkInfoBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getParkInfo(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取用户所在小区是否存在停车场系统(新)
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getParkingInfo(Subscriber<HttpResult<ParkInfoBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getParkingInfo(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 授权
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void addAuthorize(Subscriber<HttpResult<EmptyObjectBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.addAuthorize(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 车位月租付款
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getPayment(Subscriber<HttpResult<PayBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getPayment(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 车位充值明细
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getPaymentVehicleDetail(Subscriber<HttpResult<PaymentVehicleDetailsBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getPaymentVehicleDetail(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 停车场信息
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getParking(Subscriber<HttpResult<ParkInfoBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getParking(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 无人超市可用性
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getSuperMarket(Subscriber<HttpResult<LifeSuperMarketBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getSuperMarket(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取房屋列表(带签约状态)
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getHouseWithHold(Subscriber<HttpResult<HouseWithHoldBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getHouseWithHold(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 代扣解约
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void withholdBreak(Subscriber<HttpResult<WithHoldBreakBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.withholdBreak(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 首页报事列表
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getBreakList(Subscriber<HttpResult<BreaKBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getBreakList(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 点赞
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void praise(Subscriber<HttpResult<PraiseresultBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.praise(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取平台爆料列表
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getStageBrokeList(Subscriber<HttpResult<NoticeListBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getStageBrokeList(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取个人爆料列表
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getBrokePerson(Subscriber<HttpResult<BrokenPersonBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getBrokePerson(map, sign);
        toSubscribe(observable, subscriber);
    }


    /**
     * 开通代扣
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void withHoldOpen(Subscriber<HttpResult<WithHoldOpenBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.withHoldOpen(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 代扣记录
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getWithHoldRecorder(Subscriber<HttpResult<PaymentRecordBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getWithHoldRecorder(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 发表评价
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void addPostItComments(Subscriber<HttpResult<EmptyObjectBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.addPostItComments(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 报事列表-对大管家
     *
     * @param subscriber 订阅者
     * @param map        请求参数
     */
    public void getKeepPostItList(Subscriber<HttpResult<PropertyBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getKeepPostItList(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 报事详情-对大管家
     */
    public void getKeeperDetail(Subscriber<HttpResult<KeepPropertyBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getKeeperDetail(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 报事详情节点信息-对大管家
     */
    public void getProgress(Subscriber<HttpResult<PropertyProgressBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getProgress(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取当前用户所属小区列表
     */
    public void getCommunity(Subscriber<HttpResult<UserCommunityBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getCommunity(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 埋点addAnaly
     */
    public void addAnaly(Subscriber<HttpResult<EmptyObjectBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.addAnaly(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取标签
     *
     * @param subscriber
     * @param map
     * @param sign
     */
    public void getNote(Subscriber<HttpResult<NoteBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getNote(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 设置标签
     *
     * @param subscriber
     * @param map
     * @param sign
     */
    public void setNote(Subscriber<HttpResult<EmptyObjectBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.setNote(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取红点信息
     *
     * @param subscriber
     * @param map
     * @param sign
     */
    public void getRedCircle(Subscriber<HttpResult<RedCircleGroupBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getRedCircle(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 更新红点信息
     *
     * @param subscriber
     * @param map
     * @param sign
     */
    public void upDateCicle(Subscriber<HttpResult<EmptyObjectBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.upDateCicle(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取试点小区配置
     *
     * @param subscriber
     * @param map
     * @param sign
     */
    public void getConfig(Subscriber<HttpResult<CommunityBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getConfig(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取房屋审核状态
     *
     * @param subscriber
     * @param map
     * @param sign
     */
    public void getState(Subscriber<HttpResult<StateBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getState(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 二维码开门
     *
     * @param subscriber
     * @param map
     * @param sign
     */
    public void getQrCode(Subscriber<HttpResult<String>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getQrCode(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 消息中心
     *
     * @param subscriber
     * @param map
     * @param sign
     */
    public void getMsg(Subscriber<HttpResult<MsgBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.getMsg(map, sign);
        toSubscribe(observable, subscriber);
    }

    /**
     * 消息中心——标记已读
     *
     * @param subscriber
     * @param map
     * @param sign
     */
    public void upDateMsg(Subscriber<HttpResult<EmptyObjectBean>> subscriber, Map<String, String> map, String sign) {
        Observable observable = service.upDateMsg(map, sign);
        toSubscribe(observable, subscriber);
    }
}
