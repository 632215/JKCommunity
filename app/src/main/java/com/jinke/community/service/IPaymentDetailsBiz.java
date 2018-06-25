package com.jinke.community.service;

import com.jinke.community.http.main.OnRequestListener;
import com.jinke.community.presenter.PaymentDetailsPresenter;
import com.jinke.community.service.listener.PaymentDetailsListener;

import java.util.Map;

/**
 * Created by root on 17-8-19.
 */

public interface IPaymentDetailsBiz {
    /**
     * 缴费明细
     *
     * @param map
     * @param listener
     */
    void getPaymentRecordDetail(Map<String, String> map, OnRequestListener listener);

    /**
     * 预存明细
     *
     * @param map
     * @param listener
     */
    void getPrePayDetail(Map<String, String> map, OnRequestListener listener);

    /**
     * 车位充值明细
     *
     * @param map
     * @param listener
     */
    void getPaymentVehicleDetail(Map<String, String> map, PaymentDetailsListener listener);
}
