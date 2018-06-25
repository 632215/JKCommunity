package com.jinke.community.service;

import com.jinke.community.http.main.OnRequestListener;

import java.util.Map;

/**
 * Created by root on 17-8-21.
 */

public interface IDoorHouseListBiz {
    void getHouseListDate(Map<String, String> map, OnRequestListener listener);
}
