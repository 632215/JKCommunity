package com.jinke.community.service;

import com.jinke.community.service.listener.IOpenDoorPasswordInfoListener;

import java.util.Map;

/**
 * Created by root on 17-8-21.
 */

public interface IOpenDoorPasswordInfoBiz {

    void getPassWordData(Map<String,String> map,IOpenDoorPasswordInfoListener listener);

}
