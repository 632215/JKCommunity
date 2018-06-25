package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.SetDefaultHouseBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.service.IMyHouseBiz;
import com.jinke.community.service.impl.MyHouseImpl;
import com.jinke.community.service.listener.IMyHouseListener;
import com.jinke.community.view.IMyHouseView;

import java.util.Map;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by root on 17-8-3.
 */

public class MyHousePresenter extends BasePresenter<IMyHouseView> implements IMyHouseListener {
    Context mContext;
    IMyHouseBiz myHouseBiz;

    public MyHousePresenter(Context mContext) {
        this.mContext = mContext;
        myHouseBiz = new MyHouseImpl(mContext);
    }

    public void getHouseList(Map<String, String> map) {
        if (mView != null && myHouseBiz != null) {
            mView.showLoading();
            myHouseBiz.getHouseListData(map, this);
        }
    }

    public void getDefaultData(Map<String, String> map) {
        if (mView != null) {
            myHouseBiz.getDefaultHouseData(map, this);
        }
    }

    @Override
    public void onErrorMsg(String code, String msg) {
        if (mView != null) {
            mView.showMsg(msg);
            mView.hideLoading();
            if (StringUtils.equals("4007", code)) {
                mView.getHouseListError();
            }
        }
    }

    @Override
    public void onSuccess(HouseListBean bean) {
        if (mView != null) {
            mView.onSuccessBack(bean);
            mView.hideLoading();
        }
    }

    @Override
    public void onDefaultHouse(SetDefaultHouseBean bean) {
        if (mView != null) {
            mView.onHouseHouseData(bean);
        }
    }

}
