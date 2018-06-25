package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.PaymentRecordBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.presenter.PaymentRecordPresenter;
import com.jinke.community.service.IPaymentRecordBiz;
import com.jinke.community.service.listener.IPaymentRecordListener;

import java.util.Map;

/**
 * Created by root on 17-8-19.
 */

public class PaymentRecordImpl implements IPaymentRecordBiz {
    private Context mContext;

    public PaymentRecordImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getPaymentRecordList(Map<String, String> map, final IPaymentRecordListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<PaymentRecordBean>() {
            @Override
            public void onNext(PaymentRecordBean info) {
                listener.onPaymentRecordList(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getPaymentRecordList(new ProgressSubscriber<HttpResult<PaymentRecordBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void getWithHoldRecorder(Map<String, String> map, final IPaymentRecordListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<PaymentRecordBean>() {
            @Override
            public void onNext(PaymentRecordBean info) {
                listener.getWithHoldRecorderNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getWithHoldRecorder(new ProgressSubscriber<HttpResult<PaymentRecordBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }
}
