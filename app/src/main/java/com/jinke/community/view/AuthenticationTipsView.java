package com.jinke.community.view;

import com.jinke.community.base.BaseView;
import com.jinke.community.bean.StateBean;

/**
 * Created by Administrator on 2018/5/30.
 */

public interface AuthenticationTipsView extends BaseView{
    void getStateNext(StateBean info);

    void getStateError(String msg);
}
