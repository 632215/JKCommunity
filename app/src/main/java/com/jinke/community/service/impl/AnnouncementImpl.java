package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.BrokeNoticeListBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.UserCommunityBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.AnnouncementBiz;
import com.jinke.community.service.listener.AnnouncementListener;
import com.jinke.community.service.listener.IDynamicBrokenListener;

import java.util.Map;

/**
 * Created by Administrator on 2017/12/7.
 */

public class AnnouncementImpl implements AnnouncementBiz {
    private Context mContext;
    public AnnouncementImpl(Context mContext) {
        this.mContext = mContext;
    }

    /**
     * 获取当前用户所属小区列表
     */
    @Override
    public void getCommunity(Map<String, String> map,final AnnouncementListener listener) {
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

    /**
     * 物业报事
     *
     * @param map
     * @param listener
     */
    @Override
    public void getPostItNoticeList(Map<String, String> map,final AnnouncementListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<BrokeNoticeListBean>() {
            @Override
            public void onNext(BrokeNoticeListBean info) {
                listener.getPostItNoticeListNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getPostItNoticeList(new ProgressSubscriber<HttpResult<BrokeNoticeListBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }
}
