package com.jinke.community.service;

import com.jinke.community.bean.CustomerPhoneBean;
import com.jinke.community.service.listener.SettingListener;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/20.
 */

public interface SettingBiz {
    void getCustomerPhone(Map<String, String> map, SettingListener  listener);
}
