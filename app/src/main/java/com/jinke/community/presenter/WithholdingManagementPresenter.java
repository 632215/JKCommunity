package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.HouseWithHoldBean;
import com.jinke.community.bean.WithHoldBreakBean;
import com.jinke.community.service.WithholdingManagementBiz;
import com.jinke.community.service.impl.WithholdingManagementImpl;
import com.jinke.community.service.listener.WithholdingManagementListener;
import com.jinke.community.view.WithholdingManagementView;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/11/21.
 */

public class WithholdingManagementPresenter extends BasePresenter<WithholdingManagementView>
        implements WithholdingManagementListener {
    private Context mContext;
    private WithholdingManagementBiz mBiz;

    public WithholdingManagementPresenter(Context mContext) {
        this.mContext = mContext;
        mBiz = new WithholdingManagementImpl(mContext);
    }

    /**
     * 获取房屋列表（签约状态）
     */
    public void getHouseList() {
        if (mView != null) {
            Map map = new HashMap();
            map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
            map.put("signType", "true");
            mBiz.getHouseWithHold(map, this);

        }
    }

    @Override
    public void getHouseWithHoldNext(HouseWithHoldBean info) {
        if (mView != null) {
            mView.getHouseWithHoldNext(info);
        }
    }

    @Override
    public void onErrorMsg(String code, String msg) {
        if (mView != null) {
            mView.showMessage(msg);
        }
    }

    /**
     * 解约
     */
    public void withholdBreak(HouseWithHoldBean.ListBean listBean) {
        if (listBean != null && listBean.getSign_status() != null) {
            Map map = new HashMap();
            map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
            map.put("houseId", listBean.getHouse_id());
            map.put("signType", listBean.getSign_status().getAli_sign_status() == 1 ? "alisign" : listBean.getSign_status().getWx_sign_status() == 1 ? "wxsign" : "");
            if (map.get("signType").equals("")) {
                return;
            }
            mBiz.withholdBreak(map, this);
        }
    }

    @Override
    public void withholdBreakNext(WithHoldBreakBean info) {
        getHouseList();
        if (mView != null) {
            mView.showMessage("代扣解约成功");
        }
    }
}
