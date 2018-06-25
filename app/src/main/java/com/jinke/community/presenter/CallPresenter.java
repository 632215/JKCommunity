package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.application.MyApplication;
import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.acachebean.CallCenterBean;
import com.jinke.community.bean.acachebean.HouseListBean;
import com.jinke.community.service.CallBiz;
import com.jinke.community.service.impl.CallImpl;
import com.jinke.community.service.listener.CallListener;
import com.jinke.community.view.CallView;

import java.util.HashMap;
import java.util.Map;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by Administrator on 2018/3/27.
 */

public class CallPresenter extends BasePresenter<CallView> implements CallListener {
    private Context mContext;
    private CallBiz mCallBiz;

    public CallPresenter(Context mContext) {
        this.mContext = mContext;
        mCallBiz = new CallImpl(mContext);
    }

    //获取房屋管家号码
    public void getPhone(HouseListBean.ListBean bean) {
        Map map = new HashMap();
        map.put("accessToken", MyApplication.getBaseUserBean().getAccessToken());
        if (bean == null || StringUtils.isEmpty(bean.getHouse_id()))
            return;
        map.put("houseId", bean.getHouse_id());
        if (mCallBiz != null && mView != null) {
            mView.showLoading();
            mCallBiz.getPhone(map, this);
        }
    }

    /**
     * 获取房屋管家号码 成功回调
     *
     * @param info
     */
    @Override
    public void getPhoneNext(CallCenterBean info) {
        if (mView != null) {
            mView.hideLoading();
            mView.getPhoneNext(info);
        }
    }

    @Override
    public void onError(String code, String msg) {
        if (mView != null) {
            mView.hideLoading();
            mView.showMsg(msg);
        }
    }
}
