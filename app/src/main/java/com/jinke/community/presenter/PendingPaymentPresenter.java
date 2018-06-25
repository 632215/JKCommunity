package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.ArrearsListBean;
import com.jinke.community.bean.WithholdingBean;
import com.jinke.community.service.IPendingPaymentBiz;
import com.jinke.community.service.impl.PendingPaymentImpl;
import com.jinke.community.service.listener.IPendingPaymentListener;
import com.jinke.community.view.PendingPaymentView;

import java.util.Map;

/**
 * Created by Administrator on 2017/8/3.
 */
public class PendingPaymentPresenter extends BasePresenter<PendingPaymentView> implements IPendingPaymentListener {
    private Context mContext;
    private IPendingPaymentBiz paymentBiz;

    public PendingPaymentPresenter(Context mContext) {
        this.mContext = mContext;
        paymentBiz = new PendingPaymentImpl(mContext);
    }

    /**
     * 查询房屋欠费信息
     *
     * @param map
     */
    public void getArrearsList(Map<String, String> map) {
        if (mView != null) {
            paymentBiz.getArrearsList(map, this);
        }
    }

    @Override
    public void onArrearsList(ArrearsListBean bean) {
        if (mView != null) {
            mView.onArrearsList(bean);
        }
    }


    public void getSignStatus(Map<String, String> map) {
        if (mView != null) {
            paymentBiz.getSignStatus(map, this);
        }
    }

    public void getUnSign(Map<String, String> map) {
        if (mView != null) {
            paymentBiz.getSignUn(map, this);
        }
    }

    @Override
    public void onError(String code, String msg) {
        if (mView != null) {
            mView.showMsg(msg);
        }
    }

    @Override
    public void onTopLayout(String code, String msg) {
        if (mView != null) {
            mView.showTopLayout(msg);
        }
    }

    @Override
    public void onUnSign(WithholdingBean bean) {
        if (mView != null) {
            mView.onUnSign(bean);
        }
    }

    @Override
    public void onWithholdingInfo(WithholdingBean bean) {
        if (mView != null) {
            mView.onWithholdingInfo(bean);
        }
    }
}