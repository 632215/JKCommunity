package com.jinke.community.service;

import com.jinke.community.service.listener.IOtherCommunityListener;
import com.jinke.community.service.listener.ISelectCommunityListener;

import java.util.Map;

/**
 * Created by root on 17-8-2.
 */

public interface ISelectCommunityBiz {
    /**
     * 根据经纬度获取小区列表
     *
     * @param map
     * @param listener
     */
    void getCommunityListByGps(Map<String, String> map, ISelectCommunityListener listener);

    /***
     * 根据关键字查询小区
     * @param map
     * @param listener
     */
    void getQueryCommunity(Map<String, String> map, ISelectCommunityListener listener);

    /**
     * 获取城市列表
     *
     * @param map
     * @param listener
     */
    void getCityList(Map<String, String> map, ISelectCommunityListener listener);

    /**
     * 根据城市名查询小区
     *
     * @param map
     * @param listener
     */
    void getCommunityList(Map<String, String> map, ISelectCommunityListener listener);
}
