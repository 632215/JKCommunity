package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.BrokenNewsListBean;
import com.jinke.community.bean.CommunityBean;
import com.jinke.community.bean.HouseBean;
import com.jinke.community.bean.PropertyBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.service.impl.PropertyHistoryImpl;
import com.jinke.community.service.listener.IPropertyHistoryListener;
import com.jinke.community.view.IPropertyHistoryView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 17-8-11.
 */

public class PropertyHistoryPresent extends BasePresenter<IPropertyHistoryView> implements IPropertyHistoryListener {

    private Context mContext;
    PropertyHistoryImpl propertyHistory;

    public PropertyHistoryPresent(Context mContext) {
        this.mContext = mContext;
        propertyHistory = new PropertyHistoryImpl(mContext);
    }

    /**
     * 报事列表
     *
     * @param map
     */
    public void getPostItList(HashMap map) {
        if (map != null)
            propertyHistory.getKeepPostItList(map, this);
    }

    @Override
    public void getKeepPostItListNext(PropertyBean info) {
        if (mView != null) {
            mView.getKeepPostItListNext(info);
        }
    }

    @Override
    public void onError(String code, String msg) {
        if (mView != null) {
            mView.showMsg(msg);
        }
    }

    //获取配置试点小区
    public void getConfig() {
        if (propertyHistory != null) {
            HouseListBean.ListBean bean = MyApplication.getInstance().getDefaultHouse();
            if (bean != null) {
                Map map = new HashMap();
                map.put("communityId", bean.getCommunity_id());
                propertyHistory.getConfig(map,this);
            }
        }
    }

    /**
     * 获取配置试点小区 成功
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
