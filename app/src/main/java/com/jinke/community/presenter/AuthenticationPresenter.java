package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.base.BasePresenter;
import com.jinke.community.service.impl.AuthenticationImpl;
import com.jinke.community.service.listener.AuthenticationListener;
import com.jinke.community.view.AuthenticationView;
import com.lidroid.xutils.http.RequestParams;

/**
 * Created by Administrator on 2018/5/30.
 */

public class AuthenticationPresenter extends BasePresenter<AuthenticationView> implements AuthenticationListener {
    private Context mContext;
    private AuthenticationImpl impl;

    public AuthenticationPresenter(Context mContext) {
        this.mContext = mContext;
        impl = new AuthenticationImpl(mContext);
    }

    //获取待接房房屋信息
    public void upload(RequestParams params) {
        if (mView != null && impl != null) {
            impl.upload(params, this);
            mView.showLoading();
        }
    }

    @Override
    public void uploadSuccess() {
        if (mView!=null){
            mView.hideLoading();
            mView.uploadSuccess();
        }
    }

    @Override
    public void uploadFail(String s) {
        if (mView!=null){
            mView.hideLoading();
            mView.uploadFail(s);
        }
    }
}
