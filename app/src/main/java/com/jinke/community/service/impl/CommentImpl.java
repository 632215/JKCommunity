package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.EmptyObjectBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.PaymentRecordBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.CommentBiz;
import com.jinke.community.service.listener.CommentListener;

import java.util.Map;

import rx.Subscriber;

/**
 * Created by Administrator on 2017/12/6.
 */

public class CommentImpl implements CommentBiz {
    private Context mContext;

    public CommentImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void addPostItComments(Map map, final CommentListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<EmptyObjectBean>() {
            @Override
            public void onNext(EmptyObjectBean info) {
                listener.addPostItCommentsNext();
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().addPostItComments(new ProgressSubscriber<HttpResult<EmptyObjectBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }
}
