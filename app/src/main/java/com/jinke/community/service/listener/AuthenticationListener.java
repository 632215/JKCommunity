package com.jinke.community.service.listener;

/**
 * Created by Administrator on 2018/5/30.
 */

public interface AuthenticationListener {
    void uploadSuccess();

    void uploadFail(String s);
}
