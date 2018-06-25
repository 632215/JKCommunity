package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.AuthorizedRecordBean;
import com.jinke.community.bean.EmptyObjectBean;
import com.jinke.community.bean.ParkInfoBean;
import com.jinke.community.bean.UserCarBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.service.VehicleManagementBiz;
import com.jinke.community.service.impl.VehicleManagementImpl;
import com.jinke.community.service.listener.VehicleManagementListener;
import com.jinke.community.view.VehicleManagementView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/16.
 */

public class VehicleManagementPresenter extends BasePresenter<VehicleManagementView> implements VehicleManagementListener {
    private Context mContext;
    private VehicleManagementBiz mBiz;

    public VehicleManagementPresenter(Context mContext) {
        this.mContext = mContext;
        mBiz = new VehicleManagementImpl(mContext);
    }

    public void getMyCar(Map map) {
        if (mView != null) {
            mView.showLoading();
            mBiz.getMyCar(map, this);
        }
    }

    @Override
    public void getMyCarOnNext(UserCarBean info) {
        if (mView != null) {
            mView.hideLoading();
            mView.getMyCarOnNext(info);
        }
    }

    @Override
    public void error(String msg) {
        if (mView != null) {
            mView.hideLoading();
            mView.getMyCarOnError(msg);
        }
    }

    public void banVehicle(Map map) {
        if (mView != null) {
            mView.showLoading();
            mBiz.banVehicle(map, this);
        }
    }

    //绑定成功
    @Override
    public void banVehicleOnNext() {
        if (mView != null) {
            mView.hideLoading();
            mView.successOperating();
        }
    }

    public void unBanVehicle(Map map) {
        if (mView != null) {
            mView.showLoading();
            mBiz.unBanVehicle(map, this);
        }
    }

    //解绑成功
    @Override
    public void unBanVehicleOnNext(EmptyObjectBean info) {
        if (mView != null) {
            mView.hideLoading();
            mView.successOperating();
        }
    }

    public void getAuthorizedVehicle(Map map) {
        if (mView != null) {
            mView.showLoading();
            mBiz.getAuthorizedVehicle(map, this);
        }
    }

    //获取车辆授权信息成功
    @Override
    public void getAuthorizedVehicleNext(List<AuthorizedRecordBean.ListBean> infoList) {
        if (mView != null) {
            mView.hideLoading();
            mView.getAuthorizedVehicleNext(infoList);
        }
    }

    public void deleteAuthorized(Map map) {
        if (mView != null) {
            mView.showLoading();
            mBiz.deleteAuthorized(map, this);
        }
    }

    @Override
    public void deleteAuthorizedOnNext(EmptyObjectBean info) {
        if (mView != null) {
            mView.hideLoading();
            mView.deleteAuthorizedOnNext();
        }
    }

    /**
     * 获取停车场信息
     */
    public void getParkingInfo() {
        if (mView != null) {
            mView.showLoading();
            Map map = new HashMap();
            map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
            mBiz.getParkingInfo(map, this);
        }
    }

    @Override
    public void getParkInfoonNext(ParkInfoBean info) {
        if (mView != null) {
            mView.hideLoading();
            if (info != null)
                mView.getParkInfoonNext(info);
        }
    }
}
