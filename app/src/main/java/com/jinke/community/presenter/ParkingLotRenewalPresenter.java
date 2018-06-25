package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.ParkInfoSelectBean;
import com.jinke.community.service.ParkingLotRenewalBiz;
import com.jinke.community.service.impl.ParkingLotRenewalImpl;
import com.jinke.community.service.listener.ParkingLotRenewalListener;
import com.jinke.community.view.ParkingLotRenewalView;

import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/10/21.
 */

public class ParkingLotRenewalPresenter extends BasePresenter<ParkingLotRenewalView> implements ParkingLotRenewalListener {
    private Context mContext;
    private ParkingLotRenewalBiz mBiz;


    public ParkingLotRenewalPresenter(Context mContext) {
        this.mContext = mContext;
        mBiz = new ParkingLotRenewalImpl(mContext);
    }

    public void getParkInfo(Map map) {
        if (mView != null) {
            mView.showLoading();
            mBiz.getParkInfo(map, this);
        }
    }

    @Override
    public void error(String msg) {
        if (mView != null) {
            mView.hideLoading();
            mView.error(msg);
        }
    }

    @Override
    public void getParkInfoonNext(List<ParkInfoSelectBean.ListBean> info) {
        if (mView != null) {
            mView.hideLoading();
            mView.getParkInfoonNext(info);
        }
    }
}
