package com.jinke.community.service;

import com.jinke.community.service.listener.IMyHouseListener;

import java.util.Map;

/**
 * Created by root on 17-8-3.
 */

public interface IMyHouseBiz {

    void getHouseListData(Map<String, String> map, IMyHouseListener listener);
    void getDefaultHouseData(Map<String, String> map, IMyHouseListener listener);
}
