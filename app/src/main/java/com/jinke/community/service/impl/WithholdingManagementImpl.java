package com.jinke.community.service.impl;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.bean.HouseWithHoldBean;
import com.jinke.community.bean.HttpResult;
import com.jinke.community.bean.WithHoldBreakBean;
import com.jinke.community.http.main.HttpMethods;
import com.jinke.community.http.main.ProgressSubscriber;
import com.jinke.community.http.main.SubscriberOnNextListener;
import com.jinke.community.service.WithholdingManagementBiz;
import com.jinke.community.service.listener.WithholdingManagementListener;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/21.
 */

public class WithholdingManagementImpl implements WithholdingManagementBiz {
    private Context mContext;

    public WithholdingManagementImpl(Context mContext) {
        this.mContext = mContext;
    }

    //获取房屋列表（签约状态）
    @Override
    public void getHouseWithHold(Map map, final WithholdingManagementListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<HouseWithHoldBean>() {
            @Override
            public void onNext(HouseWithHoldBean info) {
                listener.getHouseWithHoldNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onErrorMsg(Code, Msg);
            }
        };

        HttpMethods.getInstance().getHouseWithHold(new ProgressSubscriber<HttpResult<HouseWithHoldBean>>(nextListener, mContext), map, MyApplication.creatSign(map));
    }

    //解约
    @Override
    public void withholdBreak(Map map, final WithholdingManagementListener listener) {
        SubscriberOnNextListener nextListener = new SubscriberOnNextListener<WithHoldBreakBean>() {
            @Override
            public void onNext(WithHoldBreakBean info) {
                listener.withholdBreakNext(info);
            }

            @Override
            public void onError(String Code, String Msg) {
                listener.onErrorMsg(Code, Msg);
            }
        };

        HttpMethods.getInstance().withholdBreak(new ProgressSubscriber<HttpResult<WithHoldBreakBean>>(nextListener, mContext), map, MyApplication.creatSign(map));

    }
}
