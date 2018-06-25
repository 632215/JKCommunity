package com.jinke.community.wxapi;

import android.os.Bundle;
import android.util.Log;

import com.umeng.weixin.callback.WXCallbackActivity;

public class WXEntryActivity extends WXCallbackActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.i("32s", "回调了");
    }
}
