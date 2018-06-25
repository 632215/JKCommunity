package com.jinke.community.service;

import com.jinke.community.presenter.WithholdingPresenter;
import com.jinke.community.service.listener.IWithholdingListener;

import java.util.Map;

/**
 * Created by root on 17-8-18.
 */

public interface IWithholdingBiz {
    /**
     * 立即缴费
     *
     * @param map
     * @param listener
     */
    void getDoPayment(Map<String, String> map, IWithholdingListener listener);


    /**
     * 预存发起支付
     *
     * @param map
     * @param listener
     */
    void getDoPay(Map<String, String> map, IWithholdingListener listener);

    void getSignState(Map houseId, IWithholdingListener listener);
}
