package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.acachebean.CallCenterBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.CallBiz;
import com.jinke.community.service.listener.CallListener;

import java.util.Map;

/**
 * Created by Administrator on 2018/3/27.
 */

public class CallImpl implements CallBiz {
    private Context mContext;

    public CallImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getPhone(Map map, final CallListener listener) {
        SubscriberOnNextListener subscriberOnNextListener = new SubscriberOnNextListener<CallCenterBean>() {
            @Override
            public void onNext(CallCenterBean info) {
                listener.getPhoneNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getCallCenter(new ProgressSubscriber<HttpResult<CallCenterBean>>(subscriberOnNextListener, mContext), map, MyApplication.creatSign(map));
    }
}
