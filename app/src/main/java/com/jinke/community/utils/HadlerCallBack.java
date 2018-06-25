package com.jinke.community.utils;

import android.content.Context;
import android.widget.Toast;

import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;

/**
 * Created by root on 17-8-8.
 */

public class HadlerCallBack extends DefaultHandler {
    Context mContext;

    public HadlerCallBack(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public void handler(String data, CallBackFunction function) {
        if (function != null) {
            Toast.makeText(mContext, data, Toast.LENGTH_SHORT).show();
        }
    }
}
