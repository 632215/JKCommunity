package com.jinke.community.service.listener;


/**
 * Created by root on 17-7-22.
 */

public interface IRequestListener<T> {
    void onSuccessListener(T t);
    void onErrorListener(String Code, String Msg);
}
