package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.acachebean.LifeTopBannerBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.ISocialCircleBiz;
import com.jinke.community.service.listener.ISocialCircleListener;

import java.util.Map;

/**
 * Created by root on 17-8-17.
 */

public class SocialCircleImpl implements ISocialCircleBiz {
    private Context mContext;

    public SocialCircleImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getAdvertising(Map<String, String> map, final ISocialCircleListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<LifeTopBannerBean>() {
            @Override
            public void onNext(LifeTopBannerBean info) {
                if (info != null && info.getCode() != null)
                    listener.onAdvertising(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().getAdvertising(new ProgressSubscriber<HttpResult<LifeTopBannerBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }
}
