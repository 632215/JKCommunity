package com.jinke.community.service;

import com.jinke.community.service.listener.AddAuthorizedListener;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/21.
 */

public interface AddAuthorizedBiz {
    void addAuthorization(Map<String, String> map, AddAuthorizedListener listener);
}
