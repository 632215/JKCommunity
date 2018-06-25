package com.jinke.community.service;

import android.content.Context;

import com.jinke.community.service.listener.IRequestListener;

import java.util.Map;

/**
 * Created by root on 17-7-22.
 */

public interface IShareLogin {
    void getUserLogin(Context mContext, Map<String, String> map, IRequestListener listener);

}
