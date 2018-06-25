package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.PaymentRecordBean;
import com.jinke.community.service.impl.PaymentRecordImpl;
import com.jinke.community.service.listener.IPaymentRecordListener;
import com.jinke.community.view.PaymentRecordView;

import java.util.Map;

/**
 * Created by Administrator on 2017/8/1.
 */

public class PaymentRecordPresenter extends BasePresenter<PaymentRecordView> implements IPaymentRecordListener {
    private Context mContext;
    private PaymentRecordImpl paymentRecord;

    public PaymentRecordPresenter(Context mContext) {
        this.mContext = mContext;
        paymentRecord = new PaymentRecordImpl(mContext);
    }

    /**
     * 缴费记录查询
     *
     * @param map
     */
    public void getPaymentRecordList(Map<String, String> map) {
        paymentRecord.getPaymentRecordList(map, this);
    }

    @Override
    public void onError(String code, String msg) {
        if (mView != null) {
            mView.showMsg(msg);
        }
    }

    @Override
    public void onPaymentRecordList(PaymentRecordBean bean) {
        if (mView != null) {
            mView.onRecordList(bean);
        }
    }

    /**
     * 代扣记录查询
     *
     * @param map
     */
    public void getWithHoldRecorder(Map<String, String> map) {
        paymentRecord.getWithHoldRecorder(map, this);
    }

    @Override
    public void getWithHoldRecorderNext(PaymentRecordBean info) {
        if (mView != null) {
            mView.onRecordList(info);
        }
    }
}
