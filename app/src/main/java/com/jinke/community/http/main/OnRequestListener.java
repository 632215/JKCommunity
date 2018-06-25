package com.jinke.community.http.main;

public interface OnRequestListener<T> {
    void onSuccess(T t);

    void onError(String Code, String Msg);
}