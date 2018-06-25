package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.CityBean;
import com.jinke.community.bean.CommunityGPSBean;
import com.jinke.community.bean.CommunityListBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.ISelectCommunityBiz;
import com.jinke.community.service.listener.ISelectCommunityListener;

import java.util.Map;

/**
 * Created by root on 17-8-2.
 */

public class SelectCommunityImpl implements ISelectCommunityBiz {
    private Context mContext;

    public SelectCommunityImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getCommunityListByGps(Map<String, String> map, final ISelectCommunityListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<CommunityGPSBean>() {
            @Override
            public void onNext(CommunityGPSBean info) {
                listener.getCommunityGpsData(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onErrorMsg(Code, Msg);
            }
        };

        HttpMethods.getInstance().getCommunityListByGps(new ProgressSubscriber<HttpResult<CommunityGPSBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }


    @Override
    public void getQueryCommunity(Map<String, String> map, final ISelectCommunityListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<CommunityGPSBean>() {
            @Override
            public void onNext(CommunityGPSBean info) {
                listener.getQueryCommunity(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onErrorMsg(Code, Msg);
            }
        };

        HttpMethods.getInstance().getQueryCommunityData(new ProgressSubscriber<HttpResult<CommunityGPSBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }


    @Override
    public void getCityList(Map<String, String> map, final ISelectCommunityListener listener) {

        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<CityBean>() {
            @Override
            public void onNext(CityBean info) {
                listener.onCityData(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onErrorMsg(Code, Msg);
            }
        };

        HttpMethods.getInstance().getCityListData(new ProgressSubscriber<HttpResult<CityBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }


    @Override
    public void getCommunityList(Map<String, String> map, final ISelectCommunityListener listener) {

        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<CommunityListBean>() {
            @Override
            public void onNext(CommunityListBean info) {
                listener.onCommunityListData(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onErrorMsg(Code, Msg);
            }
        };

        HttpMethods.getInstance().getCommunityListData(new ProgressSubscriber<HttpResult<CommunityListBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }
}
