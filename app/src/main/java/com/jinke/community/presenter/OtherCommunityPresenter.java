package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.CityBean;
import com.jinke.community.bean.CommunityListBean;
import com.jinke.community.service.IOtherCommunityBiz;
import com.jinke.community.service.impl.OtherCommunityImpl;
import com.jinke.community.service.listener.IOtherCommunityListener;
import com.jinke.community.view.IOtherCommunityView;

import java.util.Map;

/**
 * Created by root on 17-8-2.
 */

public class OtherCommunityPresenter extends BasePresenter<IOtherCommunityView> implements IOtherCommunityListener {
    private Context mContext;
    private IOtherCommunityBiz communityBiz;

    public OtherCommunityPresenter(Context mContext) {
        this.mContext = mContext;
        communityBiz = new OtherCommunityImpl(mContext);
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
}
