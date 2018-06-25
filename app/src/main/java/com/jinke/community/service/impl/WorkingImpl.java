package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.WorkingSortBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.IWorkingBiz;
import com.jinke.community.service.listener.IWorkingListener;

import java.util.Map;

/**
 * Created by root on 17-8-17.
 */

public class WorkingImpl implements IWorkingBiz {
    Context mContext;

    public WorkingImpl(Context mContext) {
        this.mContext = mContext;

    }

    @Override
    public void getWorkingSort(Map<String, String> map, final IWorkingListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<WorkingSortBean>() {
            @Override
            public void onNext(WorkingSortBean info) {
                listener.onWorkingSort(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().getWorkingSort(new ProgressSubscriber<HttpResult<WorkingSortBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }


    @Override
    public void getUpDate(Map<String, String> map, final IWorkingListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<WorkingSortBean>() {
            @Override
            public void onNext(WorkingSortBean info) {
                listener.onUpdate(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().getWorkingUpdate(new ProgressSubscriber<HttpResult<WorkingSortBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }
}
