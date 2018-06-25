package com.jinke.community.service.listener;

import com.jinke.community.bean.PayBean;

/**
 * Created by Administrator on 2017/10/23.
 */

public interface VehiclePayListener {

    void onError(String code, String msg);

    void getPaymentNext(PayBean info);
}
