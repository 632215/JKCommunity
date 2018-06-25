package com.jinke.community.service;

import com.jinke.community.service.listener.IPaymentRecordListener;

import java.util.Map;

/**
 * Created by root on 17-8-19.
 */

public interface IPaymentRecordBiz {
    void getPaymentRecordList(Map<String,String> map, IPaymentRecordListener listener);

    void getWithHoldRecorder(Map<String, String> map, IPaymentRecordListener listener);
}
