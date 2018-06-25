package com.jinke.community.service.listener;

import com.jinke.community.bean.HouseListInfo;
import com.jinke.community.bean.ReasonBean;

/**
 * Created by root on 17-8-21.
 */

public interface IOpenDoorPasswordListener {
    void onError(String code,String msg);
    void onReasonBean(ReasonBean bean);

    void getHouseListDateError(String code, String msg);

    void getHouseListDateNext(HouseListInfo info);
}
