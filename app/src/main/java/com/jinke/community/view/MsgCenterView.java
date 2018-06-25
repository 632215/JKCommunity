package com.jinke.community.view;

import com.jinke.community.base.BaseView;
import com.jinke.community.bean.MsgBean;

/**
 * Created by Administrator on 2018/6/7.
 */

public interface MsgCenterView extends BaseView {

    void getMsgNext(MsgBean info);

    void getMsgError(String msg);

    void upDateMsgNext();
}
