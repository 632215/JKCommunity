package com.jinke.community.service;

import com.jinke.community.service.listener.IOpenDoorListener;

import java.util.Map;

/**
 * Created by root on 17-8-21.
 */

public interface IOpenDoorBiz {
    /**
     * 系统时间
     * @param map
     * @param listener
     */
    void getDataTime(Map<String,String> map, IOpenDoorListener listener);

    /**
     * 门禁日志
     * @param map
     * @param listener
     */
    void getOpenLogDate(Map<String,String> map);

}
