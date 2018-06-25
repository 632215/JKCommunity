package com.jinke.community.view;

import com.jinke.community.base.BaseView;
import com.jinke.community.bean.PaymentRecordBean;

/**
 * Created by Administrator on 2017/8/1.
 */

public interface PaymentRecordView {
    void showMsg(String msg);

    void onRecordList(PaymentRecordBean bean);
}
