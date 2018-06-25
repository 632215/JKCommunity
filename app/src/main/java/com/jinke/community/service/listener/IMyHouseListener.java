package com.jinke.community.service.listener;

import com.jinke.community.bean.SetDefaultHouseBean;
import com.jinke.community.bean.acachebean.HouseListBean;

/**
 * Created by root on 17-8-3.
 */

public interface IMyHouseListener {
    void onErrorMsg(String code, String msg);

    void onSuccess(HouseListBean bean);
    void onDefaultHouse(SetDefaultHouseBean bean);

}
