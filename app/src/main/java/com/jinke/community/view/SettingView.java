package com.jinke.community.view;

import com.jinke.community.base.BaseView;
import com.jinke.community.bean.CustomerPhoneBean;

/**
 * Created by Administrator on 2017/11/20.
 */

public interface SettingView extends BaseView{
    void getLoginOutNext();

    void getCustomerPhoneNext(CustomerPhoneBean bean);

    void showErrorMsg(String msg);
}
