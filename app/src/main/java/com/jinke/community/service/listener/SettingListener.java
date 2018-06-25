package com.jinke.community.service.listener;

import com.jinke.community.bean.CustomerPhoneBean;

/**
 * Created by Administrator on 2017/11/20.
 */

public interface SettingListener {
    void getCustomerPhoneNext(CustomerPhoneBean bean);

    void onError(String code, String msg);
}
