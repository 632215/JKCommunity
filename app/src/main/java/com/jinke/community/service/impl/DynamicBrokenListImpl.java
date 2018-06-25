package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.BrokeNoticeListBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.UserCommunityBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.presenter.DynamicBrokenListPresenter;
import com.jinke.community.service.IDynamicBrokenListBiz;
import com.jinke.community.service.listener.IDynamicBrokenListener;

import java.util.Map;

/**
 * Created by root on 17-8-14.
 */

public class DynamicBrokenListImpl implements IDynamicBrokenListBiz {

    private Context mContext;

    public DynamicBrokenListImpl(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 爆料
     *
     * @param map
     * @param listener
     */
    @Override
    public void getBrokeNewsNoticeList(Map<String, String> map, final IDynamicBrokenListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<BrokeNoticeListBean>() {
            @Override
            public void onNext(BrokeNoticeListBean info) {
                listener.BrokeNewsNoticeList(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().getBrokeNewsNoticeList(new ProgressSubscriber<HttpResult<BrokeNoticeListBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    /**
     * 物业报事
     *
     * @param map
     * @param listener
     */
    @Override
    public void getPostItNoticeList(Map<String, String> map, final IDynamicBrokenListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<BrokeNoticeListBean>() {
            @Override
            public void onNext(BrokeNoticeListBean info) {
                listener.BrokeNewsNoticeList(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };

        HttpMethods.getInstance().getPostItNoticeList(new ProgressSubscriber<HttpResult<BrokeNoticeListBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    /**
     * 房屋列表信息
     *
     * @param map
     * @param listener
     */
    public void getHouseList(Map<String, String> map, final IDynamicBrokenListener listener) {
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

    /**
     * 获取当前用户所属小区列表
     */
    public void getCommunity(Map<String, String> map, final IDynamicBrokenListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<UserCommunityBean>() {
            @Override
            public void onNext(UserCommunityBean info) {
                listener.getCommunityNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getCommunity(new ProgressSubscriber<HttpResult<UserCommunityBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }
}
