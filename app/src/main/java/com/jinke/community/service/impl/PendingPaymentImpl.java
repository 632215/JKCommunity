package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.ArrearsListBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.WithholdingBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.IPendingPaymentBiz;
import com.jinke.community.service.listener.IPendingPaymentListener;

import java.util.Map;

/**
 * Created by root on 17-8-18.
 */

public class PendingPaymentImpl implements IPendingPaymentBiz {
    private Context mContext;

    public PendingPaymentImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getArrearsList(Map<String, String> map, final IPendingPaymentListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<ArrearsListBean>() {
            @Override
            public void onNext(ArrearsListBean info) {
                listener.onArrearsList(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getArrearsList(new ProgressSubscriber<HttpResult<ArrearsListBean>>(nextListener, mContext), map, MyApplication.creatSign(map));

    }

    @Override
    public void getSignStatus(Map<String, String> map, final IPendingPaymentListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<WithholdingBean>() {
            @Override
            public void onNext(WithholdingBean info) {
                listener.onWithholdingInfo(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onTopLayout(Code, Msg);
            }
        };
        HttpMethods.getInstance().getSignStatus(new ProgressSubscriber<HttpResult<WithholdingBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void getSignUn(Map<String, String> map, final IPendingPaymentListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<WithholdingBean>() {
            @Override
            public void onNext(WithholdingBean info) {
                listener.onUnSign(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getUnSign(new ProgressSubscriber<HttpResult<WithholdingBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }


}
