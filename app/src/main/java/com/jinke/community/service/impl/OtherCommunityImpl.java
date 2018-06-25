package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.CityBean;
import com.jinke.community.bean.CommunityListBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.IOtherCommunityBiz;
import com.jinke.community.service.listener.IOtherCommunityListener;

import java.util.Map;

/**
 * Created by root on 17-8-2.
 */

public class OtherCommunityImpl implements IOtherCommunityBiz {
    Context mContext;

    public OtherCommunityImpl(Context mContext) {
        this.mContext = mContext;

    }

    @Override
    public void getCityList(Map<String, String> map, final IOtherCommunityListener listener) {

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
    public void getCommunityList(Map<String, String> map, final IOtherCommunityListener listener) {

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
