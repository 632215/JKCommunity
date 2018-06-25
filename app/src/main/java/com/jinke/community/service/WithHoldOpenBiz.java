package com.jinke.community.service;

import com.jinke.community.service.listener.WithHoldOpenListener;

import java.util.Map;

/**
 * Created by Administrator on 2017/11/23.
 */

public interface WithHoldOpenBiz {
    void withHoldOpen(Map map, WithHoldOpenListener listener);
}
