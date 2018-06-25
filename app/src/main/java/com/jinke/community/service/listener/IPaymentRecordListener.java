package com.jinke.community.service.listener;

import com.jinke.community.bean.PaymentRecordBean;

/**
 * Created by root on 17-8-19.
 */

public interface IPaymentRecordListener {

    void onError(String code,String msg);

    void onPaymentRecordList(PaymentRecordBean bean);

    void getWithHoldRecorderNext(PaymentRecordBean info);
}
