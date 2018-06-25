package com.jinke.community.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import com.jinke.community.application.MyApplication;

import www.jinke.com.library.utils.commont.StringUtils;

/**
 * Created by Administrator on 2018/1/22.
 */

public class AndroidUtils {
    public static String getUnId() {
        String androidID = Settings.Secure.getString(MyApplication.getInstance().getContentResolver(), Settings.Secure.ANDROID_ID);
        String id = androidID + Build.SERIAL;
        return id;
    }

    /**
     * 拨打电话
     * @param context
     * @param phone
     */
    public static void callPhone(Context context, String phone) {
        if (context == null || StringUtils.isEmpty(phone))
            return;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phone));
        //开启系统拨号器
        context.startActivity(intent);
    }
}
