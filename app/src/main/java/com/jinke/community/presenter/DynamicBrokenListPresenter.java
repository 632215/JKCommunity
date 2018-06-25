package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.BrokeNoticeListBean;
import com.jinke.community.bean.UserCommunityBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.service.impl.DynamicBrokenListImpl;
import com.jinke.community.service.listener.IDynamicBrokenListener;
import com.jinke.community.ui.widget.LoadingLayout;
import com.jinke.community.view.IDynamicBrokenListView;

import java.util.Map;

/**
 * Created by root on 17-8-14.
 */

public class DynamicBrokenListPresenter extends BasePresenter<IDynamicBrokenListView> implements IDynamicBrokenListener {
    private Context mContext;
    private DynamicBrokenListImpl dynamicBroken;

    public DynamicBrokenListPresenter(Context mContext) {
        this.mContext = mContext;
        dynamicBroken = new DynamicBrokenListImpl(mContext);
    }

    public void getDynamicBrokeList(Map<String, String> map) {
        dynamicBroken.getBrokeNewsNoticeList(map, this);
    }

    public void getPostItNoticeList(Map<String, String> map) {
        dynamicBroken.getPostItNoticeList(map, this);
    }

    @Override
    public void onError(String code, String msg) {
        if (mView != null) {
            mView.showMsg(msg);
        }
    }

    @Override
    public void BrokeNewsNoticeList(BrokeNoticeListBean bean) {
        if (mView != null) {
            mView.onBrokeNewsNoticeList(bean);
        }

    }

    /**
     * 获取房屋列表信息
     *
     * @param map
     */
    public void getHouseList(Map<String, String> map) {
            dynamicBroken.getHouseList(map, this);
    }

    @Override
    public void getHouseListNext(HouseListBean info) {
        if (mView != null) {
            mView.getHouseListNext(info);
        }
    }

    /**
     * 获取当前用户所属小区列表
     */
    public void getCommunity(Map<String, String> map) {
        dynamicBroken.getCommunity(map, this);
    }

    @Override
    public void getCommunityNext(UserCommunityBean info) {
        if (mView != null) {
            mView.getCommunityNext(info);
        }
    }
}
