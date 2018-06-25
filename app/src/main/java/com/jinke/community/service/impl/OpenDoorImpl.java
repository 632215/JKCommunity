package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.EmptyObjectBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.ListDateInfo;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.http.door.DriveHttpMethods;
import com.jinke.community.presenter.OpenDoorPresenter;
import com.jinke.community.service.IOpenDoorBiz;
import com.jinke.community.service.listener.IOpenDoorListener;

import java.util.Map;

import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by root on 17-8-21.
 */

public class OpenDoorImpl implements IOpenDoorBiz {
    Context mContext;

    public OpenDoorImpl(Context mContext) {
        this.mContext = mContext;

    }

    @Override
    public void getDataTime(Map<String, String> map, final IOpenDoorListener listener) {

        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<ListDateInfo>() {
            @Override
            public void onNext(ListDateInfo info) {
                listener.onListDateInfo(info);
            }

            @Override
            public void onError(String Code, String Msg) {
//                listener.onError(Code, Msg);
            }
        };

        DriveHttpMethods.getInstance().getDateTimeData(new ProgressSubscriber<HttpResult<ListDateInfo>>(nextListener, mContext), map);
    }

    @Override
    public void getOpenLogDate(final Map<String, String> map) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<EmptyObjectBean>() {
            @Override
            public void onNext(EmptyObjectBean info) {
//                listener.onDateTime(info);
            }

            @Override
            public void onError(String Code, String Msg) {
//                ToastUtils.showShort(mContext, Msg);
            }
        };

        DriveHttpMethods.getInstance().getDateTimeData(new ProgressSubscriber<HttpResult<ListDateInfo>>(nextListener, mContext), map);
    }

    public void getQrCode(Map map, final IOpenDoorListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<String>() {
            @Override
            public void onNext(String info) {
                listener.getQrCodeNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                ToastUtils.showShort(mContext, Msg);
            }
        };

        HttpMethods.getInstance().getQrCode(new ProgressSubscriber<HttpResult<String>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }
}
