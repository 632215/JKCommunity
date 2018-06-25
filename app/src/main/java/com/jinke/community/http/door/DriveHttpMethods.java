package com.jinke.community.http.door;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.EmptyObjectBean;
import com.jinke.community.bean.HouseListInfo;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.ListDateInfo;
import com.jinke.community.bean.PasswordInfo;
import com.jinke.community.bean.ReasonBean;
import com.jinke.community.bean.XiMoDriveListBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.http.main.LogInterceptor;

import java.io.IOException;
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
 * Created by root on 17-8-21.
 */

public class DriveHttpMethods {
//        public static String BASE_URL = "http://men-api.tq-service.com/OpenPlatform/platform/";//运行环境
    public static String BASE_URL = "http://dev-men-api.tq-service.com/OpenPlatform/platform/";//测试服务器

    private static final int DEFAULT_TIMEOUT = 120;

    private Retrofit retrofit;
    private DoorAppService service;
    //封装header
    public static boolean HEADER = false;

    //构造方法私有
    private DriveHttpMethods() {
        //手动创建一个OkHttpClient并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        builder.addInterceptor(new LogInterceptor());
        builder.addInterceptor(new HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY));

        //添加头文件
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder request = chain.request().newBuilder();
                if (HEADER) {
                    request.addHeader("unionId", String.valueOf(MyApplication.getBaseUserBean().getUid()));
                    request.addHeader("appId", AppConfig.OPEN_APPID);
                }
                return chain.proceed(request.build());
            }
        });

        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(BASE_URL)
                .build();
        service = retrofit.create(DoorAppService.class);
    }

    //在访问HttpMethods时创建单例
    private static class SingletonHolder {
        private static final DriveHttpMethods INSTANCE = new DriveHttpMethods();
    }

    //获取单例
    public static DriveHttpMethods getInstance() {
        return SingletonHolder.INSTANCE;
    }


    //获取单例
    public static DriveHttpMethods getInstance(boolean isHeader) {
        HEADER = isHeader;
        return SingletonHolder.INSTANCE;
    }


    private <T> void toSubscribe(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }


    /**
     * 获取设备列表
     *
     * @param subscriber
     * @param map
     */
    public void getDeviceListDate(Subscriber<HttpResult<XiMoDriveListBean>> subscriber, Map<String, String> map) {
        Observable observable = service.getDeviceList(map);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取设备列表
     *
     * @param subscriber
     * @param map
     */
    public void getDateTimeData(Subscriber<HttpResult<ListDateInfo>> subscriber, Map<String, String> map) {
        Observable observable = service.getDateTime(map);
        toSubscribe(observable, subscriber);
    }


    /**
     * 获取设备列表
     *
     * @param subscriber
     * @param map
     */
    public void getOpenLogDate(Subscriber<HttpResult<EmptyObjectBean>> subscriber, Map<String, String> map) {
        Observable observable = service.getOpenLog(map);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取设备列表
     *
     * @param subscriber
     * @param map
     */
    public void getPurposeListDate(Subscriber<HttpResult<ReasonBean>> subscriber, Map<String, String> map) {
        Observable observable = service.getPurposeList(map);
        toSubscribe(observable, subscriber);
    }

    /**
     * 获取设备列表
     *
     * @param subscriber
     * @param map
     */
    public void getHouseListDate(Subscriber<HttpResult<HouseListInfo>> subscriber, Map<String, String> map) {
        Observable observable = service.getHouseList(map);
        toSubscribe(observable, subscriber);
    }


    /**
     * 获取设备列表
     *
     * @param subscriber
     * @param map
     */
    public void getSaveCode(Subscriber<HttpResult<PasswordInfo>> subscriber, Map<String, String> map) {
        Observable observable = service.getSaveCode(map);
        toSubscribe(observable, subscriber);
    }
}
