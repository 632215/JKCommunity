package com.jinke.community.view;

import com.jinke.community.bean.BrokenPersonBean;

/**
 * Created by Administrator on 2017/11/28.
 */

public interface BrokenPersonView {
    void showMsg(String msg);

    void getBrokePersonNext(BrokenPersonBean info);
}
