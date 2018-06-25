package com.jinke.community.service.listener;

import com.jinke.community.bean.acachebean.HouseListBean;

/**
 * Created by Administrator on 2017/11/21.
 */

public interface AddAuthorizedListener {
    void addAuthorizationNext(HouseListBean info);

    void onErrorMsg(String code, String msg);
}
