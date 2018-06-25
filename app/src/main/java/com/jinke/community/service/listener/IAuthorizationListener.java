package com.jinke.community.service.listener;

import com.jinke.community.bean.acachebean.HouseListBean;

/**
 * Created by root on 17-8-7.
 */

public interface IAuthorizationListener {
    void onErrorMsg(String code, String msg);

    void onSuccess(HouseListBean info);

    void getHouseListDataNext(HouseListBean info);

    void deleteOnNext();
}
