package com.jinke.community.http.door.http;

import android.content.Context;

/**
 * function:
 * author: hank
 * date: 2017/5/15
 */

public class Account {
    //path
    public static final String regularUrl = "http://47.95.28.64:8099/";
    public static final String debugUrl = "https://www.doormaster.me:9099/";
    private Context context;

    public Account(Context context) {
        this.context = context;
//        new CustomHttpURL(context).getAccessToke();
    }
}