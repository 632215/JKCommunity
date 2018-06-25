package com.jinke.community.view;

import com.jinke.community.base.BaseView;
import com.jinke.community.bean.PayBean;

/**
 * Created by Administrator on 2017/10/23.
 */

public interface VehiclePayView extends BaseView {
    void getPaymentOnNext(PayBean info);

    void showMsg(String msg);

    void payResult(PayBean payBean);
}
