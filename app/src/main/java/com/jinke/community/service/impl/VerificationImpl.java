package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.HouseBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.RegisterLoginBean;
import com.jinke.community.bean.UserLoginBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.IVerificationPhone;
import com.jinke.community.service.listener.BindHouseListener;
import com.jinke.community.service.listener.IVerificationListener;

import java.util.Map;

/**
 * Created by root on 17-8-1.
 */

public class VerificationImpl implements IVerificationPhone {
    private Context mContext;

    public VerificationImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getVerificationPhone(Map<String, String> map, final IVerificationListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<UserLoginBean>() {
            @Override
            public void onNext(UserLoginBean info) {
                listener.onCaptchaCode(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onErrorMsg(Code, Msg);
            }
        };

        HttpMethods.getInstance().getVerificationPhone(new ProgressSubscriber<HttpResult<UserLoginBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }


    @Override
    public void getRegisterData(Map<String, String> map, final  IVerificationListener listener) {
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
}
