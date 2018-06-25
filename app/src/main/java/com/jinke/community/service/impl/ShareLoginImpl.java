package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.UserLoginBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.IShareLogin;
import com.jinke.community.service.listener.IRequestListener;

import java.util.Map;

/**
 * Created by root on 17-7-22.
 */

public class ShareLoginImpl implements IShareLogin {

    @Override
    public void getUserLogin(Context mContext, Map<String, String> map, final IRequestListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<UserLoginBean>() {
            @Override
            public void onNext(UserLoginBean info) {
                listener.onSuccessListener(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onErrorListener(Code, Msg);
            }
        };

        HttpMethods.getInstance().getUserLogin(new ProgressSubscriber<HttpResult<UserLoginBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }
}
