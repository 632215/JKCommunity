package com.jinke.community.view;

import com.jinke.community.base.BaseView;
import com.jinke.community.bean.acachebean.CallCenterBean;

/**
 * Created by Administrator on 2018/3/27.
 */

public interface CallView extends BaseView{
    void getPhoneNext(CallCenterBean info);

    void showMsg(String msg);
}
