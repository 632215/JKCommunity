package com.jinke.community.service.listener;

import com.jinke.community.bean.ParkInfoBean;
import com.jinke.community.bean.acachebean.HouseListBean;

/**
 * Created by Administrator on 2017/11/18.
 */

public interface PropertyPaymentListener {
    void getHouseListNext(HouseListBean info);

    void onErrorMsg(String code, String msg);

    void getParkInfoNext(ParkInfoBean info);
}
