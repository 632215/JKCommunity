package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.EmptyObjectBean;
import com.jinke.community.bean.ParkInfoBean;
import com.jinke.community.service.VehicleAuthorizedBiz;
import com.jinke.community.service.impl.VehicleAuthorizedImpl;
import com.jinke.community.service.listener.VehicleAuthorizedListener;
import com.jinke.community.view.VehicleAuthorizedView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/17.
 */

public class VehicleAuthorizedPresenter extends BasePresenter<VehicleAuthorizedView> implements VehicleAuthorizedListener {
    private Context mContext;
    private VehicleAuthorizedBiz vehicleAuthorizedBiz;

    public VehicleAuthorizedPresenter(Context mContext) {
        this.mContext = mContext;
        vehicleAuthorizedBiz = new VehicleAuthorizedImpl(mContext);
    }

    public void getParkInfo() {
        if (mView != null) {
            mView.showLoading();
            Map map = new HashMap();
            map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
            vehicleAuthorizedBiz.getParkInfo(map, this);
        }
    }

    @Override
    public void error(String msg) {
        if (mView != null) {
            mView.hideLoading();
            mView.error(msg);
        }
    }

    @Override
    public void getParkInfoonNext(ParkInfoBean info) {
        if (mView != null) {
            mView.hideLoading();
            mView.getParkInfoonNext(info);
        }
    }

    public void addAuthorize(Map map) {
        if (mView != null) {
            mView.showLoading();
            vehicleAuthorizedBiz.addAuthorize(map, this);
        }
    }

    @Override
    public void addAuthorizeonNext(EmptyObjectBean info) {
        if (mView != null) {
            mView.hideLoading();
            mView.addAuthorizeonNext();
        }
    }
}
