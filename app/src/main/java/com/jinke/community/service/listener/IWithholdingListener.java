package com.jinke.community.service.listener;

import com.jinke.community.bean.PayBean;
import com.jinke.community.bean.WithholdingBean;

/**
 * Created by root on 17-8-18.
 */

public interface IWithholdingListener {
    void onDoPayMent(PayBean bean);

    void onError(String code, String msg);

    void getSignStateNext(WithholdingBean info);
}
