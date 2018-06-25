package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.StateBean;
import com.jinke.community.bean.acachebean.NoticeListBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.listener.AuthenticationTipsListener;

import java.util.Map;

/**
 * Created by Administrator on 2018/5/30.
 */

public class AuthenticationTipsImpl {
    private Context mContext;

    public AuthenticationTipsImpl(Context mContext) {
        this.mContext = mContext;
    }

    public void getState(Map map,final AuthenticationTipsListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<StateBean>() {
            @Override
            public void onNext(StateBean info) {
                listener.getStateNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getStateError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getState(new ProgressSubscriber<HttpResult<StateBean>>(nextListener, mContext), map, MyApplication.creatSign(map));

    }
}
