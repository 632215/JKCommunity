package com.jinke.community.service;

import com.jinke.community.service.listener.VehicleAuthorizedListener;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/17.
 */

public interface VehicleAuthorizedBiz {
    void getParkInfo(Map map, VehicleAuthorizedListener listener);

    void addAuthorize(Map map, VehicleAuthorizedListener listener);
}
