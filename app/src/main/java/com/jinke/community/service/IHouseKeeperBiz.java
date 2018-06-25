package com.jinke.community.service;

import com.jinke.community.service.listener.IHouseKeeperListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 17-8-14.
 */

public interface IHouseKeeperBiz {

    /**
     * 首页报事列表
     *
     * @param map
     * @param listener
     */
    void getBreakList(Map<String, String> map, IHouseKeeperListener listener);

    /**
     * 首页公告列表
     *
     * @param hashMap
     * @param listener
     */
    void getDefaultData(HashMap hashMap, IHouseKeeperListener listener);


    /**
     * 房屋估价
     *
     * @param listener
     */
    void getHouseValue(Map<String, String> map, IHouseKeeperListener listener);


    /**
     * 获取天气
     *
     * @param listener
     */
    void getWeatheInfo(Map<String, String> map, IHouseKeeperListener listener);

    /**
     * 点赞
     * @param map
     */
    void praiseClick(Map map, IHouseKeeperListener listener);

    /**
     * 获取房屋列表
     * @param map
     */
    void getHouseList(Map map, IHouseKeeperListener listener);
}
