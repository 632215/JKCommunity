package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.EmptyObjectBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.OnRequestListener;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.ISuggestionsBiz;

import java.util.Map;

/**
 * Created by root on 17-8-16.
 */

public class SuggestionsImpl implements ISuggestionsBiz {
    private Context mContext;

    public SuggestionsImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getAddSuggestData(Map<String, String> map, final OnRequestListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<EmptyObjectBean>() {
            @Override
            public void onNext(EmptyObjectBean info) {
                listener.onSuccess(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getAddSuggestData(new ProgressSubscriber<HttpResult<EmptyObjectBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }
}
