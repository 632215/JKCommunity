package com.jinke.community.service.listener;

import com.jinke.community.bean.ArrearsListBean;
import com.jinke.community.bean.EmptyObjectBean;
import com.jinke.community.bean.WithholdingBean;

/**
 * Created by root on 17-8-18.
 */

public interface IPendingPaymentListener {

    void onArrearsList(ArrearsListBean bean);

    void onError(String code, String msg);

    void onTopLayout(String code, String msg);

    void onUnSign(WithholdingBean bean);

    /**
     * 代扣信息
     *
     * @param bean
     */
    void onWithholdingInfo(WithholdingBean bean);
}
