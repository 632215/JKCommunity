package com.jinke.community.service;

import com.jinke.community.service.listener.IPendingPaymentListener;

import java.util.Map;

/**
 * Created by root on 17-8-18.
 */

public interface IPendingPaymentBiz {
    /**
     * 欠费列表
     *
     * @param map
     * @param listener
     */
    void getArrearsList(Map<String, String> map, IPendingPaymentListener listener);

    /**
     * 查询代扣签约状态接口
     *
     * @param map
     * @param listener
     */
    void getSignStatus(Map<String, String> map, IPendingPaymentListener listener);

    /**
     * 代扣解约接口
     *
     * @param map
     * @param listener
     */
    void getSignUn(Map<String, String> map, IPendingPaymentListener listener);
}
