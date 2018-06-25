package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.CityBean;
import com.jinke.community.bean.CommunityGPSBean;
import com.jinke.community.bean.CommunityListBean;
import com.jinke.community.bean.LocationBean;
import com.jinke.community.service.ISelectCommunityBiz;
import com.jinke.community.service.impl.SelectCommunityImpl;
import com.jinke.community.service.listener.ISelectCommunityListener;
import com.jinke.community.utils.LocationUtils;
import com.jinke.community.view.ISelectCommunityView;

import java.util.Map;

/**
 * Created by root on 17-8-2.
 */

public class SelectCommunityPresenter extends BasePresenter<ISelectCommunityView> implements ISelectCommunityListener, LocationUtils.onLocationInfoListener {
    private Context mContext;
    private ISelectCommunityBiz communityBiz;

    public SelectCommunityPresenter(Context mContext) {
        this.mContext = mContext;
        communityBiz = new SelectCommunityImpl(mContext);
    }

    /**
     * 获取定位信息
     */
    public void getLocationInfo() {
        LocationUtils locationUtils = new LocationUtils(mContext);
        locationUtils.setOnLocationInfoListener(this);
    }

    /**
     * 根据经纬度获取小区列表
     *
     * @param map
     */
    public void getCommunityListByGps(Map<String, String> map) {
        communityBiz.getCommunityListByGps(map, this);
    }

    /**
     * 根据关键字查询小区列表
     *
     * @param map
     */
    public void getQueryCommunity(Map<String, String> map) {
        communityBiz.getQueryCommunity(map, this);
    }

    /**
     * 获取城市列表
     *
     * @param map
     */
    public void getCityList(Map<String, String> map) {
        communityBiz.getCityList(map, this);
    }

    /**
     * 获取小区二级列表
     *
     * @param map
     */
    public void getCommunityList(Map<String, String> map) {
        communityBiz.getCommunityList(map, this);
    }


    @Override
    public void onErrorMsg(String code, String msg) {
        if (mView != null) {
            mView.showMsg(msg);
        }
    }

    @Override
    public void getCommunityGpsData(CommunityGPSBean info) {
        if (mView != null) {
            mView.showCommunityGpsData(info);
        }
    }

    @Override
    public void getQueryCommunity(CommunityGPSBean bean) {
        if (mView != null) {
            mView.showQueryCommunity(bean);
        }
    }

    @Override
    public void onCityData(CityBean info) {
        if (mView != null) {
            mView.showCityData(info);
        }
    }

    @Override
    public void onCommunityListData(CommunityListBean bean) {
        if (mView != null) {
            mView.showCommunityListData(bean);
        }
    }

    /**
     * 定位成功
     *
     * @param locationBean
     */
    @Override
    public void locationBackInfo(LocationBean locationBean) {
        if (mView != null) {
            mView.showLocationInfo(locationBean);
        }
    }
    /**
     * 定位失败
     *
     * @param errorCode
     */
    @Override
    public void locationBackFailed(int errorCode) {
        if (mView != null) {
            mView.locationBackFailed(errorCode);
        }
    }
}
