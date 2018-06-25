package com.jinke.community.service;

import com.jinke.community.presenter.PersonalCenterPresent;
import com.jinke.community.service.listener.IHouseKeeperListener;
import com.jinke.community.service.listener.IMainListener;
import com.jinke.community.service.listener.IPersonalCenterListener;
import com.lidroid.xutils.http.RequestParams;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by root on 17-8-16.
 */

public interface IPersonalCenterBiz {
    /**
     * 获取url配置信息
     *
     * @param map
     * @param listener
     */
    void getConfigUrl(Map<String, String> map, IPersonalCenterListener listener);


    /**
     * 预存余额
     *
     * @param map
     * @param listener
     */
    void getPrePayList(Map<String, String> map, IPersonalCenterListener listener);

    /**
     * 积分
     *
     * @param map
     * @param listener
     */
    void getPoint(Map<String, String> map, IPersonalCenterListener listener);

    /**
     * 车位信息
     *
     * @param map
     * @param listener
     */
    void getParkingInfo(Map map, IPersonalCenterListener listener);

    /**
     * 房屋列表
     *
     * @param map
     * @param listener
     */
    void getHouseList(Map map, IPersonalCenterListener listener);

    /**
     * 获取用户基本信息
     *
     * @param map
     * @param listener
     */
    void getUserInfo(HashMap map,IPersonalCenterListener listener);
}
