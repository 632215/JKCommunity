package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.AutoBindBean;
import com.jinke.community.bean.SetDefaultHouseBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.RegisterLoginBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.BindHouseBiz;
import com.jinke.community.service.listener.BindHouseListener;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/13.
 */

public class BindHouseImpl implements BindHouseBiz {
    private Context mContext;

    public BindHouseImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getRegisterData(Map<String, String> map, final BindHouseListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<RegisterLoginBean>() {
            @Override
            public void onNext(RegisterLoginBean info) {
                listener.getRegisterDataNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onErrorMsg(Code, Msg);
            }
        };

        HttpMethods.getInstance().getRegisterData(new ProgressSubscriber<HttpResult<RegisterLoginBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void autoBindHouse(Map<String, String> map, final BindHouseListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<AutoBindBean>() {
            @Override
            public void onNext(AutoBindBean info) {
                listener.autoBindHouseNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onErrorMsg(Code, Msg);
            }
        };

        HttpMethods.getInstance().getAutoBindHouseData(new ProgressSubscriber<HttpResult<AutoBindBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void setDefaultHouse(Map map, final BindHouseListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<SetDefaultHouseBean>() {
            @Override
            public void onNext(SetDefaultHouseBean info) {
                listener.setDefaultHouseNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onErrorMsg(Code, Msg);
            }
        };

        HttpMethods.getInstance().getDefaultHouseData(new ProgressSubscriber<HttpResult<SetDefaultHouseBean>>(nextListener, mContext), map, MyApplication.creatSign(map));

    }
}
