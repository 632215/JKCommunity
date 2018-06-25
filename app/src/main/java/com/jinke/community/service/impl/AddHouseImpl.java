package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.UserLoginBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.IAddHouseBiz;
import com.jinke.community.service.listener.IAddHouseListener;

import java.util.Map;

/**
 * Created by root on 17-8-2.
 */

public class AddHouseImpl implements IAddHouseBiz {
    private Context mContext;

    public AddHouseImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getVerificationPhone(Map<String, String> map, final IAddHouseListener listener) {
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
    public void addHouse(Map<String, String> map, final IAddHouseListener listener) {

        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<UserLoginBean>() {
            @Override
            public void onNext(UserLoginBean info) {
                listener.onSuccess("操作成功");
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onErrorMsg(Code, Msg);
            }
        };

        HttpMethods.getInstance().getAddHouseData(new ProgressSubscriber<HttpResult<UserLoginBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void checkCaptcha(Map<String, String> map, final IAddHouseListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<UserLoginBean>() {
            @Override
            public void onNext(UserLoginBean info) {
                listener.checkCaptcha(info);

            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onErrorMsg(Code, Msg);
            }
        };

        HttpMethods.getInstance().getCheckCaptchaData(new ProgressSubscriber<HttpResult<UserLoginBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }
}
