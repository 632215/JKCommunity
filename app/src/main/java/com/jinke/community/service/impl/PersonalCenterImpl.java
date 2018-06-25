package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.DefaultHouseBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.MsgBean;
import com.jinke.community.bean.ParkInfoBean;
import com.jinke.community.bean.PrepaidExpensesBean;
import com.jinke.community.bean.UrlConfigBean;
import com.jinke.community.bean.UserInfo;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.presenter.PersonalCenterPresent;
import com.jinke.community.service.IPersonalCenterBiz;
import com.jinke.community.service.listener.IMainListener;
import com.jinke.community.service.listener.IPersonalCenterListener;

import java.util.HashMap;
import java.util.Map;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by root on 17-8-16.
 */

public class PersonalCenterImpl implements IPersonalCenterBiz {
    private Context mContext;

    public PersonalCenterImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getConfigUrl(Map<String, String> map, final IPersonalCenterListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<UrlConfigBean>() {
            @Override
            public void onNext(UrlConfigBean bean) {
                listener.onConfigUrl(bean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getUrlConfig(new ProgressSubscriber<HttpResult<UrlConfigBean>>(onNextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void getPrePayList(Map<String, String> map, final IPersonalCenterListener listener) {
        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<PrepaidExpensesBean>() {
            @Override
            public void onNext(PrepaidExpensesBean bean) {
                listener.onPrePayList(bean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.getPrePayListError();
            }
        };

        HttpMethods.getInstance().getPrePayList(new ProgressSubscriber<HttpResult<PrepaidExpensesBean>>(onNextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void getPoint(Map<String, String> map, final IPersonalCenterListener listener) {

        SubscriberOnNextListener onNextListener = new SubscriberOnNextListener<PrepaidExpensesBean>() {
            @Override
            public void onNext(PrepaidExpensesBean bean) {
                listener.getPointInfo(bean);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().getPointData(new ProgressSubscriber<HttpResult<PrepaidExpensesBean>>(onNextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void getParkingInfo(Map map, final IPersonalCenterListener listener) {
        SubscriberOnNextListener subscriberOnNextListener = new SubscriberOnNextListener<ParkInfoBean>() {
            @Override
            public void onNext(ParkInfoBean info) {
                listener.getParkInfoNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getParkingInfo(new ProgressSubscriber<HttpResult<ParkInfoBean>>(subscriberOnNextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void getHouseList(Map map, final IPersonalCenterListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<HouseListBean>() {
            @Override
            public void onNext(HouseListBean info) {
                listener.getHouseListNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                if (StringUtils.equals("4007", Code))
                    listener.getHouseListError();
                else
                    listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getHouseListData(new ProgressSubscriber<HttpResult<HouseListBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void getUserInfo(HashMap map, final IPersonalCenterListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<UserInfo>() {
            @Override
            public void onNext(UserInfo info) {
                listener.getUserInfoNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getUserInfoData(new ProgressSubscriber<HttpResult<UserInfo>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    public void getMsg(Map map,  final IPersonalCenterListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<MsgBean>() {
            @Override
            public void onNext(MsgBean info) {
                listener.getMsgNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getMsg(new ProgressSubscriber<HttpResult<MsgBean>>(nextListener, mContext), map, MyApplication.creatSign(map));}
}
