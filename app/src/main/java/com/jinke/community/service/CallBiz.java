package com.jinke.community.service;

import com.jinke.community.service.listener.CallListener;

import java.util.Map;

/**
 * Created by Administrator on 2018/3/27.
 */

public interface CallBiz {
    void getPhone(Map map, CallListener Listener);
}
