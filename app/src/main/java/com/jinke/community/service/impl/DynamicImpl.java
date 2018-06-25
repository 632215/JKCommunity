package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.NoticeOneBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.OnRequestListener;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.IDynamicBiz;

import java.util.Map;

/**
 * Created by root on 17-8-25.
 */

public class DynamicImpl implements IDynamicBiz {
    private Context mContext;

    public DynamicImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getDynamicData(Map<String, String> map, final OnRequestListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<NoticeOneBean>() {
            @Override
            public void onNext(NoticeOneBean info) {
                listener.onSuccess(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().getNoticeOne(new ProgressSubscriber<HttpResult<NoticeOneBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }
}
