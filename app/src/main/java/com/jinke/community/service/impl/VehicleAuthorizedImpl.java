package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.EmptyObjectBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.ParkInfoBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.VehicleAuthorizedBiz;
import com.jinke.community.service.listener.VehicleAuthorizedListener;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/17.
 */

public class VehicleAuthorizedImpl implements VehicleAuthorizedBiz {
    private Context mContext;

    public VehicleAuthorizedImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getParkInfo(Map map, final VehicleAuthorizedListener listener) {
        SubscriberOnNextListener subscriberOnNextListener = new SubscriberOnNextListener<ParkInfoBean>() {
            @Override
            public void onNext(ParkInfoBean info) {
                listener.getParkInfoonNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.error(Msg);
            }
        };
        HttpMethods.getInstance().getParkingInfo(new ProgressSubscriber<HttpResult<ParkInfoBean>>(subscriberOnNextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void addAuthorize(Map map, final VehicleAuthorizedListener listener) {
        SubscriberOnNextListener subscriberOnNextListener = new SubscriberOnNextListener<EmptyObjectBean>() {
            @Override
            public void onNext(EmptyObjectBean info) {
                listener.addAuthorizeonNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.error(Msg);
            }
        };
        HttpMethods.getInstance().addAuthorize(new ProgressSubscriber<HttpResult<EmptyObjectBean>>(subscriberOnNextListener, mContext), map, MyApplication.creatSign(map));
    }
}
