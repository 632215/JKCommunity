package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.PayBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.VehiclePayBiz;
import com.jinke.community.service.listener.IWithholdingListener;
import com.jinke.community.service.listener.VehiclePayListener;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/23.
 */

public class VehiclePayImpl implements VehiclePayBiz {
    private Context mContext;

    public VehiclePayImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getPayment(Map<String, String> map, final VehiclePayListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<PayBean>() {
            @Override
            public void onNext(PayBean info) {
                listener.getPaymentNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getPayment(new ProgressSubscriber<HttpResult<PayBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }
}
