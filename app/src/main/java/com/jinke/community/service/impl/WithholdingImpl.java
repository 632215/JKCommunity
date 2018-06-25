package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.PayBean;
import com.jinke.community.bean.WithholdingBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.IWithholdingBiz;
import com.jinke.community.service.listener.IWithholdingListener;

import java.util.Map;

/**
 * Created by root on 17-8-18.
 */

public class WithholdingImpl implements IWithholdingBiz {


    private Context mContext;

    public WithholdingImpl(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 立即缴费
     *
     * @param map
     * @param listener
     */
    @Override
    public void getDoPayment(Map<String, String> map, final IWithholdingListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<PayBean>() {
            @Override
            public void onNext(PayBean info) {
                listener.onDoPayMent(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getDoPayment(new ProgressSubscriber<HttpResult<PayBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    /**
     * 预存缴费
     *
     * @param map
     * @param listener
     */
    @Override
    public void getDoPay(Map<String, String> map, final IWithholdingListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<PayBean>() {
            @Override
            public void onNext(PayBean info) {
                listener.onDoPayMent(info);

            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getDoPay(new ProgressSubscriber<HttpResult<PayBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void getSignState(Map map, final IWithholdingListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<WithholdingBean>() {
            @Override
            public void onNext(WithholdingBean info) {
                listener.getSignStateNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getSignStatus(new ProgressSubscriber<HttpResult<WithholdingBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }
}
