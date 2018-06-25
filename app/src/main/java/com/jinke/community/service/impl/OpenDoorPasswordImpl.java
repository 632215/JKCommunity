package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.bean.HouseListInfo;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.ReasonBean;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.http.door.DriveHttpMethods;
import com.jinke.community.http.door.DriveSubscriber;
import com.jinke.community.presenter.OpenDoorPasswordPresenter;
import com.jinke.community.service.IOpenDoorPasswordBiz;
import com.jinke.community.service.listener.IOpenDoorPasswordListener;

import java.util.Map;

/**
 * Created by root on 17-8-21.
 */

public class OpenDoorPasswordImpl implements IOpenDoorPasswordBiz {

    private Context mContext;

    public OpenDoorPasswordImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getPurposeListDate(Map<String, String> map, final IOpenDoorPasswordListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<ReasonBean>() {
            @Override
            public void onNext(ReasonBean info) {
                listener.onReasonBean(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        DriveHttpMethods.getInstance().getPurposeListDate(new DriveSubscriber<HttpResult<ReasonBean>>(nextListener, mContext), map);

    }

    public void getHouseListDate(Map<String, String> map, final IOpenDoorPasswordListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<HouseListInfo>() {
            @Override
            public void onNext(HouseListInfo info) {
                listener.getHouseListDateNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getHouseListDateError(Code, Msg);
            }
        };
        DriveHttpMethods.getInstance(true).getHouseListDate(new DriveSubscriber<HttpResult<HouseListInfo>>(nextListener, mContext), map);
    }
}
