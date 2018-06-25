package com.jinke.community.service;

import com.jinke.community.service.listener.BindHouseListener;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/13.
 */

public interface BindHouseBiz {
    void getRegisterData(Map<String, String> map, BindHouseListener listener);

    void autoBindHouse(Map<String, String> map, BindHouseListener listener);

    void setDefaultHouse(Map map, BindHouseListener listener);
}
