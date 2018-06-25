package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.BrokenNewsListBean;
import com.jinke.community.bean.BrokenPersonBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.presenter.BrokenPersonPresenter;
import com.jinke.community.service.BrokenPersonBiz;
import com.jinke.community.service.listener.BrokenPersonlistener;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/28.
 */

public class BrokenPersonImpl implements BrokenPersonBiz {
    private Context mContext;

    public BrokenPersonImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getBrokePerson(Map<String, String> map, final BrokenPersonlistener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<BrokenPersonBean>() {
            @Override
            public void onNext(BrokenPersonBean info) {
                listener.getBrokePersonNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().getBrokePerson(new ProgressSubscriber<HttpResult<BrokenPersonBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }
}
