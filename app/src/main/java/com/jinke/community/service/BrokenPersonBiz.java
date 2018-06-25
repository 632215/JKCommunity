package com.jinke.community.service;

import com.jinke.community.service.listener.BrokenPersonlistener;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/28.
 */

public interface BrokenPersonBiz {
    void getBrokePerson(Map<String, String> map, BrokenPersonlistener listener);
}
