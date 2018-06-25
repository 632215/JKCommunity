package com.jinke.community.service.listener;

import com.jinke.community.bean.EmptyObjectBean;
import com.jinke.community.bean.MsgBean;

/**
 * Created by Administrator on 2018/6/7.
 */

public interface MsgCenterListener {
    void getMsgNext(MsgBean info);

    void getMsgError(String code, String msg);

    void upDateMsgNext(EmptyObjectBean info);
}
