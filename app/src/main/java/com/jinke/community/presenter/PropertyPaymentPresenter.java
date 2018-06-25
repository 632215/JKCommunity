package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.ParkInfoBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.config.AppConfig;
import com.jinke.community.service.PropertyPaymentBiz;
import com.jinke.community.service.impl.PropertyPaymentImpl;
import com.jinke.community.service.listener.PropertyPaymentListener;
import com.jinke.community.view.PropertyPaymentView;

import java.util.HashMap;
import java.util.Map;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by Administrator on 2017/11/18.
 */

public class PropertyPaymentPresenter extends BasePresenter<PropertyPaymentView> implements PropertyPaymentListener {
    private Context mContext;
    private PropertyPaymentBiz mBiz;

    public PropertyPaymentPresenter(Context mContext) {
        this.mContext = mContext;
        mBiz = new PropertyPaymentImpl(mContext);
    }

    public void getHouseList() {
        if (mView != null) {
            mView.showLoading();
            Map map = new HashMap();
            map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
            mBiz.getHouseList(map, this);
        }
    }

    @Override
    public void getHouseListNext(HouseListBean info) {
        if (mView != null) {
            mView.hideLoading();
            mView.getHouseListNext(info);
        }
    }

    @Override
    public void onErrorMsg(String code, String msg) {
        if (mView != null) {
            mView.hideLoading();
            if (!StringUtils.equals(code, "4024"))
                mView.showMessage(msg);
        }
    }

    /**
     * 获取车位信息
     */
    public void getParkInfo() {
        if (mView != null) {
            mView.showLoading();
            Map map = new HashMap();
            map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
            map.put("secretKey", AppConfig.SECRETKEY);
            map.put("phone", MyApplication.getBaseUserBean().getPhone());
            mBiz.getParkInfo(map, this);
        }
    }

    @Override
    public void getParkInfoNext(ParkInfoBean info) {
        if (mView != null) {
            mView.hideLoading();
            mView.getParkInfoNext(info);
        }
    }
}
