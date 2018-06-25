package com.jinke.community.service;

import com.jinke.community.service.listener.IOtherCommunityListener;

import java.util.Map;

/**
 * Created by root on 17-8-2.
 */

public interface IOtherCommunityBiz {
    void getCityList(Map<String, String> map, IOtherCommunityListener listener);
    void getCommunityList(Map<String, String> map, IOtherCommunityListener listener);
}
