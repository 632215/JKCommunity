package com.jinke.community.service;

import com.jinke.community.service.listener.IBrokeUpListener;
import com.lidroid.xutils.http.RequestParams;

import java.util.Map;

/**
 * Created by root on 17-8-6.
 */

public interface IBrokeUpBiz {
    void getUpFile(RequestParams params, IBrokeUpListener listener);

    void getHouseList(Map<String, String> map, IBrokeUpListener listener);
}
