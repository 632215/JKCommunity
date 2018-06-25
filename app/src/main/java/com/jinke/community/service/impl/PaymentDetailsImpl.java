package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.PaymentDetailsBean;
import com.jinke.community.bean.PaymentVehicleDetailsBean;
import com.jinke.community.bean.PrePayDetailBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.OnRequestListener;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.IPaymentDetailsBiz;
import com.jinke.community.service.listener.PaymentDetailsListener;

import java.util.Map;

/**
 * Created by root on 17-8-19.
 */

public class PaymentDetailsImpl implements IPaymentDetailsBiz {
    Context mContext;

    public PaymentDetailsImpl(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 预存明细
     * @param map
     * @param listener
     */
    @Override
    public void getPrePayDetail(Map<String, String> map, final OnRequestListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<PaymentDetailsBean>() {
            @Override
            public void onNext(PaymentDetailsBean info) {
                listener.onSuccess(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getPrePayDetail(new ProgressSubscriber<HttpResult<PaymentDetailsBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void getPaymentRecordDetail(Map<String, String> map, final OnRequestListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<PaymentDetailsBean>() {
            @Override
            public void onNext(PaymentDetailsBean info) {
                listener.onSuccess(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getPaymentRecordDetail(new ProgressSubscriber<HttpResult<PaymentDetailsBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }


    @Override
    public void getPaymentVehicleDetail(Map<String, String> map, final PaymentDetailsListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<PaymentVehicleDetailsBean>() {
            @Override
            public void onNext(PaymentVehicleDetailsBean info) {
                listener.getVehicleDetailNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getVehicleDetailError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getPaymentVehicleDetail(new ProgressSubscriber<HttpResult<PaymentVehicleDetailsBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }
}
