package com.jinke.community.service;

import com.jinke.community.service.listener.ISocialCircleListener;

import java.util.Map;

/**
 * Created by root on 17-8-17.
 */

public interface ISocialCircleBiz {
    void getAdvertising(Map<String, String> map, ISocialCircleListener listener);
}
