package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.RegisterLoginBean;
import com.jinke.community.bean.WithHoldOpenBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.WithHoldOpenBiz;
import com.jinke.community.service.listener.WithHoldOpenListener;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/23.
 */

public class WithHoldOpenImpl implements WithHoldOpenBiz {
    private Context mContext;

    public WithHoldOpenImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void withHoldOpen(Map map, final WithHoldOpenListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<WithHoldOpenBean>() {
            @Override
            public void onNext(WithHoldOpenBean info) {
                listener.withHoldOpenNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onErrorMsg(Code, Msg);
            }
        };

        HttpMethods.getInstance().withHoldOpen(new ProgressSubscriber<HttpResult<WithHoldOpenBean>>(nextListener, mContext), map, MyApplication.creatSign(map));

    }
}
