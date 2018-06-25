package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.EmptyObjectBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.MsgBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.presenter.MsgCenterPresenter;
import com.jinke.community.service.listener.MsgCenterListener;

import java.util.Map;

/**
 * Created by Administrator on 2018/6/7.
 */

public class MsgCenterImpl {
    private Context mContext;

    public MsgCenterImpl(Context mContext) {
        this.mContext = mContext;
    }

    public void getMsg(Map map, final MsgCenterListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<MsgBean>() {
            @Override
            public void onNext(MsgBean info) {
                listener.getMsgNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getMsgError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getMsg(new ProgressSubscriber<HttpResult<MsgBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    public void upDateMsg(Map map, final MsgCenterListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<EmptyObjectBean>() {
            @Override
            public void onNext(EmptyObjectBean info) {
                listener.upDateMsgNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getMsgError(Code, Msg);
            }
        };
        HttpMethods.getInstance().upDateMsg(new ProgressSubscriber<HttpResult<EmptyObjectBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }
}
