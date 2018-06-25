package com.jinke.community.service.listener;

import com.jinke.community.bean.ThemeBean;

/**
 * Created by Administrator on 2018/3/29.
 */

public interface StartupPageListener {
    void getThemeNext(ThemeBean info);

    void onError(String code, String msg);
}
