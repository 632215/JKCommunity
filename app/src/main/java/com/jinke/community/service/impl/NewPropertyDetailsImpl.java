package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.KeepPropertyBean;
import com.jinke.community.bean.PropertyProgressBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.listener.NewPropertyDetailsListener;
import com.jinke.community.ui.activity.broken.NewPropertyDetailsActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2018/4/25.
 */

public class NewPropertyDetailsImpl {
    private Context mContext;

    public NewPropertyDetailsImpl(Context mContext) {
        this.mContext = mContext;
    }

    public void getKeeperDetail(Map map, final NewPropertyDetailsListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<KeepPropertyBean>() {
            @Override
            public void onNext(KeepPropertyBean info) {
                listener.getKeeperDetailSuccess(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getKeeperDetail(new ProgressSubscriber<HttpResult<KeepPropertyBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    public void getProgress(Map map, final NewPropertyDetailsListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<PropertyProgressBean>() {
            @Override
            public void onNext(PropertyProgressBean info) {
                if (info.getListObj() != null && info.getListObj().size() != 0)
                    listener.getProgressSuccess(info);
                else {
                    listener.onError("000", "节点为空");
                }
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getProgress(new ProgressSubscriber<HttpResult<PropertyProgressBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }
}
