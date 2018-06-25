package com.jinke.community.service.listener;

import com.jinke.community.bean.StateBean;

/**
 * Created by Administrator on 2018/5/30.
 */

public interface AuthenticationTipsListener {
    void getStateNext(StateBean info);

    void getStateError(String code, String msg);
}
