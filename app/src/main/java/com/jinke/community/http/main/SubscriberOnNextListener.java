package com.jinke.community.http.main;

/**
 * Created by liukun on 16/icon_cs3/10.
 */
public interface SubscriberOnNextListener<T> {
    void onNext(T t);
    void onError(String Code, String Msg);
}
