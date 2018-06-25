package com.jinke.community.service.listener;

import com.jinke.community.bean.PaymentVehicleDetailsBean;

/**
 * Created by Administrator on 2017/10/31.
 */

public interface PaymentDetailsListener {
    void getVehicleDetailNext(PaymentVehicleDetailsBean info);

    void getVehicleDetailError(String code, String msg);
}
