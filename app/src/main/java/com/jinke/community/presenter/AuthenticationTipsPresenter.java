package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.StateBean;
import com.jinke.community.service.impl.AuthenticationTipsImpl;
import com.jinke.community.service.listener.AuthenticationTipsListener;
import com.jinke.community.view.AuthenticationTipsView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/5/30.
 */

public class AuthenticationTipsPresenter extends BasePresenter<AuthenticationTipsView> implements AuthenticationTipsListener {
    private Context mContext;
    private AuthenticationTipsImpl impl;

    public AuthenticationTipsPresenter(Context mContext) {
        this.mContext = mContext;
        this.impl = new AuthenticationTipsImpl(mContext);
    }

    //获取房屋审核状态
    public void getState(String houseId) {
        if (impl != null && mView != null) {
            Map map = new HashMap();
            map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
            map.put("houseId", houseId);
            impl.getState(map, this);
            mView.showLoading();
        }
    }

    @Override
    public void getStateNext(StateBean info) {
        if ( mView != null) {
            mView.hideLoading();
            mView.getStateNext(info);
        }
    }

    @Override
    public void getStateError(String code, String msg) {
        if ( mView != null) {
            mView.hideLoading();
            mView.getStateError(msg);
        }
    }
}
