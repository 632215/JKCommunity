package com.jinke.community.service.listener;

import com.jinke.community.bean.EmptyObjectBean;
import com.jinke.community.bean.ParkInfoBean;

/**
 * Created by Administrator on 2017/10/20.
 */

public interface VehicleAuthorizedListener {
    void error(String msg);

    void getParkInfoonNext(ParkInfoBean info);

    void addAuthorizeonNext(EmptyObjectBean info);
}
