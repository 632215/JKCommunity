package com.jinke.community.service;

import com.jinke.community.service.listener.ParkingLotRenewalListener;

import java.util.Map;

/**
 * Created by Administrator on 2017/10/21.
 */

public interface ParkingLotRenewalBiz {
    void getParkInfo(Map map, ParkingLotRenewalListener listener);
}
