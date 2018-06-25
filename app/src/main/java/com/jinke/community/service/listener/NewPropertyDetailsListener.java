package com.jinke.community.service.listener;

import com.jinke.community.bean.KeepPropertyBean;
import com.jinke.community.bean.PropertyProgressBean;

/**
 * Created by Administrator on 2018/4/25.
 */

public interface NewPropertyDetailsListener {
    void getKeeperDetailSuccess(KeepPropertyBean info);

    void onError(String code, String msg);

    void getProgressSuccess(PropertyProgressBean info);
}
