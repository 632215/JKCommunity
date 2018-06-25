package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.ParkInfoBean;
import com.jinke.community.bean.ParkInfoSelectBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.PropertyPaymentBiz;
import com.jinke.community.service.listener.PropertyPaymentListener;

import java.util.Map;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by Administrator on 2017/11/18.
 */

public class PropertyPaymentImpl implements PropertyPaymentBiz {
    private Context mContext;

    public PropertyPaymentImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getHouseList(Map map, final PropertyPaymentListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<HouseListBean>() {
            @Override
            public void onNext(HouseListBean info) {
                listener.getHouseListNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onErrorMsg(Code, Msg);
            }
        };

        HttpMethods.getInstance().getHouseListData(new ProgressSubscriber<HttpResult<HouseListBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void getParkInfo(Map map, final PropertyPaymentListener listener) {
        SubscriberOnNextListener subscriberOnNextListener = new SubscriberOnNextListener<ParkInfoBean>() {
            @Override
            public void onNext(ParkInfoBean info) {
                listener.getParkInfoNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                    listener.onErrorMsg(Code, Msg);
            }
        };
        HttpMethods.getInstance().getParkInfo(new ProgressSubscriber<HttpResult<ParkInfoBean>>(subscriberOnNextListener, mContext), map, MyApplication.creatSign(map));
    }
}
