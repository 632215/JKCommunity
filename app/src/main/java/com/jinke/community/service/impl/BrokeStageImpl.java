package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.BrokenNewsListBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.PraiseresultBean;
import com.jinke.community.bean.UserCommunityBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.bean.acachebean.NoticeListBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.presenter.BrokeStagePresenter;
import com.jinke.community.service.BrokeStageBiz;
import com.jinke.community.service.listener.BrokeStageListener;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/28.
 */

public class BrokeStageImpl implements BrokeStageBiz {
    private Context mContext;

    public BrokeStageImpl(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void getStageBrokeList(Map<String, String> map, final BrokeStageListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<NoticeListBean>() {
            @Override
            public void onNext(NoticeListBean info) {
                listener.getStageBrokeListNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().getStageBrokeList(new ProgressSubscriber<HttpResult<NoticeListBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void praiseClick(Map map, final BrokeStageListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<PraiseresultBean>() {
            @Override
            public void onNext(PraiseresultBean info) {
                listener.praiseClickNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onError(Code, Msg);
            }
        };
        HttpMethods.getInstance().praise(new ProgressSubscriber<HttpResult<PraiseresultBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    @Override
    public void getCommunity(Map<String, String> map, final BrokeStageListener listener) {
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
