package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.CommunityBean;
import com.jinke.community.bean.PropertyBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.service.impl.PropertyNewsImpl;
import com.jinke.community.service.listener.PropertyNewsListener;
import com.jinke.community.view.IPropertyNewsView;
import com.lidroid.xutils.http.RequestParams;

import java.util.HashMap;
import java.util.Map;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by root on 17-8-10.
 */

public class PropertyNewsPresent extends BasePresenter<IPropertyNewsView>
        implements PropertyNewsListener {
    private Context mContext;
    private PropertyNewsImpl propertyNews;

    public PropertyNewsPresent(Context mContext) {
        this.mContext = mContext;
        propertyNews = new PropertyNewsImpl(mContext);
    }

    /**
     * 提交报事对大管家
     *
     * @param params
     */
    public void addPostIt(RequestParams params) {
        if (mView != null) {
            propertyNews.addPostIt(params, this);
        }
    }

    //提交报事成功
    @Override
    public void addPostItNext(PropertyBean.ListBean data) {
        if (mView != null) {
            mView.addPostItNext(data);
            mView.hindLoading();
        }
    }

    //网络请求失败
    @Override
    public void onErrorListener(String Code, String Msg) {
        if (mView != null) {
            mView.showMsg(Msg);
            mView.hindLoading();
        }
    }

    /**
     * 获取房屋列表信息
     *
     * @param map
     */
    public void getHouseList(Map<String, String> map) {
        if (mView != null) {
            mView.showLoading();
            propertyNews.getHouseList(map, this);
        }
    }

    //获取房屋列表成功
    @Override
    public void getHouseListNext(HouseListBean info) {
        if (mView != null) {
            mView.getHouseListNext(info);
            mView.hindLoading();
        }
    }

    //获取配置试点小区
    public void getConfig(String community_id) {
        if (!StringUtils.isEmpty(community_id) && propertyNews != null) {
            Map map = new HashMap();
            map.put("communityId", community_id);
            mView.showLoading();
            propertyNews.getConfig(map, this);
        }
    }

    /**
     * 获取配置试点小区 成功
     *
     * @param info
     */
    @Override
    public void getConfigNext(CommunityBean info) {
        if (mView != null) {
            mView.getConfigNext(info);
        }
    }

    /**
     * 获取配置试点小区 失败
     */
    @Override
    public void getConfigError() {
        if (mView != null) {
            mView.getConfigError();
        }
    }
}
