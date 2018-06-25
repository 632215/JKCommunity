package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.BrokenDetailsBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.KeepPropertyBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.presenter.BrokenDetailsPresent;
import com.jinke.community.service.IBrokenDetailsBiz;
import com.jinke.community.service.listener.IBrokenDetailsListener;

import java.util.Map;

/**
 * Created by root on 17-8-11.
 */

public class BrokenDetailsImpl implements IBrokenDetailsBiz {
    Context mContext;

    public BrokenDetailsImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getBrokeNewsDetail(Map<String, String> map, final IBrokenDetailsListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<BrokenDetailsBean>() {
            @Override
            public void onNext(BrokenDetailsBean info) {
                listener.onSuccess(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().getBrokeNewsDetail(new ProgressSubscriber<HttpResult<BrokenDetailsBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void getPostItDetail(Map<String, String> map, final IBrokenDetailsListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<BrokenDetailsBean>() {
            @Override
            public void onNext(BrokenDetailsBean info) {
                listener.onPropertyInfo(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().getPostItDetail(new ProgressSubscriber<HttpResult<BrokenDetailsBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    public void getKeeperDetail(Map map, final IBrokenDetailsListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<KeepPropertyBean>() {
            @Override
            public void onNext(KeepPropertyBean info) {
                listener.getKeeperDetailNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getKeeperDetail(new ProgressSubscriber<HttpResult<KeepPropertyBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }
}
