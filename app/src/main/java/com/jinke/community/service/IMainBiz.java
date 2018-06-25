package com.jinke.community.service;

import com.jinke.community.http.door.RequestHeader;
import com.jinke.community.presenter.MainPresenter;
import com.jinke.community.service.listener.IHouseKeeperListener;
import com.jinke.community.service.listener.IMainListener;
import com.jinke.community.service.listener.IPersonalCenterListener;

import java.util.Map;

/**
 * Created by root on 17-8-9.
 */

public interface IMainBiz {
    /**
     * 获取ｕｒｌ地址
     *
     * @param map
     * @param listener
     */
    void getUrlConfig(Map<String, String> map, IMainListener listener);

    void getDeviceListDate(Map<String, String> map, IMainListener listener);

    void getUserInfo(Map<String, String> map, IMainListener listener);

    void getBandingControl(Map<String, String> map, IMainListener listener);

    void getUpDate(Map<String, String> map, IMainListener listener);

    void getRedCircle(Map<String, String> map, IMainListener listener);
}
