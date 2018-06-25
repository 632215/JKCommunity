package com.jinke.community.service;

import com.jinke.community.service.listener.VehiclePayListener;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/23.
 */

public interface VehiclePayBiz {
    void getPayment(Map<String, String> map, VehiclePayListener listener);
}
