package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.UrlConfigBean;
import com.jinke.community.bean.UserInfo;
import com.jinke.community.bean.WorkingSortBean;
import com.jinke.community.bean.XiMoDriveListBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.bean.RedCircleGroupBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.http.door.DriveHttpMethods;
import com.jinke.community.http.door.DriveSubscriber;
import com.jinke.community.service.IMainBiz;
import com.jinke.community.service.listener.IMainListener;

import java.util.Map;

/**
 * Created by root on 17-8-9.
 */

public class MainImpl implements IMainBiz {
    Context mContext;

    public MainImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getUrlConfig(Map<String, String> map, final IMainListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<UrlConfigBean>() {
            @Override
            public void onNext(UrlConfigBean info) {
                listener.onUrlSuccess(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().getUrlConfig(new ProgressSubscriber<HttpResult<UrlConfigBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void getDeviceListDate(Map<String, String> map, final IMainListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<XiMoDriveListBean>() {
            @Override
            public void onNext(XiMoDriveListBean info) {
                listener.onDeviceList(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onDeriveError(Code, Msg);
            }
        };
        DriveHttpMethods.getInstance(true).getDeviceListDate(new DriveSubscriber<HttpResult<XiMoDriveListBean>>(nextListener, mContext), map);
    }

    @Override
    public void getUserInfo(Map<String, String> map, final IMainListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<UserInfo>() {
            @Override
            public void onNext(UserInfo info) {
                listener.onUserInfo(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().getUserInfoData(new ProgressSubscriber<HttpResult<UserInfo>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void getBandingControl(Map<String, String> map, final IMainListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<XiMoDriveListBean>() {
            @Override
            public void onNext(XiMoDriveListBean info) {
                listener.onBandingControl(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().getBandingControl(new ProgressSubscriber<HttpResult<XiMoDriveListBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void getUpDate(Map<String, String> map, IMainListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<WorkingSortBean>() {
            @Override
            public void onNext(WorkingSortBean info) {
            }

            @Override
            public void onError(String Code, String Msg) {
            }
        };

        HttpMethods.getInstance().getWorkingUpdate(new ProgressSubscriber<HttpResult<WorkingSortBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    public void getHouseList(Map map, final IMainListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<HouseListBean>() {
            @Override
            public void onNext(HouseListBean info) {
                listener.getHouseListNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().getHouseListData(new ProgressSubscriber<HttpResult<HouseListBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void getRedCircle(Map<String, String> map,final IMainListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<RedCircleGroupBean>() {
            @Override
            public void onNext(RedCircleGroupBean info) {
                listener.getRedCircleNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getRedCircle(new ProgressSubscriber<HttpResult<RedCircleGroupBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }
}
