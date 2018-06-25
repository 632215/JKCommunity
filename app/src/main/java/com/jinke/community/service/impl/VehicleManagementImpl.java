package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.AuthorizedRecordBean;
import com.jinke.community.bean.AuthorizedVehicleBean;
import com.jinke.community.bean.EmptyObjectBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.ParkInfoBean;
import com.jinke.community.bean.UserCarBean;

import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.presenter.VehicleManagementPresenter;
import com.jinke.community.service.VehicleManagementBiz;
import com.jinke.community.service.listener.VehicleManagementListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import www.jinke.com.library.utils.commont.StringUtils;
import www.jinke.com.library.utils.commont.ToastUtils;

/**
 * Created by Administrator on 2017/10/19.
 */

public class VehicleManagementImpl implements VehicleManagementBiz {
    private Context mContext;

    public VehicleManagementImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getMyCar(Map map, final VehicleManagementListener listener) {
        SubscriberOnNextListener subscriberOnNextListener = new SubscriberOnNextListener<UserCarBean>() {
            @Override
            public void onNext(UserCarBean info) {
                if (info != null) {
                    listener.getMyCarOnNext(info);
                }
            }

            @Override
            public void onError(String Code, String Msg) {
                if (StringUtils.equals(Code, "4500"))
                    return;
                listener.error(Msg);

            }
        };
        HttpMethods.getInstance().getMyCar(new ProgressSubscriber<HttpResult<UserCarBean>>(subscriberOnNextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void banVehicle(Map map, final VehicleManagementListener listener) {
        SubscriberOnNextListener subscriberOnNextListener = new SubscriberOnNextListener<EmptyObjectBean>() {
            @Override
            public void onNext(EmptyObjectBean info) {
                listener.banVehicleOnNext();
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.error(Msg);
            }
        };
        HttpMethods.getInstance().banVehicle(new ProgressSubscriber<HttpResult<EmptyObjectBean>>(subscriberOnNextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void unBanVehicle(Map map, final VehicleManagementListener listener) {
        SubscriberOnNextListener subscriberOnNextListener = new SubscriberOnNextListener<EmptyObjectBean>() {
            @Override
            public void onNext(EmptyObjectBean info) {
                listener.unBanVehicleOnNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.error(Msg);
            }
        };
        HttpMethods.getInstance().unBanVehicle(new ProgressSubscriber<HttpResult<EmptyObjectBean>>(subscriberOnNextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void getAuthorizedVehicle(Map map, final VehicleManagementListener listener) {
        SubscriberOnNextListener subscriberOnNextListener = new SubscriberOnNextListener<AuthorizedVehicleBean>() {
            @Override
            public void onNext(AuthorizedVehicleBean info) {
                List<AuthorizedRecordBean.ListBean> list = new ArrayList<>();
                AuthorizedRecordBean.ListBean bean;
                if (info != null && info.getList() != null) {
                    try {
                        for (AuthorizedVehicleBean.ListBean listBean : info.getList()) {
                            bean = new AuthorizedRecordBean.ListBean();
                            bean.setDelete_remark("false");
                            bean.setUser_No(listBean.getUser_No());
                            bean.setReserveOrder_ID(listBean.getReserveOrder_ID());
                            bean.setReserveOrder_No(listBean.getReserveOrder_No());
                            bean.setReserveOrder_ReserveTime(listBean.getReserveOrder_ReserveTime());
                            bean.setReserveOrder_CreateTime(listBean.getReserveOrder_CreateTime());
                            bean.setReserveOrder_CancelTime(listBean.getReserveOrder_CancelTime());
                            bean.setReserveOrder_CarNO(listBean.getReserveOrder_CarNO());
                            bean.setParking_Key(listBean.getParking_Key());
                            bean.setReserveOrder_Status(listBean.getReserveOrder_Status());
                            bean.setReserveOrder_Desc(listBean.getReserveOrder_Desc());
                            bean.setCommunityName(listBean.getCommunityName());
                            list.add(bean);
                        }
                    } catch (Exception e) {
                        ToastUtils.showShort(mContext, "数据解析错误");
                    }
                }
                listener.getAuthorizedVehicleNext(list);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.error(Msg);
            }
        };
        HttpMethods.getInstance().getAuthorizedVehicle(new ProgressSubscriber<HttpResult<AuthorizedVehicleBean>>(subscriberOnNextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void deleteAuthorized(Map map, final VehicleManagementListener listener) {
        SubscriberOnNextListener subscriberOnNextListener = new SubscriberOnNextListener<EmptyObjectBean>() {
            @Override
            public void onNext(EmptyObjectBean info) {
                listener.deleteAuthorizedOnNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.error(Msg);
            }
        };
        HttpMethods.getInstance().deleteAuthorized(new ProgressSubscriber<HttpResult<EmptyObjectBean>>(subscriberOnNextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void getParkingInfo(Map map, final VehicleManagementListener listener) {
        SubscriberOnNextListener subscriberOnNextListener = new SubscriberOnNextListener<ParkInfoBean>() {
            @Override
            public void onNext(ParkInfoBean info) {
                listener.getParkInfoonNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.error(Msg);
            }
        };
        HttpMethods.getInstance().getParkingInfo(new ProgressSubscriber<HttpResult<ParkInfoBean>>(subscriberOnNextListener, mContext), map, MyApplication.creatSign(map));
    }
}
