package com.jinke.community.view;

import com.jinke.community.bean.ArrearsListBean;
import com.jinke.community.bean.WithholdingBean;

/**
 * Created by Administrator on 2017/8/3.
 */

public interface PendingPaymentView {
    void onArrearsList(ArrearsListBean bean);

    /**
     * 代扣解约
     * @param bean
     */
    void onUnSign(WithholdingBean bean);

    /**
     * 获取代扣信息
     *
     * @param bean
     */
    void onWithholdingInfo(WithholdingBean bean);

    /**
     * 错误提示信息
     * @param msg
     */
    void showMsg(String msg);

    void showTopLayout(String msg);


}
