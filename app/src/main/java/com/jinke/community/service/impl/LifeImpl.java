package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.LifeRecommendBean;
import com.jinke.community.bean.LifeSuperMarketBean;
import com.jinke.community.bean.acachebean.LifeTopBannerBean;
import com.jinke.community.bean.PassCodeBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.ILifBiz;
import com.jinke.community.service.listener.ILifListener;
import com.jinke.community.utils.GsonUtil;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.util.Map;

/**
 * Created by root on 17-8-16.
 */

public class LifeImpl implements ILifBiz {
    private Context mContext;

    public LifeImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getTopAdvertising(Map<String, String> map, final ILifListener listener) {

        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<LifeTopBannerBean>() {
            @Override
            public void onNext(LifeTopBannerBean info) {
                listener.onTopAdvertising(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getTopAdvertising(new ProgressSubscriber<HttpResult<LifeTopBannerBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void getTopNavigationOne(Map<String, String> map, final ILifListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<LifeTopBannerBean>() {
            @Override
            public void onNext(LifeTopBannerBean info) {
                listener.onTopNavigationOne(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getTopNavigationOne(new ProgressSubscriber<HttpResult<LifeTopBannerBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void getTopNavigationTwo(Map<String, String> map, final ILifListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<LifeTopBannerBean>() {
            @Override
            public void onNext(LifeTopBannerBean info) {
                listener.onNavigationTwo(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getTopNavigationTwo(new ProgressSubscriber<HttpResult<LifeTopBannerBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void getRanking(Map<String, String> map, final ILifListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<LifeRecommendBean>() {
            @Override
            public void onNext(LifeRecommendBean info) {
                listener.onRanking(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getRanking(new ProgressSubscriber<HttpResult<LifeRecommendBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void getBottomActivity(Map<String, String> map, final ILifListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<LifeTopBannerBean>() {
            @Override
            public void onNext(LifeTopBannerBean info) {
                listener.onBottomActivity(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getBottomActivity(new ProgressSubscriber<HttpResult<LifeTopBannerBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }
}
