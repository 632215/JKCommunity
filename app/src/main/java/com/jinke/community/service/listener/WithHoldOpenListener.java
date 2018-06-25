package com.jinke.community.service.listener;

import com.jinke.community.bean.RegisterLoginBean;
import com.jinke.community.bean.WithHoldOpenBean;

/**
 * Created by Administrator on 2017/11/28.
 */

public interface WithHoldOpenListener {
    void onErrorMsg(String code, String msg);

    void withHoldOpenNext(WithHoldOpenBean info);
}
