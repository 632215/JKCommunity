package com.jinke.community.utils;

import android.content.Context;

import com.tencent.stat.StatService;

/**
 * Created by Administrator on 2018/1/31.
 */

public class StatServiceUtils {
    public static void beginPage(Context context, String content) {
        StatService.onResume(context);
        StatService.trackBeginPage(context, content);
    }

    public static void endPage(Context context, String content) {
        StatService.onPause(context);
        StatService.trackEndPage(context, content);
    }
}
