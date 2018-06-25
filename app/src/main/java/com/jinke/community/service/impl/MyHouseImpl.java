package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.SetDefaultHouseBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.IMyHouseBiz;
import com.jinke.community.service.listener.IMyHouseListener;

import java.util.Map;

/**
 * Created by root on 17-8-3.
 */

public class MyHouseImpl implements IMyHouseBiz {
    Context mContext;

    public MyHouseImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getHouseListData(Map<String, String> map, final IMyHouseListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<HouseListBean>() {
            @Override
            public void onNext(HouseListBean info) {
                listener.onSuccess(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onErrorMsg(Code, Msg);
            }
        };

        HttpMethods.getInstance().getHouseListData(new ProgressSubscriber<HttpResult<HouseListBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void getDefaultHouseData(Map<String, String> map, final IMyHouseListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<SetDefaultHouseBean>() {
            @Override
            public void onNext(SetDefaultHouseBean info) {
                listener.onDefaultHouse(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onErrorMsg(Code, Msg);
            }
        };
        HttpMethods.getInstance().getDefaultHouseData(new ProgressSubscriber<HttpResult<SetDefaultHouseBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }
}
