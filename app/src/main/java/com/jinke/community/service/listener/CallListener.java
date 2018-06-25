package com.jinke.community.service.listener;

import com.jinke.community.bean.acachebean.CallCenterBean;

/**
 * Created by Administrator on 2018/3/27.
 */

public interface CallListener {
    void getPhoneNext(CallCenterBean info);

    void onError(String code, String msg);
}
