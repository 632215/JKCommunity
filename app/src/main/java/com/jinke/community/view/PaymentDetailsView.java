package com.jinke.community.view;

import com.jinke.community.base.BaseView;
import com.jinke.community.bean.PaymentDetailsBean;
import com.jinke.community.bean.PaymentRecordDetailBean;
import com.jinke.community.bean.PaymentVehicleDetailsBean;
import com.jinke.community.bean.PrePayDetailBean;

/**
 * Created by Administrator on 2017/8/2.
 */

public interface PaymentDetailsView {
    void onSuccess(PaymentDetailsBean bean);
    void showMsg(String msg);

    void getVehicleDetailNext(PaymentVehicleDetailsBean info);
}
