package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.AddAuthorizedBiz;
import com.jinke.community.service.listener.AddAuthorizedListener;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/21.
 */

public class AddAuthorizedImpl implements AddAuthorizedBiz {
    private Context mContext;
    public AddAuthorizedImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void addAuthorization(Map<String, String> map, final AddAuthorizedListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<HouseListBean>() {
            @Override
            public void onNext(HouseListBean info) {
                listener.addAuthorizationNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onErrorMsg(Code, Msg);
            }
        };

        HttpMethods.getInstance().getSaveGrantUserData(new ProgressSubscriber<HttpResult<HouseListBean>>(nextListener, mContext), map, MyApplication.creatSign(map));

    }
}
