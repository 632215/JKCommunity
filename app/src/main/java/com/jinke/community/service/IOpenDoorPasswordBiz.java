package com.jinke.community.service;

import com.jinke.community.service.listener.IOpenDoorPasswordListener;

import java.util.Map;

/**
 * Created by root on 17-8-21.
 */

public interface IOpenDoorPasswordBiz {
    void getPurposeListDate(Map<String,String> map, IOpenDoorPasswordListener listener);


}
