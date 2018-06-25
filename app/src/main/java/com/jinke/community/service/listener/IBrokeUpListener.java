package com.jinke.community.service.listener;

import com.jinke.community.bean.LoginBean;
import com.jinke.community.bean.acachebean.HouseListBean;

/**
 * Created by root on 17-8-6.
 */

public interface IBrokeUpListener {
    void onSuccess(LoginBean bean);

    void onError(String code,String msg);

    void getHouseListNext(HouseListBean info);
}
