package com.jinke.community.service;

import com.jinke.community.service.listener.PropertyPaymentListener;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/18.
 */

public interface PropertyPaymentBiz {
    void getHouseList(Map map, PropertyPaymentListener listener);

    void getParkInfo(Map map,PropertyPaymentListener listener);
}
