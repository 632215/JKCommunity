package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.LoginBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.service.IBrokeUpBiz;
import com.jinke.community.service.impl.BrokeUpImpl;
import com.jinke.community.service.listener.IBrokeUpListener;
import com.jinke.community.view.IBrokeUpView;
import com.lidroid.xutils.http.RequestParams;

import java.util.Map;

/**
 * Created by root on 17-8-6.
 */
public class BrokeUpPresenter extends BasePresenter<IBrokeUpView> implements IBrokeUpListener {
    private Context mContext;
    private IBrokeUpBiz brokeUpBiz;

    public BrokeUpPresenter(Context mContext) {
        this.mContext = mContext;
        brokeUpBiz = new BrokeUpImpl(mContext);
    }

    /**
     * 上传文件
     *
     * @param params
     */
    public void getUpLoadFile(RequestParams params) {
        if (mView != null) {
            brokeUpBiz.getUpFile(params, this);
            mView.showLoading();
        }
    }

    //上传成功
    @Override
    public void onSuccess(LoginBean bean) {
        if (mView != null) {
            mView.onFileUpSuccess(bean);
            mView.hideLoading();
        }
    }

    //网络请求失败
    @Override
    public void onError(String code, String msg) {
        if (mView != null) {
            mView.showMsg(msg);
            mView.hideLoading();
        }
    }

    /**
     * 获取房屋列表
     *
     * @param map
     */
    public void getHouseList(Map<String, String> map) {
        if (mView != null) {
            mView.showLoading();
            brokeUpBiz.getHouseList(map, this);
        }
    }

    //获取房屋列表成功
    @Override
    public void getHouseListNext(HouseListBean info) {
        if (mView != null) {
            mView.hideLoading();
            mView.getHouseListNext(info);
        }
    }
}