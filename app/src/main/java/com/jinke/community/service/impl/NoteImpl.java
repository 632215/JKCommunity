package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.EmptyObjectBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.NoteBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.NoteBiz;
import com.jinke.community.service.listener.NoteListener;

import java.util.Map;

import rx.Subscriber;

/**
 * Created by Administrator on 2018/3/23.
 */

public class NoteImpl implements NoteBiz {
    private Context mContext;

    public NoteImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getNote(Map map, final NoteListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<NoteBean>() {
            @Override
            public void onNext(NoteBean info) {
                listener.getNoteNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onErrorMsg(Code, Msg);
            }
        };
        HttpMethods.getInstance().getNote(new ProgressSubscriber<HttpResult<NoteBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void setNote(Map map, final NoteListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<EmptyObjectBean>() {
            @Override
            public void onNext(EmptyObjectBean info) {
                listener.setNoteNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onErrorMsg(Code, Msg);
            }
        };
        HttpMethods.getInstance().setNote(new ProgressSubscriber<HttpResult<EmptyObjectBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }
}
