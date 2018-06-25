package com.jinke.community.service.listener;

import com.jinke.community.bean.ParkInfoSelectBean;

import java.util.List;

/**
 * Created by Administrator on 2017/10/21.
 */

public interface ParkingLotRenewalListener {
    void getParkInfoonNext(List<ParkInfoSelectBean.ListBean> info);

    void error(String msg);
}
