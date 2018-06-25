package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.EmptyObjectBean;
import com.jinke.community.bean.MsgBean;
import com.jinke.community.service.impl.MsgCenterImpl;
import com.jinke.community.service.listener.MsgCenterListener;
import com.jinke.community.view.MsgCenterView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/6/7.
 */

public class MsgCenterPresenter extends BasePresenter<MsgCenterView> implements MsgCenterListener {
    private Context mContext;
    private MsgCenterImpl impl;

    public MsgCenterPresenter(Context mContext) {
        this.mContext = mContext;
        this.impl = new MsgCenterImpl(mContext);
    }

    //获取
    public void getMsg(int page) {
        Map map = new HashMap();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        map.put("page", page+"");
        map.put("rows", 10+"");
        if (mView != null && impl != null) {
            mView.showLoading();
            impl.getMsg(map, this);
        }
    }

    //获取
    public void getMsg(int page,MsgCenterListener listener) {
        Map map = new HashMap();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        map.put("page", page+"");
        map.put("rows", 10+"");
        if (mView != null && impl != null) {
            mView.showLoading();
            impl.getMsg(map, listener);
        }
    }

    /**
     * 请求消息中心 成功回调
     *
     * @param info
     */
    @Override
    public void getMsgNext(MsgBean info) {
        if (mView != null) {
            mView.hideLoading();
            mView.getMsgNext(info);
        }
    }

    @Override
    public void getMsgError(String code, String msg) {
        if (mView != null) {
            mView.hideLoading();
            mView.getMsgError(msg);
        }
    }

    //更新消息读取状态
    public void upDateMsg(int id) {
        Map map = new HashMap();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        map.put("id", id+"");
        if (mView != null && impl != null) {
            impl.upDateMsg(map, this);
        }
    }

    /**
     * 更新消息读取状态 成功回调
     * @param info
     */
    @Override
    public void upDateMsgNext(EmptyObjectBean info) {
        if (mView != null) {
            mView.upDateMsgNext();
        }
    }
}
