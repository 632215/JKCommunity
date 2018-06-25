package com.jinke.community.service.listener;

import com.jinke.community.bean.PasswordInfo;

/**
 * Created by root on 17-8-21.
 */

public interface IOpenDoorPasswordInfoListener {
    void onPasswordInfo(PasswordInfo info);
    void onError(String code,String msg);

}
