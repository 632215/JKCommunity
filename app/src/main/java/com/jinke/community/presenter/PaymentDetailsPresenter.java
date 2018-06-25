package com.jinke.community.presenter;

import android.content.Context;

import com.jinke.community.base.BasePresenter;
import com.jinke.community.bean.PaymentDetailsBean;
import com.jinke.community.bean.PaymentVehicleDetailsBean;
import com.jinke.community.bean.PrePayDetailBean;
import com.jinke.community.http.main.OnRequestListener;
import com.jinke.community.service.impl.PaymentDetailsImpl;
import com.jinke.community.service.listener.PaymentDetailsListener;
import com.jinke.community.view.PaymentDetailsView;

import java.util.Map;

/**
 * Created by Administrator on 2017/8/2.
 */

public class PaymentDetailsPresenter extends BasePresenter<PaymentDetailsView> implements OnRequestListener<PaymentDetailsBean> ,PaymentDetailsListener {
    PaymentDetailsImpl details;

    public PaymentDetailsPresenter(Context mContext) {
        details = new PaymentDetailsImpl(mContext);
    }

    public void getPaymentRecordDetail(Map<String, String> map) {
        if (mView != null) {
            details.getPaymentRecordDetail(map, this);
        }
    }

    public void getPrePayDetail(Map<String, String> map) {
        if (mView != null) {
            details.getPrePayDetail(map, this);
        }
    }


    @Override
    public void onSuccess(PaymentDetailsBean bean) {
        if (mView != null) {
            mView.onSuccess(bean);
        }
    }

    @Override
    public void onError(String Code, String Msg) {
        if (mView != null) {
            mView.showMsg(Msg);
        }
    }

    @Override
    public void getVehicleDetailNext(PaymentVehicleDetailsBean info) {
        if (mView != null) {
            mView.getVehicleDetailNext(info);
        }
    }

    @Override
    public void getVehicleDetailError(String code, String msg) {
        if (mView != null) {
            mView.showMsg(msg);
        }
    }

    public void getPaymentVehicleDetail(Map<String, String> map) {
        if (mView != null) {
            details.getPaymentVehicleDetail(map, this);
        }
    }
}
