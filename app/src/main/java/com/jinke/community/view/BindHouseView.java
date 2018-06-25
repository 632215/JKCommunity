package com.jinke.community.view;

import com.jinke.community.base.BaseView;
import com.jinke.community.bean.AutoBindBean;
import com.jinke.community.bean.SetDefaultHouseBean;
import com.jinke.community.bean.RegisterLoginBean;

/**
 * Created by Administrator on 2017/11/13.
 */

public interface BindHouseView extends BaseView{
    void showMsg(String msg);

    void getRegisterDataNext(RegisterLoginBean info);

    void autoBindHouseNext(AutoBindBean info);

    void setDefaultHouseNext(SetDefaultHouseBean info);
}
