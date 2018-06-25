package com.jinke.community.service.listener;

/**
 * Created by Administrator on 2018/3/28.
 */

public interface MyMeansListener {
    void onSuccess(String avatar);

    void onError(String s, String errmsg);
}
