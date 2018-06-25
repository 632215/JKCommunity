package com.jinke.community.service.listener;

import com.jinke.community.bean.BrokenPersonBean;

/**
 * Created by Administrator on 2017/11/28.
 */

public interface BrokenPersonlistener {
    void getBrokePersonNext(BrokenPersonBean info);

    void onError(String code, String msg);
}
