package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.DefaultHouseBean;
import com.jinke.community.bean.acachebean.HouseValueBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.PraiseresultBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.bean.acachebean.NoticeListBean;
import com.jinke.community.bean.acachebean.WeatherBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.IHouseKeeperBiz;
import com.jinke.community.service.listener.IHouseKeeperListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 17-8-14.
 */

public class HouseKeeperImpl implements IHouseKeeperBiz {
    private Context mContext;

    public HouseKeeperImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getDefaultData(HashMap hashMap, final IHouseKeeperListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<DefaultHouseBean>() {
            @Override
            public void onNext(DefaultHouseBean info) {
                listener.onDefaultDataNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getDefaultHouseInfo(new ProgressSubscriber<HttpResult<DefaultHouseBean>>(nextListener, mContext), hashMap, MyApplication.creatSign(hashMap));
    }

    @Override
    public void getHouseList(Map map, final IHouseKeeperListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<HouseListBean>() {
            @Override
            public void onNext(HouseListBean info) {
                listener.getHouseListNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getHouseListData(new ProgressSubscriber<HttpResult<HouseListBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void getBreakList(Map<String, String> map, final IHouseKeeperListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<NoticeListBean>() {
            @Override
            public void onNext(NoticeListBean info) {
                listener.onNoticeList(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getNoticeList(new ProgressSubscriber<HttpResult<NoticeListBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void getHouseValue(Map<String, String> map, final IHouseKeeperListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<HouseValueBean>() {
            @Override
            public void onNext(HouseValueBean info) {
                listener.onHouseValue(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getHouseValue(new ProgressSubscriber<HttpResult<HouseValueBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void getWeatheInfo(Map<String, String> map, final IHouseKeeperListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<WeatherBean>() {
            @Override
            public void onNext(WeatherBean info) {
                listener.getWeatheInfoNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getWeather(new ProgressSubscriber<HttpResult<WeatherBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void praiseClick(Map map, final IHouseKeeperListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<PraiseresultBean>() {
            @Override
            public void onNext(PraiseresultBean info) {
                listener.praiseClickNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().praise(new ProgressSubscriber<HttpResult<PraiseresultBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }
}
